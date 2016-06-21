package com.ase.iec.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.ase.iec.service.NotificationService;

@RestController
public class NotificationsController {
	
	@Autowired
	NotificationService service;
	

	@RequestMapping(value = "sendTestNotification/", method = RequestMethod.GET)
	@ResponseBody
	public void sendTestNotification() {
		service.sendNotification("This is an example text<b>Strong</b>");
	}
	
}
