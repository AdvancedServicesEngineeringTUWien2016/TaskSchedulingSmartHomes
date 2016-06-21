package com.ase.iec.service;

import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import com.ase.iec.Shared.constants.Constants;

@Service
public class PowerOutputComputationService {
	
	
	
	public static double getEstimation(Long fakeTimestamp, Integer cloudiness, double maxProduction, boolean useCloudiness) {
		DateTime dt = new DateTime(fakeTimestamp);  // current time
		int hour = dt.getHourOfDay();
		int minutes = dt.getMinuteOfHour();
		
		maxProduction = maxProduction / (60 * 60);
		
		double hourDouble = (double) hour + ((double) minutes) / 60.0;
		
		if(hourDouble < Constants.HOUR_SUNRISE) {
			return 0;
		} else if(hourDouble > Constants.HOUR_SUNSET) {
			return 0;
		} else {
			int p = Constants.HOUR_SUNRISE;
			int q = Constants.HOUR_SUNSET;
			double x = hourDouble;
			// quadratic function with 0 at p and q and a maximum of 1 in the middle
			double percent = (4*(p-x)*(-q+x))/(Math.pow(p-q, 2));
			if(!useCloudiness) {
				return percent * maxProduction;
			} else {
				return percent * maxProduction * ( (100 - ((double) cloudiness))/100);
			}
		}
		

	}

}
