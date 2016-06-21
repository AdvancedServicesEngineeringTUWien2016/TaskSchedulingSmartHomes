package com.ase.iec;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@SpringBootApplication
public class WeatherDataServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherDataServiceApplication.class, args);
		
		
	}
	
	
}
