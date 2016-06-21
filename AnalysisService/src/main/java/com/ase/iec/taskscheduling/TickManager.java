package com.ase.iec.taskscheduling;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.ase.iec.DTO.TaskDTO;
import com.ase.iec.Shared.constants.Constants;
import com.ase.iec.Shared.dto.EnergyDTO;
import com.ase.iec.Shared.dto.EnergyPriceDTO;
import com.ase.iec.controller.SetupController;
import com.ase.iec.persistence.DAO.BatteryDAO;
import com.ase.iec.persistence.DAO.TaskDAO;
import com.ase.iec.persistence.DAO.TaskDTODAO;
import com.ase.iec.persistence.DAO.TickInfoDAO;
import com.ase.iec.persistence.entity.Battery;
import com.ase.iec.persistence.entity.Battery.BuySell;
import com.ase.iec.persistence.entity.Task;
import com.ase.iec.persistence.entity.Task.State;
import com.ase.iec.persistence.entity.TickInfo;
import com.ase.iec.persistence.entity.User;
import com.ase.iec.service.NotificationService;

public class TickManager implements Runnable{

	
	private TaskScheduler scheduler;	
	private TaskDAO taskDAO;
	private TaskDTODAO taskDTOdao;	
	private BatteryDAO batteryDAO;
	private TickInfoDAO tickDAO;
	private NotificationService notificationService;
		
	
	private User user;	
	private int productionZero = 0;	
	private Date fakeDate = new Date();
	
	
	public Long getFakeDate() {
		return fakeDate.getTime();
	}
	
	public TickManager(User user, TaskScheduler scheduler, TaskDAO taskDAO, TaskDTODAO taskDTOdao, BatteryDAO batteryDAO, TickInfoDAO tickDAO, NotificationService notificationService) {
		this.user = user;
		this.scheduler = scheduler;
		this.taskDAO = taskDAO;
		this.taskDTOdao = taskDTOdao;
		this.tickDAO = tickDAO;
		this.batteryDAO = batteryDAO;
		this.notificationService = notificationService;
	}
	
		
	public void run() {
		
		System.out.println("Tick called!");
		
		fakeDate = new Date(fakeDate.getTime() + getTickInterval() * 1000);
		
		TickInfo tickinfo = new TickInfo();
		tickinfo.setTimestamp(fakeDate);
				
		
		if(user == null)
			System.out.println("no user found");
			
		handleLastTick(user, tickinfo);	
		startNewTasks(user, tickinfo);
		
		//set all running tasks for this tick
		List<Task> runningTasks = taskDAO.findAllByUserAndState(user.getId(), State.RUNNING);			
		List<TaskDTO> tasks = new ArrayList<TaskDTO>();
		for(Task t : runningTasks){
			tasks.add(new TaskDTO(t));
		}
		taskDTOdao.saveAll(tasks);
		tickinfo.setRunningTasks(tasks);
		
		tickDAO.save(tickinfo);
		
		System.out.println("Tick info: " + tickinfo.toString());
		
		Thread.interrupted();
	}
	
	
	private void handleLastTick(User u, TickInfo tickinfo){
		
		//find all running tasks of this user sum energy and stop if task is finished
		List<Task> runningTasks = taskDAO.findAllByUserAndState(u.getId(), State.RUNNING);
		
		double usedEnergy = 0;		
		if(runningTasks != null && !runningTasks.isEmpty()){			
			
			for(Task t : runningTasks){
				//sum up energy used in last tick
				//getDuration -> min, intervall ->sec 
				usedEnergy += t.getEnergyUsage()/(t.getDuration()*60)*getTickInterval();
										
				//finish consumers
				//duration in min+60000 -> millisec
				if(t.getStartTime().getTime() + t.getDuration()*60000 <= fakeDate.getTime()){
					
					t.setState(State.FINISH);
					t.setEndTime(fakeDate);
					notificationService.sendNotification("Task " + t.getTaskname() + " from device " + t.getDevicename() + " has just finished");
					taskDAO.save(t);
				}
					
			}
			
		}
		System.out.println("Sum energy used in last tick " + usedEnergy);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<EnergyDTO> energyProduction = null;
		try{
			energyProduction = restTemplate.getForEntity("http://"+ u.getIgPlusServiceAddress() + "/igplus/getData?timestamp=" + Long.toString(tickinfo.getTimestamp().getTime()), EnergyDTO.class);
		}catch(Exception e){
			energyProduction = new ResponseEntity<EnergyDTO>(new EnergyDTO(0), HttpStatus.NOT_FOUND);
		}
		
		// multiply energy production with tick duration:
		double energyProductionPerTick = energyProduction.getBody().getProduction() * getTickInterval();
		System.out.println("production in tick: " + energyProductionPerTick);
		tickinfo.setEnergyProduction(energyProductionPerTick);	
		
		DateTime dt = new DateTime(tickinfo.getTimestamp());
		if(tickinfo.getEnergyProduction() == 0 && dt.getHourOfDay() < Constants.HOUR_SUNSET && dt.getHourOfDay() > Constants.HOUR_SUNRISE){
			productionZero++;
			if(productionZero >= Constants.SERVICE_MAN_NOTIFICATION){
				notificationService.sendNotification("Photovoltaic Pannel or IGPlus with address: " + u.getIgPlusServiceAddress() + " seems to be broken, and need your service! Please contact " + u.getUsername() + " and visit Longitude: " + u.getLongitude() + ", Latitude: " + u.getLatitude() + ". Best regards, EnergyService!");
				productionZero = 0;
			}
		}
		
		ResponseEntity<EnergyPriceDTO> price = restTemplate.getForEntity("http://"+ u.getEnergyPriceServiceAddress() + "/getEnergyPrice?fakeTimestamp=" + Long.toString(tickinfo.getTimestamp().getTime()), EnergyPriceDTO.class);
		tickinfo.setEnergyPriceBuy(price.getBody().getBuying());
		tickinfo.setEnergyPriceSell(price.getBody().getSelling());
		
		//load battery or buy/sell energy
		Map<BuySell,Double> buySellMap = updateBatteryLoad(u, ( tickinfo.getEnergyProduction() - usedEnergy), tickinfo);		
		tickinfo.setBuySellMap(buySellMap);
		
		tickinfo.setBatteryCurrentCapacity(u.getBattery().getCurrentCapacity());
		tickinfo.setBatteryMaxCapacity(u.getBattery().getMaxCapacity());

	}
	
	
	private void startNewTasks(User u, TickInfo tickinfo){
		List<Task> tasks = scheduler.getNextTasks(u.getIgPlusServiceAddress(), tickinfo);
		List<TaskDTO> taskDTOs = new ArrayList<TaskDTO>();
		if(tasks == null || tasks.isEmpty()) {
			return;
		}
		for(Task t : tasks){
			if(t.getState().equals(State.PLANNED)){
				t.setState(State.RUNNING);
				t.setStartTime(fakeDate);
				taskDAO.save(t);
				System.out.println("STARTING task " + t.getTaskname());
				notificationService.sendNotification("Task " + t.getTaskname() + " from device " + t.getDevicename() + " was just started");
				taskDTOs.add(new TaskDTO(t));
				
			}
			
		}
		taskDTOdao.saveAll(taskDTOs);
		tickinfo.setStartedTasks(taskDTOs);
	}
	
