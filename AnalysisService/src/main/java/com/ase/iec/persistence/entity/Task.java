package com.ase.iec.persistence.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ase.iec.DTO.TaskDTO;

@Entity
public class Task {

	public enum State{
		PLANNED, RUNNING, FINISH
	}

	
	@Id
	@GeneratedValue
	private Long id;
	
	@ManyToOne
	private User user;
	
	private String devicename;
	
	private String homeAutomationEndpoint;
	
	private String taskname;
		
	@Override
	public String toString() {
		return "Task [id=" + id + ", taskname=" + taskname + ", duration=" + duration + ", energyUsage=" + energyUsage
				+ ", latestFinishTime=" + latestFinishTime + ", earliestStartTime=" + earliestStartTime + ", startTime="
				+ startTime + ", endTime=" + endTime + "]";
	}

	private double duration;
	
	private double energyUsage;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date latestFinishTime;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date earliestStartTime;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date startTime;
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date endTime;
	
	private String startCommand;
	private String stopCommand;
	
	@Enumerated(EnumType.STRING)
	private State state;
	
	
	public Task(){
		
	}
	
	public Task(TaskDTO dto, User user){
		this.user = user;
		this.devicename = dto.getDevicename();
		this.homeAutomationEndpoint = dto.getHomeAutomationEndpoint();
		this.taskname = dto.getTaskname();
		this.duration = dto.getDuration();
		this.energyUsage = dto.getEnergyUsage();
		this.startCommand = dto.getStartCommand();
		this.stopCommand = dto.getStopCommand();
	}

	public Task(User user, String devicename, String homeAutomationEndpoint,
			String taskname, double duration, double energyUsage,
			String startCommand, String stopCommand) {
		super();
		this.user = user;
		this.devicename = devicename;
		this.homeAutomationEndpoint = homeAutomationEndpoint;
		this.taskname = taskname;
		this.duration = duration;
		this.energyUsage = energyUsage;
		this.startCommand = startCommand;
		this.stopCommand = stopCommand;
	}
	
	public Task(Long id, User user, String devicename, String homeAutomationEndpoint, String taskname, double duration,
			double energyUsage, Date latestFinishTime, Date earliestStartTime, Date startTime, Date endTime,
			String startCommand, String stopCommand, State state) {
		super();
		this.id = id;
		this.user = user;
		this.devicename = devicename;
		this.homeAutomationEndpoint = homeAutomationEndpoint;
		this.taskname = taskname;
		this.duration = duration;
		this.energyUsage = energyUsage;
		this.latestFinishTime = latestFinishTime;
		this.earliestStartTime = earliestStartTime;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startCommand = startCommand;
		this.stopCommand = stopCommand;
		this.state = state;
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Date getEarliestStartTime() {
		return earliestStartTime;
	}

	public void setEarliestStartTime(Date earliestStartTime) {
		this.earliestStartTime = earliestStartTime;
	}
	
	
	
	
	
	
}
