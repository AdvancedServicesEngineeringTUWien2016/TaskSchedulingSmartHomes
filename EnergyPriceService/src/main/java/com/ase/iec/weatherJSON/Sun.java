package com.ase.iec.weatherJSON;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Sun {

    @JsonProperty("sunrise")
    private Long sunrise;
    
    
    @JsonProperty("sunset")
    private Long sunset;
    
    /**
     * No args constructor for use in serialization
     * 
     */
    public Sun() {
    }

	public Long getSunrise() {
		return sunrise;
	}

	public void setSunrise(Long sunrise) {
		this.sunrise = sunrise;
	}

	public Long getSunset() {
		return sunset;
	}

	public void setSunset(Long sunset) {
		this.sunset = sunset;
	}
 
    
}