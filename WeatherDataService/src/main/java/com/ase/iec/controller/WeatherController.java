package com.ase.iec.controller;

import java.util.concurrent.ThreadLocalRandom;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ase.iec.Shared.dto.StringResponse;
import com.ase.iec.Shared.dto.WeatherServiceDTO;
import com.ase.iec.Shared.weatherJSON.WeatherDTO;
import com.ase.iec.util.WeatherUtil;


@RestController
@RequestMapping("/")
public class WeatherController {
	
	private static int minAccuracy = 70;
	private static int maxAccuracy = 100;
	private boolean simulate = false;
	private int value = minAccuracy;
	
	
	@RequestMapping(value = "getWeatherData/{lat:.+}/{lon:.+}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<WeatherDTO> getWeatherData(@PathVariable("lat") String latitude, @PathVariable("lon") String longitude ) {
		
		RestTemplate restTemplate = new RestTemplate();		
		
		
		ResponseEntity<WeatherDTO> weather = null;
		
		try{
			weather = restTemplate.getForEntity(WeatherUtil.url + "lat=" + latitude + "&lon=" + longitude + WeatherUtil.apikey, WeatherDTO.class);
		}catch(Exception e){
			
		}
		
		if(weather!=null && simulate){
			value = (int) ThreadLocalRandom.current().nextDouble(value - 10, value + 10);
			if(value<0)
				value = 0;
			if(value>100)
				value = 100;
			weather.getBody().getList().get(0).getClouds().setAll(value);
			System.out.println(weather.getBody().getList().get(0).getClouds().getAll());
		}
		
		
		double random = ThreadLocalRandom.current().nextDouble(minAccuracy, maxAccuracy);
		weather.getBody().setAccuracy(random);
		
		System.out.println(WeatherUtil.url + "lat=" + latitude + "&lon=" + longitude + WeatherUtil.apikey);
		return weather;
	
	}

	@RequestMapping(value = "setupWeather", method = RequestMethod.POST, consumes = " application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<StringResponse> setupWeather(@RequestBody WeatherServiceDTO setup) {
		if(setup.getWeatherAccuracyMin() >= setup.getWeatherAccuracyMax()){
			return new ResponseEntity<StringResponse>(new StringResponse("Weather Service setup not done! Accuracy range wrong!"), HttpStatus.NOT_ACCEPTABLE);

		}
		this.minAccuracy = setup.getWeatherAccuracyMin();
		this.maxAccuracy = setup.getWeatherAccuracyMax();
		this.simulate = setup.isSimulate();
		return new ResponseEntity<StringResponse>(new StringResponse("Weather Service setup done!"), HttpStatus.OK);
	}
	

	
}
