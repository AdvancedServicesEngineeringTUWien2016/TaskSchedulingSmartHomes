package com.ase.iec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ase.iec.MyUserDetailsService;
import com.ase.iec.DTO.AnaysisSetupDTO;
import com.ase.iec.Shared.dto.IGSetupDTO;
import com.ase.iec.Shared.dto.StringResponse;
import com.ase.iec.Shared.dto.WeatherServiceDTO;
import com.ase.iec.persistence.DAO.BatteryDAO;
import com.ase.iec.persistence.DAO.UserDAO;
import com.ase.iec.persistence.entity.Battery;
import com.ase.iec.persistence.entity.User;
import com.ase.iec.util.UtilAddresses;

@RestController
public class SetupController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BatteryDAO batteryDAO;
	
	@Autowired
	private MyUserDetailsService userDetails;
	
	@Autowired 
	private UtilAddresses util;
	
	@RequestMapping(value = "setupAnalysis/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<StringResponse> setupAnalysis(@RequestBody AnaysisSetupDTO setup) {

		User user = userDetails.getcurrentUser();
		if (user == null)
			return new ResponseEntity<>(new StringResponse("No User found!"), HttpStatus.NOT_FOUND);
		
		
		//check ips
		if(setup.getHomeautomationAddress() == null || setup.getIgPlusServiceAddress() == null || setup.getPriceServiceAddress() == null || setup.getWeatherServiceAddress() == null)
			return new ResponseEntity<>(new StringResponse("One of the Service Adresses is null!"), HttpStatus.NOT_FOUND);
				
		user.setHomeautomationAddress(setup.getHomeautomationAddress());
		user.setIgPlusServiceAddress(setup.getIgPlusServiceAddress());
		
		user.setEnergyPriceServiceAddress(setup.getPriceServiceAddress());
		UtilAddresses.weatherServiceAddress = setup.getWeatherServiceAddress();
		
		
		//check location data
		if(setup.getLatitude() == null || setup.getLongitude() == null)
			return new ResponseEntity<>(new StringResponse("Location must not be null!"), HttpStatus.NOT_FOUND);
		
		user.setLatitude(setup.getLatitude());
		user.setLongitude(setup.getLongitude());
		
		if(setup.getPremiumUser().equals("yes")) {
			user.setUserIsPremium(true);
		} else {
			user.setUserIsPremium(false);
		}
		
		//findbattery setup
		Battery b = batteryDAO.findByIPPlusAddress(setup.getIgPlusServiceAddress());
		if(b == null)
			return new ResponseEntity<>(new StringResponse("No battery found under this ig plus address!"), HttpStatus.NOT_FOUND);
		
	
		user.setBattery(b);
	
		batteryDAO.save(b);
		
		userDAO.save(user);	
		
		
		return new ResponseEntity<>(new StringResponse("Analyse Service Setup done!"), HttpStatus.OK);
	}
	
	@RequestMapping(value = "setupIG/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<StringResponse> setupIG(@RequestBody IGSetupDTO setup) {

		//check battery data
		if(setup.getBatteryCapacity() == null || setup.getBatteryLoad() == null)
			return new ResponseEntity<>(new StringResponse("Battery data must not be null"), HttpStatus.NOT_FOUND);
				
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> response = restTemplate.postForEntity("http://" + setup.getIgPlusServiceAddress() + "/igplus/setupIG/", setup, String.class);
		
			
		Battery b = new Battery(null, setup.getBatteryCapacity(), setup.getBatteryCapacity()/100*setup.getBatteryLoad(), setup.getIgPlusServiceAddress());
		
		Battery existingBattery = batteryDAO.findByIPPlusAddress(setup.getIgPlusServiceAddress());
		//its possible to overwrite setup
		if(existingBattery != null)
			b.setId(existingBattery.getId());
		
		batteryDAO.save(b);
								
		return new ResponseEntity<>(new StringResponse(response.getBody()), response.getStatusCode());
	}
	
//	@RequestMapping(value = "setupBattery/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
//	@ResponseBody
//	public ResponseEntity<String> setupBattery(@RequestBody BatterySetupDTO setup) {
//
//		RestTemplate restTemplate = new RestTemplate();
//		ResponseEntity<String> response = restTemplate.postForEntity("http://" + setup.getBatteryServiceAddress() + "/igplus/setupBattery/", setup, String.class);
//		
//		return new ResponseEntity<String>("{\"data\" : \"" + response.getBody() + "\"}", response.getStatusCode());
//	}
//	
	@RequestMapping(value = "setupWeather/", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<StringResponse> setupWeather(@RequestBody WeatherServiceDTO setup) {

		RestTemplate restTemplate = new RestTemplate();
		System.out.println("http://" + setup.getWeatherServiceAddress() + "/setupWeather");
		ResponseEntity<StringResponse> response = restTemplate.postForEntity("http://" + setup.getWeatherServiceAddress() + "/setupWeather", setup, StringResponse.class);
		
		return new ResponseEntity<>(response.getBody(), response.getStatusCode());
	}
}
