package com.ase.iec.DTO;

import java.util.ArrayList;
import java.util.List;

import com.ase.iec.persistence.entity.Task;
import com.ase.iec.persistence.entity.User;
import com.ase.iec.util.UtilAddresses;

public class UserInfoDTO{

	
	private Long id;
	
	private String username;
	
	private String igPlusServiceAddress;
	private String homeautomationAddress;
	private String energyPriceServiceAddress;
	private String weatherServiceAddress;
	private String latitude;
	private String longitude;

	private List<TaskDTO> tasks = new ArrayList<TaskDTO>();
	

	private BatteryDTO battery;
	
	private List<String> shared = new ArrayList<String>();

	
	public UserInfoDTO(){
		
	}

	

	public UserInfoDTO(User u, List<User> users) {
		super();
		this.id = u.getId();
		this.username = u.getUsername();
		this.igPlusServiceAddress = u.getIgPlusServiceAddress();
		this.homeautomationAddress = u.getHomeautomationAddress();
		this.energyPriceServiceAddress = u.getEnergyPriceServiceAddress();
		this.latitude = u.getLatitude();
		this.longitude = u.getLongitude();
		for(Task t : u.getTask())
			this.tasks.add(new TaskDTO(t));
		if(u.getBattery() != null)
			this.battery = new BatteryDTO(u.getBattery());
		this.weatherServiceAddress = UtilAddresses.weatherServiceAddress;
		for(User user : users)
			shared.add(user.getUsername());
	}



	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getIgPlusServiceAddress() {
		return igPlusServiceAddress;
	}


	public void setIgPlusServiceAddress(String igPlusServiceAddress) {
		this.igPlusServiceAddress = igPlusServiceAddress;
	}


	public String getHomeautomationAddress() {
		return homeautomationAddress;
	}


	public void setHomeautomationAddress(String homeautomationAddress) {
		this.homeautomationAddress = homeautomationAddress;
	}


	public String getEnergyPriceServiceAddress() {
		return energyPriceServiceAddress;
	}


	public void setEnergyPriceServiceAddress(String energyPriceServiceAddress) {
		this.energyPriceServiceAddress = energyPriceServiceAddress;
	}


	public String getLatitude() {
		return latitude;
	}


	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}


	public String getLongitude() {
		return longitude;
	}


	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}


	public List<TaskDTO> getTasks() {
		return tasks;
	}


	public void setTasks(List<TaskDTO> tasks) {
		this.tasks = tasks;
	}


	public BatteryDTO getBattery() {
		return battery;
	}


	public void setBattery(BatteryDTO battery) {
		this.battery = battery;
	}



	public String getWeatherServiceAddress() {
		return weatherServiceAddress;
	}



	public void setWeatherServiceAddress(String weatherServiceAddress) {
		this.weatherServiceAddress = weatherServiceAddress;
	}



	public List<String> getShared() {
		return shared;
	}



	public void setShared(List<String> shared) {
		this.shared = shared;
	}
	
}
