package com.ase.iec.controller;

import java.util.concurrent.ThreadLocalRandom;
import org.joda.time.DateTime;

import javax.websocket.server.PathParam;

import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ase.iec.Shared.dto.EnergyPriceDTO;
import com.ase.iec.util.WeatherUtil;

@RestController
@RequestMapping("/")
public class EnergyPriceController {

	private double min = 3; // in %
	private double max = 8; // c pro kw

	@RequestMapping(value = "getEnergyPrice", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public EnergyPriceDTO getEnergyPrice(@RequestParam long fakeTimestamp) {
		
		double priceSelling = 0;
		double priceBuying = 0;
		
		double basePrice = 0;
		
		DateTime dt = new DateTime(fakeTimestamp);  // current time
		int hour = dt.getHourOfDay();
		int minutes = dt.getMinuteOfHour();
		
		double hourDouble = (double) hour + ((double) minutes) / 60.0;
		
		if(hourDouble < 6) {
			basePrice = 3;
		} else if(hourDouble < 8) {
			basePrice = 5;
		} else if(hourDouble < 11) {
			basePrice = 3;
		} else if(hourDouble < 13) {
			basePrice = 8;
		} else if(hourDouble < 17) {
			basePrice = 4;
		} else if(hourDouble < 19) {
			basePrice = 6;
		} else {
			basePrice = 3;
		}
		
		priceSelling = ThreadLocalRandom.current().nextDouble(basePrice * 0.9, basePrice * 1.1);
		priceBuying = ThreadLocalRandom.current().nextDouble(basePrice * 0.9, basePrice * 1.1) * 1.3;
		
		return new EnergyPriceDTO(priceSelling, priceBuying);
	
	}

}
