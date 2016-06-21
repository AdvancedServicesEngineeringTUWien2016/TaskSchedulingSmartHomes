package com.ase.iec.Shared.dto;

public class IGSetupDTO {

	
	private Double maxEnergyProduction;
	private Integer weatherAccuracyThreshold;
	
	private String weatherServiceAddress;
	private String igPlusServiceAddress;
	private String latitude;
	private String longitude;
	private Double batteryCapacity;
	private Double batteryLoad;
	
	public IGSetupDTO() {
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

	public Double getMaxEnergyProduction() {
		return maxEnergyProduction;
	}

	public void setMaxEnergyProduction(double maxEnergyProduction) {
		this.maxEnergyProduction = maxEnergyProduction;
	}

	public Integer getWeatherAccuracyThreshold() {
		return weatherAccuracyThreshold;
	}

	public void setWeatherAccuracyThreshold(int weatherAccuracyThreshold) {
		this.weatherAccuracyThreshold = weatherAccuracyThreshold;
	}

	public String getWeatherServiceAddress() {
		return weatherServiceAddress;
	}


	public void setWeatherServiceAddress(String weatherServiceAddress) {
		this.weatherServiceAddress = weatherServiceAddress;
	}

	public String getIgPlusServiceAddress() {
		return igPlusServiceAddress;
	}


	public void setIgPlusServiceAddress(String igPlusServiceAddress) {
		this.igPlusServiceAddress = igPlusServiceAddress;
	}
	

	public Double getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(Double batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	public Double getBatteryLoad() {
		return batteryLoad;
	}

	public void setBatteryLoad(Double batteryLoad) {
		this.batteryLoad = batteryLoad;
	}
	
}
