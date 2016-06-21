package com.ase.iec.service;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;

@Service
public class NotificationService {

	AmazonSNSClient snsClient;

	public void sendNotification(String text) {
		snsClient = new AmazonSNSClient(new ProfileCredentialsProvider());
		snsClient.setRegion(Region.getRegion(Regions.US_WEST_2));
		// publish
		PublishRequest publishRequest = new PublishRequest("TODO INSERT TOPIC ARN", text);
		PublishResult publishResult = snsClient.publish(publishRequest);

	}
}