	private Map<BuySell,Double> updateBatteryLoad(User u, double energy, TickInfo tickinfo){ //energy in w/h
		Battery battery = u.getBattery();
		if(battery == null)
			throw new RuntimeException("Battery is not set up!");
		System.out.println("BEFORE:   " + battery.toString());
		double newCapacity = battery.getCurrentCapacity() + energy;
		System.out.println(battery.getCurrentCapacity() + " + " + energy + " = " + newCapacity );
		
		Map<BuySell,Double> buySell = new HashMap<BuySell, Double>();
		if(newCapacity < 0){
			//*-1 to display positive values
			buySell.put(BuySell.BOUGHT, newCapacity * -1);
			System.out.println("put bought = " + newCapacity * -1);
			battery.setSumBuy(battery.getSumBuy() + newCapacity * -1);
			// divide by 100 because c -> €
			battery.setSpent(battery.getSpent() + (newCapacity * -1) * tickinfo.getEnergyPriceSell()/100.0/1000 );
			newCapacity = 0;
		}
		else if (newCapacity > battery.getMaxCapacity()){
			buySell.put(BuySell.SOLD, newCapacity-battery.getMaxCapacity());
			System.out.println("put sold = " + (newCapacity-battery.getMaxCapacity()));
			battery.setSumSell(battery.getSumSell() + (newCapacity-battery.getMaxCapacity()));
			// divide by 100 because c -> € Divide 1000 w -> kw
			battery.setEarned(battery.getEarned() + (newCapacity-battery.getMaxCapacity()) * tickinfo.getEnergyPriceBuy()/100.0/1000 );
			newCapacity = battery.getMaxCapacity();
		}
		battery.setCurrentCapacity(newCapacity);
		
		batteryDAO.save(battery);
		System.out.println("AFTER:   " + battery.toString());
		
		return buySell;
	}
	
	private int getTickInterval() {
		boolean userIsPremium = user.isUserIsPremium();
		if(userIsPremium) {
			return Constants.TICKER_INTERVALL_PREMIUM_SECONDS;
		} else {
			return Constants.TICKER_INTERVALL_STANDARD_SECONDS;
		}
	}




		
}
