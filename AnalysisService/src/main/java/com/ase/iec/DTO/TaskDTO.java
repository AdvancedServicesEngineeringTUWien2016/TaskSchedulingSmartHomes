package com.ase.iec.DTO;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ase.iec.persistence.entity.Task;
import com.ase.iec.persistence.entity.Task.State;
import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
public class TaskDTO {
	@Id
	@GeneratedValue
	private Long id;
	private Long taskId;
	private String devicename;	
	private String homeAutomationEndpoint;	
	private String taskname;	
	private double duration;	
	private double energyUsage;
	private String startCommand;
	private String stopCommand;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date earliestStartTime;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date latestFinishTime;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date startTime;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date endTime;
	
	public TaskDTO() {
		super();
	}
	
	public TaskDTO(Task t) {
		super();
		taskId = t.getId();
		devicename = t.getDevicename();
		homeAutomationEndpoint = t.getHomeAutomationEndpoint();
		taskname = t.getTaskname();
		duration = t.getDuration();
		energyUsage = t.getEnergyUsage();
		startCommand = t.getStartCommand();
		stopCommand = t.getStopCommand();
		if(t.getState() != null)
			state = t.getState();
		if(t.getLatestFinishTime() != null)
			latestFinishTime = t.getLatestFinishTime();
		if(t.getStartTime() != null)
			startTime = t.getStartTime();
		if(t.getEndTime() != null)
			endTime = t.getEndTime();
		if(t.getEarliestStartTime() != null)
			earliestStartTime = t.getEarliestStartTime();		
		if(t.getLatestFinishTime() != null)
			latestFinishTime = t.getLatestFinishTime();
		
		
	}
	
	
	public String getDevicename() {
		return devicename;
	}
	public void setDevicename(String devicename) {
		this.devicename = devicename;
	}
	public String getHomeAutomationEndpoint() {
		return homeAutomationEndpoint;
	}
	public void setHomeAutomationEndpoint(String homeAutomationEndpoint) {
		this.homeAutomationEndpoint = homeAutomationEndpoint;
	}
	public String getTaskname() {
		return taskname;
	}
	public void setTaskname(String taskname) {
		this.taskname = taskname;
	}
	public double getDuration() {
		return duration;
	}
	public void setDuration(double duration) {
		this.duration = duration;
	}
	public double getEnergyUsage() {
		return energyUsage;
	}
	public void setEnergyUsage(double energyUsage) {
		this.energyUsage = energyUsage;
	}
	public String getStartCommand() {
		return startCommand;
	}
	public void setStartCommand(String startCommand) {
		this.startCommand = startCommand;
	}
	public String getStopCommand() {
		return stopCommand;
	}
	public void setStopCommand(String stopCommand) {
		this.stopCommand = stopCommand;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getLatestFinishTime() {
		return latestFinishTime;
	}

	public void setLatestFinishTime(Date latestFinishTime) {
		this.latestFinishTime = latestFinishTime;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getEarliestStartTime() {
		return earliestStartTime;
	}

	public void setEarliestStartTime(Date earliestStartTime) {
		this.earliestStartTime = earliestStartTime;
	}
	
	
	
	
	
}
