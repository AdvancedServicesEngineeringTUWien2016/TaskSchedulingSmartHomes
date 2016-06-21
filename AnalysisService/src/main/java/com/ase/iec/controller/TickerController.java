package com.ase.iec.controller;

import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ase.iec.MyUserDetailsService;
import com.ase.iec.Shared.constants.Constants;
import com.ase.iec.Shared.dto.StringResponse;
import com.ase.iec.persistence.DAO.BatteryDAO;
import com.ase.iec.persistence.DAO.TaskDAO;
import com.ase.iec.persistence.DAO.TaskDTODAO;
import com.ase.iec.persistence.DAO.TickInfoDAO;
import com.ase.iec.persistence.entity.User;
import com.ase.iec.service.NotificationService;
import com.ase.iec.taskscheduling.TaskScheduler;
import com.ase.iec.taskscheduling.TickManager;

@RestController
public class TickerController {

	@Autowired
	private TaskScheduler scheduler;
	
	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private TaskDTODAO taskDTOdao;
	
	
	@Autowired
	private BatteryDAO batteryDAO;
	
	@Autowired
	private TickInfoDAO tickDAO;
	
	@Autowired
	private NotificationService notificationService;
	
	private ConcurrentHashMap<Long, ScheduledFuture<?>> userSchedulerMap = new ConcurrentHashMap<Long, ScheduledFuture<?>>();
	private ConcurrentHashMap<Long, TickManager> userTaskManagerMap = new ConcurrentHashMap<Long, TickManager>();
	
	@Autowired
	private MyUserDetailsService userDetails;
	

	@Autowired
	@Qualifier("taskSchedulerPool")
	private ThreadPoolTaskScheduler schedulerPool;

	
	
	
//this have to be implemented new if needed	
//	@RequestMapping(value = "multipleTicks", method = RequestMethod.GET, produces = "application/json")
//	@ResponseBody
//	public ResponseEntity<TickInfo> runMultipleTicks(@RequestParam("numberOfTicks") int numberOfTicks) {
//		for(int i=0; i < numberOfTicks - 1; i++) {
//			this.runTick();
//		}
//		
//		return this.runTick();
//	}
	
	@RequestMapping(value = "getFakeDate", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Long> getFakeDate() {
		User user = userDetails.getcurrentUser();
		if(user == null)
			return new ResponseEntity<Long>(new Date().getTime(), HttpStatus.OK);
		TickManager tm = userTaskManagerMap.get(user.getId());
		if(tm == null)
			return new ResponseEntity<Long>(new Date().getTime(), HttpStatus.OK);
		
		return new ResponseEntity<Long>(tm.getFakeDate(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "startTick/", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<StringResponse> startTick() {
		
		User user = userDetails.getcurrentUser();
		if(user == null)
			return new ResponseEntity<StringResponse>(new StringResponse("No user found!"), HttpStatus.NOT_FOUND);
		
		if(userSchedulerMap.containsKey(user.getId()))
			return new ResponseEntity<StringResponse>(new StringResponse("Scheduler is already running, please stop before starting!"), HttpStatus.NOT_FOUND);
		
		TickManager userTicker = new TickManager(user, scheduler, taskDAO, taskDTOdao, batteryDAO, tickDAO, notificationService);
		int intervall = Constants.SCHEDULE_INTERVALL;
		if(user.isUserIsPremium())
			intervall = (int) (intervall / Constants.PREMIUM_STANDARD_FAKTOR);
		ScheduledFuture<?> userScheduler = schedulerPool.scheduleAtFixedRate(userTicker, intervall*1000 );
		userSchedulerMap.put(user.getId(), userScheduler);
		userTaskManagerMap.put(user.getId(), userTicker);
		
		return new ResponseEntity<StringResponse>(new StringResponse("TaskScheduler is now running!"), HttpStatus.OK);
				
	}
	
	@RequestMapping(value = "stopTick/", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<StringResponse> stopTick() {
		
		User user = userDetails.getcurrentUser();
		if(user == null)
			return new ResponseEntity<StringResponse>(new StringResponse("No user found!"), HttpStatus.NOT_FOUND);
		
		if(!userSchedulerMap.containsKey(user.getId()))
			return new ResponseEntity<StringResponse>(new StringResponse("Scheduler is not running, please start before stopping!"), HttpStatus.NOT_FOUND);
		
		ScheduledFuture<?> userScheduler = userSchedulerMap.get(user.getId());
		
		boolean isDone = userScheduler.cancel(true);
		if(isDone){
			userSchedulerMap.remove(user.getId());
			userTaskManagerMap.remove(user.getId());
			return new ResponseEntity<StringResponse>(new StringResponse("TaskScheduler is now stopped!"), HttpStatus.OK);
		}
		return new ResponseEntity<StringResponse>(new StringResponse("TaskScheduler can't be interrupted, try later!"), HttpStatus.OK);
		
	}
	

	@PreDestroy
	public void shutdown(){
		schedulerPool.shutdown();
	}


}
