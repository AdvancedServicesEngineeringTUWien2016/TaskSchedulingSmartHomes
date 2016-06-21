package com.ase.iec.taskscheduling;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ase.iec.Shared.constants.Constants;
import com.ase.iec.Shared.dto.EnergyDTO;
import com.ase.iec.Shared.weatherJSON.WeatherDTO;
import com.ase.iec.persistence.DAO.UserDAO;
import com.ase.iec.persistence.entity.Task;
import com.ase.iec.persistence.entity.TickInfo;
import com.ase.iec.persistence.entity.User;
import com.ase.iec.util.UtilAddresses;

@Service
public class TaskScheduler {

	@Autowired
	private UserDAO userDAO;

	public List<Task> getNextTasks(String ipPlusAddress, TickInfo tickinfo) {

		List<User> userInSameHouse = userDAO.findByIGPlusAddress(ipPlusAddress);
		List<Task> allTaks = new ArrayList<Task>();

		for (User u : userInSameHouse)
			allTaks.addAll(u.getTask());

		List<Task> possibleTasksToSchedule = new ArrayList<Task>();

		for (Task t : allTaks) {
			if(t.getEarliestStartTime() == null)
				break;
			if (t.getEarliestStartTime().before(tickinfo.getTimestamp())) {
				possibleTasksToSchedule.add(t);
				System.out.println(
						"Adding task to possibleTasksToSchedule: " + t.getTaskname() + ", " + t.getEnergyUsage() + ", " + t.getState().toString());
			}
		}

		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<WeatherDTO> weather = null;
		
		try{
			weather = restTemplate.getForEntity(
					"http://" + UtilAddresses.weatherServiceAddress + "/getWeatherData/"
						+ userInSameHouse.get(0).getLatitude() + "/" + userInSameHouse.get(0).getLongitude(),
				WeatherDTO.class);
		}catch(Exception e){
			
		}
		
	
		if (weather != null){
			System.out.println("set clouds: " + weather.getBody().getList().get(0).getClouds().getAll());
			tickinfo.setClouds(weather.getBody().getList().get(0).getClouds().getAll());
			tickinfo.setWeatherAccuracy(weather.getBody().getAccuracy());
		}
		
		return getTasksToBeScheduled(possibleTasksToSchedule, tickinfo.getTimestamp().getTime(),
				tickinfo.getBatteryCurrentCapacity(), tickinfo.getEnergyProduction(), tickinfo.getEnergyPriceSell(),
				tickinfo.getEnergyPriceBuy(), tickinfo.getClouds(), tickinfo.getBatteryMaxCapacity());

	}

	public List<Task> getTasksToBeScheduled(List<Task> inputTasks, long timestamp, double batteryCurrentCapacity,
			double energyProduction, double energyPriceSell, double energyPriceBuy, int cloudinessForecast, double batteryMaxCapacity) {
		// clouds.all 100 -> 100 % bewoelkt

		List<Task> tasksToReturn = new ArrayList<Task>();

		final int TIME_BETWEEN_TICKS = Constants.TICKER_INTERVALL_STANDARD_SECONDS; // seconds
		final int MINUTES_TO_MS = 60 * 1000;

		double energyUsedSoFar = 0.0;

		for (Task t : inputTasks) {
			Date now = new Date(timestamp);
			if (now.getTime() + t.getDuration() * MINUTES_TO_MS + TIME_BETWEEN_TICKS * 1.5 * 1000 > t
					.getLatestFinishTime().getTime()) {
				// last chance to submit task, do it!
				System.out.println("Last change to schedule task: " + t.getTaskname());
				System.out.println("Reason: latest finish time " + t.getLatestFinishTime() + ", now: " + now
						+ ", duration: " + t.getDuration() + " minutes");
				tasksToReturn.add(t);
				energyUsedSoFar += t.getEnergyUsage();
			}
		}

		for (Task t : inputTasks) {
			if (tasksToReturn.contains(t)) {
				continue;
			}

			if ((energyUsedSoFar * 1.5) * Constants.TICKER_INTERVALL_STANDARD_SECONDS / (60 * 60) > energyProduction) {
				System.out.println("Energy used so far: " + energyUsedSoFar + "Wh, batteryCurrentCapacity: "
						+ batteryCurrentCapacity);
				// run no more tasks
				break;
			}

			// we can run task now or later
			double probabilityToRunTask = 0.5;

			probabilityToRunTask *= ((double) 100 - cloudinessForecast) / 100; // more
																						// clouds
																						// ->
																						// less
																						// likely
			if(t.getEnergyUsage() * Constants.TICKER_INTERVALL_STANDARD_SECONDS / (60 * 60) > (energyProduction - energyUsedSoFar)) {
				probabilityToRunTask *= 0.01;
			}
			
			if(batteryCurrentCapacity >= batteryMaxCapacity) {
				// almost always a good idea to run tasks when battery is full
				probabilityToRunTask = 0.95;
			}

			double random = ThreadLocalRandom.current().nextDouble(0.0, 1.0);

			System.out.println("Random: " + random + ", to reach less than: " + probabilityToRunTask);

			if (random < probabilityToRunTask) {
				tasksToReturn.add(t);
			}
		}

		System.out.println("starting " + tasksToReturn.size() + " tasks");

		return tasksToReturn;
	}

}
