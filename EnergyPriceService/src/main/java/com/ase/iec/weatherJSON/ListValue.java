
package com.ase.iec.weatherJSON;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ase.iec.Shared.weatherJSON.Clouds;
import com.ase.iec.Shared.weatherJSON.MainData;
import com.ase.iec.Shared.weatherJSON.Weather;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class ListValue {

 
    @JsonProperty("clouds")
    private Clouds clouds;
    
    @JsonProperty("weather")
    private List<Weather> weather;
    
    @JsonProperty("main")
    private MainData mainData;
   
    public ListValue() {
    }

    /**
     * 
     * @param clouds
     * @param dt
     * @param wind
     * @param sys
     * @param dtTxt
     * @param weather
     * @param rain
     * @param main
     */
    public ListValue(Clouds clouds, List<Weather> weather, MainData mainData) {
        this.clouds = clouds;
        this.weather = weather;
        this.mainData = mainData;
    }

    
    /**
     * 
     * @return
     *     The clouds
     */
    @JsonProperty("clouds")
    public Clouds getClouds() {
        return clouds;
    }

    /**
     * 
     * @param clouds
     *     The clouds
     */
    @JsonProperty("clouds")
    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    /**
     * 
     * @return
     *     The clouds
     */
    @JsonProperty("weather")
    public List<Weather> getWeather() {
        return weather;
    }

    /**
     * 
     * @param clouds
     *     The clouds
     */
    @JsonProperty("weather")
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
 
    /**
     * 
     * @return
     *     The clouds
     */
    @JsonProperty("main")
    public MainData getMainData() {
        return mainData;
    }

    /**
     * 
     * @param clouds
     *     The clouds
     */
    @JsonProperty("main")
    public void setMainData(MainData mainData) {
        this.mainData = mainData;
    }
    

}
