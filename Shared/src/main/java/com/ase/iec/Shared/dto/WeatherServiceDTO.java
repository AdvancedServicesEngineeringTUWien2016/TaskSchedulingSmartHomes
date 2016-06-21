package com.ase.iec.Shared.dto;

public class WeatherServiceDTO {

	private String weatherServiceAddress;
	private Integer weatherAccuracyMin;
	private Integer weatherAccuracyMax;
	private boolean simulate;
	
	public WeatherServiceDTO() {
		super();
	}


	public String getWeatherServiceAddress() {
		return weatherServiceAddress;
	}


	public void setWeatherServiceAddress(String weatherServiceAddress) {
		this.weatherServiceAddress = weatherServiceAddress;
	}


	public Integer getWeatherAccuracyMin() {
		return weatherAccuracyMin;
	}


	public void setWeatherAccuracyMin(Integer weatherAccuracyMin) {
		this.weatherAccuracyMin = weatherAccuracyMin;
	}


	public Integer getWeatherAccuracyMax() {
		return weatherAccuracyMax;
	}


	public void setWeatherAccuracyMax(Integer weatherAccuracyMax) {
		this.weatherAccuracyMax = weatherAccuracyMax;
	}


	public boolean isSimulate() {
		return simulate;
	}


	public void setSimulate(boolean simulate) {
		this.simulate = simulate;
	}
	
	
	
}
