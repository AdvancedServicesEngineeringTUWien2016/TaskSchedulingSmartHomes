package com.ase.iec.controller;

import java.util.Date;
import java.util.concurrent.ThreadLocalRandom;

import javax.annotation.PostConstruct;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ase.iec.Shared.dto.EnergyDTO;
import com.ase.iec.Shared.dto.IGSetupDTO;
import com.ase.iec.Shared.weatherJSON.WeatherDTO;
import com.ase.iec.service.PowerOutputComputationService;

@RestController
@RequestMapping("/igplus/")
public class IGPlusController {

	private double maxProduction;
	
	private int accuracyThreshold = 60; //in %
	
	private String weatherServiceAddress;
	private String lat;
	private String lon;
	
	
	
	@RequestMapping(value = "getData", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<EnergyDTO> getProductionData(@RequestParam("timestamp") Long timestamp) {
		
		System.out.println("Calling getProductionData with (fake)timestamp: " + timestamp);
				
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<WeatherDTO> weather = null;
		
		try{
			weather = restTemplate.getForEntity("http://"+weatherServiceAddress + "/getWeatherData/" + lat + "/" + lon, WeatherDTO.class);
		}catch(Exception e){
			
		}
		
		double estimatedEnergy = 0;
		double accuracy = 0;
		if(weather != null){
			accuracy = weather.getBody().getAccuracy();
			estimatedEnergy = PowerOutputComputationService.getEstimation(timestamp, weather.getBody().getList().get(0).getClouds().getAll(), maxProduction, accuracy > accuracyThreshold);

		}
		else{		
			estimatedEnergy = PowerOutputComputationService.getEstimation(timestamp, 0, maxProduction, accuracy > accuracyThreshold);
		}
			
		System.out.println("production: " + estimatedEnergy);
		return new ResponseEntity<EnergyDTO>(new EnergyDTO(estimatedEnergy), HttpStatus.OK);
	}

	
	@RequestMapping(value = "setupIG/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> setupIG(@RequestBody IGSetupDTO setup) {

		if(setup.getWeatherServiceAddress() == null)
			return new ResponseEntity<String>("WeatherService address must not be null!", HttpStatus.NOT_FOUND);
		weatherServiceAddress = setup.getWeatherServiceAddress();
		
		if(setup.getLatitude() == null || setup.getLongitude() == null)
			return new ResponseEntity<String>("Location must not be null!", HttpStatus.NOT_FOUND);
		lon = setup.getLongitude();
		lat = setup.getLatitude();
		
		if(setup.getMaxEnergyProduction() == null)
			return new ResponseEntity<String>("Max possible energy pruduction must be set!", HttpStatus.NOT_FOUND);
		
		maxProduction = setup.getMaxEnergyProduction();
		
		
		if(setup.getWeatherAccuracyThreshold() != null);
			accuracyThreshold = setup.getWeatherAccuracyThreshold();

		return new ResponseEntity<String>("IGPlus Setup done!", HttpStatus.OK);
		
	}
	
}
