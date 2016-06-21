package com.ase.iec.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ase.iec.MyUserDetailsService;
import com.ase.iec.DTO.TaskDTO;
import com.ase.iec.Shared.dto.StringResponse;
import com.ase.iec.persistence.DAO.TaskDAO;
import com.ase.iec.persistence.entity.Task;
import com.ase.iec.persistence.entity.Task.State;
import com.ase.iec.persistence.entity.User;

@RestController
public class TaskController {

	@Autowired
	private TaskDAO taskDAO;
	
	@Autowired
	private MyUserDetailsService userDetails;
	
	@RequestMapping(value = "registerTask/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<StringResponse> setupAnalysis(@RequestBody TaskDTO taskDTO) {
	
		User u = userDetails.getcurrentUser();
		if(u == null)
			return new ResponseEntity<>(new StringResponse("No User found!"), HttpStatus.NOT_FOUND);
	
		if (taskDTO.getDevicename() == null || taskDTO.getDuration() == 0 || taskDTO.getEnergyUsage() == 0 || taskDTO.getHomeAutomationEndpoint() == null || taskDTO.getStartCommand() == null || taskDTO.getStopCommand() == null || taskDTO.getTaskname() == null)
			return new ResponseEntity<>(new StringResponse("Illegal arguments!"), HttpStatus.NOT_ACCEPTABLE);
		
		Task t = new Task(taskDTO, u);
		taskDAO.save(t);
		
		return new ResponseEntity<>(new StringResponse("Task added!"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "getTasks/", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<TaskDTO>> getTasks() {
	
		User u = userDetails.getcurrentUser();
		if(u == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		
		List<Task> listTasks = taskDAO.findAll();
		List<TaskDTO> tasks = new ArrayList<TaskDTO>();
		
		for (Task t : listTasks){
			if(t.getUser().equals(u))
				tasks.add(new TaskDTO(t));
		}
				
		return new ResponseEntity<List<TaskDTO>>(tasks, HttpStatus.OK);
				
	}
	
	@RequestMapping(value = "planTask/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<StringResponse> planTask(@RequestBody TaskDTO taskDTO) {
	
		User u = userDetails.getcurrentUser();
		if(u == null)
			return new ResponseEntity<>(new StringResponse("No User found!"), HttpStatus.NOT_FOUND);
	
		if (taskDTO.getTaskId() == null)
			return new ResponseEntity<>(new StringResponse("Task not found!"), HttpStatus.NOT_FOUND);
		
		Task t = taskDAO.findById(taskDTO.getTaskId());
		t.setLatestFinishTime(taskDTO.getLatestFinishTime());
		t.setEarliestStartTime(taskDTO.getEarliestStartTime());
		t.setState(State.PLANNED);
		taskDAO.save(t);
		return new ResponseEntity<>(new StringResponse("Task planned!"), HttpStatus.OK);
	}

	
}
