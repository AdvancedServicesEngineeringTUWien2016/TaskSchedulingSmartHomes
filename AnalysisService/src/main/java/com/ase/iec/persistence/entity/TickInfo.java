package com.ase.iec.persistence.entity;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.ase.iec.DTO.TaskDTO;
import com.ase.iec.Shared.weatherJSON.WeatherDTO;
import com.ase.iec.persistence.entity.Battery.BuySell;

@Entity
public class TickInfo {

	@Id
	@GeneratedValue
	private Long id;
	
	@Temporal(value = TemporalType.TIMESTAMP)
	private Date timestamp;
	
	private int clouds;
	private double weatherAccuracy;
	private double energyProduction;
	private double energyPriceBuy;
	private double energyPriceSell;
	

	private double batteryCurrentCapacity;
	private double batteryMaxCapacity;
	
	
	@ElementCollection
	private Map<BuySell,Double> buySellMap;
		
	@OneToMany
	private List<TaskDTO> startedTasks;
	@OneToMany
	private List<TaskDTO> runningTasks;
	
	public TickInfo() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getEnergyProduction() {
		return energyProduction;
	}

	public void setEnergyProduction(double energyProduction) {
		this.energyProduction = energyProduction;
	}

	public double getEnergyPriceBuy() {
		return energyPriceBuy;
	}

	public void setEnergyPriceBuy(double energyPriceBuy) {
		this.energyPriceBuy = energyPriceBuy;
	}

	public double getEnergyPriceSell() {
		return energyPriceSell;
	}

	public void setEnergyPriceSell(double energyPriceSell) {
		this.energyPriceSell = energyPriceSell;
	}

	
	public Map<BuySell, Double> getBuySellMap() {
		return buySellMap;
	}

	public void setBuySellMap(Map<BuySell, Double> buySellMap) {
		this.buySellMap = buySellMap;
	}

	public List<TaskDTO> getStartedTasks() {
		return startedTasks;
	}

	public void setStartedTasks(List<TaskDTO> startedTasks) {
		this.startedTasks = startedTasks;
	}

	public List<TaskDTO> getRunningTasks() {
		return runningTasks;
	}

	public void setRunningTasks(List<TaskDTO> runningTasks) {
		this.runningTasks = runningTasks;
	}

	public double getBatteryCurrentCapacity() {
		return batteryCurrentCapacity;
	}

	public void setBatteryCurrentCapacity(double batteryCurrentCapacity) {
		this.batteryCurrentCapacity = batteryCurrentCapacity;
	}

	public double getBatteryMaxCapacity() {
		return batteryMaxCapacity;
	}

	public void setBatteryMaxCapacity(double batteryMaxCapacity) {
		this.batteryMaxCapacity = batteryMaxCapacity;
	}

	public int getClouds() {
		return clouds;
	}

	public void setClouds(int clouds) {
		this.clouds = clouds;
	}

	public double getWeatherAccuracy() {
		return weatherAccuracy;
	}

	public void setWeatherAccuracy(double weatherAccuracy) {
		this.weatherAccuracy = weatherAccuracy;
	}
	
	
	
}
