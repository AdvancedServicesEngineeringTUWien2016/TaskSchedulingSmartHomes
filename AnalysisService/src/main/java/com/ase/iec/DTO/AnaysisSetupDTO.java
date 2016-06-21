package com.ase.iec.DTO;

public class AnaysisSetupDTO {

	private String weatherServiceAddress;
	private Integer weatherAccuracyThreshold;
	private String priceServiceAddress;
	private String igPlusServiceAddress;
	private String homeautomationAddress;
	private String latitude;
	private String longitude;
	private String premiumUser;
	
	
	
	
	public AnaysisSetupDTO() {
		super();
	}
	
	public String getWeatherServiceAddress() {
		return weatherServiceAddress;
	}
	public void setWeatherServiceAddress(String weatherServiceAddress) {
		this.weatherServiceAddress = weatherServiceAddress;
	}
	public Integer getWeatherAccuracyThreshold() {
		return weatherAccuracyThreshold;
	}
	public void setWeatherAccuracyThreshold(Integer weatherAccuracyThreshold) {
		this.weatherAccuracyThreshold = weatherAccuracyThreshold;
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

	public String getPriceServiceAddress() {
		return priceServiceAddress;
	}

	public void setPriceServiceAddress(String priceServiceAddress) {
		this.priceServiceAddress = priceServiceAddress;
	}
	
	public String getPremiumUser() {
		return premiumUser;
	}

	public void setPremiumUser(String premiumUser) {
		this.premiumUser = premiumUser;
	}

	
	
	
}
