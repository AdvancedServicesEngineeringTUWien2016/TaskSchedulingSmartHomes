package com.ase.iec.controller;

import java.util.Date;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ase.iec.persistence.TickInfoRepository;
import com.ase.iec.persistence.entity.TickInfo;

@RestController
public class StatisticsController {

	@Autowired
	TickInfoRepository tickInfoRepository;

	@RequestMapping(value = "/getStatistics", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<String> getStatistics(
			@RequestParam("day") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate) {
		System.out.println(fromDate);

		return new ResponseEntity<String>("{\"data\" : \"Task added!\"}", HttpStatus.OK);
	}

	@RequestMapping(value = "/getStatistics2", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<TickInfo>> getStatistics2(@RequestParam("day") @DateTimeFormat(pattern = "yyyy-MM-dd") Date fromDate) {
		System.out.println("Retreiving ticks for date " + fromDate);
		
		DateTime todayStart1 = new DateTime(fromDate).withTimeAtStartOfDay();
		DateTime tomorrowStart = new DateTime(fromDate).plusDays( 1 ).withTimeAtStartOfDay();

		java.util.Date todayStart = todayStart1.toDate();
		java.util.Date endDate = tomorrowStart.toDate();

		List<TickInfo> test = tickInfoRepository.findByDate(todayStart, endDate);

		return new ResponseEntity<>(test, HttpStatus.OK);


	}
	

}
