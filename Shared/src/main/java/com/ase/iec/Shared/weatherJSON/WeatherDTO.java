package com.ase.iec.Shared.weatherJSON;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDTO {

	
	@JsonProperty("city")
    private City city = new City();
  
	@JsonProperty("list")
    private List<ListValue> list = new ArrayList<ListValue>();
	
	@JsonProperty("sys")
    private Sun sun = new Sun();
	
	private double accuracy;
	
    public WeatherDTO() {
    }

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

	public List<ListValue> getList() {
		return list;
	}

	public void setList(List<ListValue> list) {
		this.list = list;
	}

	public double getAccuracy() {
		return accuracy;
	}

	public void setAccuracy(double accuracy) {
		this.accuracy = accuracy;
	}

	public Sun getSun() {
		return sun;
	}

	public void setSun(Sun sun) {
		this.sun = sun;
	}

   
    
}