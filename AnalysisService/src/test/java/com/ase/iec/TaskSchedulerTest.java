package com.ase.iec;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.ase.iec.Shared.weatherJSON.Clouds;
import com.ase.iec.persistence.entity.Task;
import com.ase.iec.persistence.entity.Task.State;
import com.ase.iec.taskscheduling.TaskScheduler;

public class TaskSchedulerTest {
	
	public TaskScheduler taskScheduler;
	
	@Before
	public void init() throws Exception {
		taskScheduler = new TaskScheduler();
	}
	
	@Test
	public void test1() {
		List<Task> inputTasks = new ArrayList<Task>();
		inputTasks.add(new Task(null, null, null, null, "Task1", 100, 150, new Date(1465644144000L),
				new Date(1465534124000L), null, null, null, null, State.PLANNED));
		inputTasks.add(new Task(null, null, null, null, "Task2", 100, 150, new Date(1465654144000L),
				new Date(1465434124000L), null, null, null, null, State.PLANNED));
		inputTasks.add(new Task(null, null, null, null, "Task3", 100, 150, new Date(1465664144000L),
				new Date(1465334124000L), null, null, null, null, State.PLANNED));
		inputTasks.add(new Task(null, null, null, null, "Task4", 100, 150, new Date(1465674144000L),
				new Date(1465234124000L), null, null, null, null, State.PLANNED));
		inputTasks.add(new Task(null, null, null, null, "Task5", 100, 150, new Date(1465684144000L),
				new Date(1465134124000L), null, null, null, null, State.PLANNED));
		inputTasks.add(new Task(null, null, null, null, "Task6", 100, 150, new Date(1465694144000L),
				new Date(1465034124000L), null, null, null, null, State.PLANNED));

		double batteryCurrentCapacity = 300; // min: 0, max: 100
		double energyProduction = 200; // // min: 0, max: im setup gesetzt
		double energyPriceSell = 55; // min: 3, max: 8
		double energyPriceBuy = 20; // min: 3, max: 8
		double maxCapacity = 300;
		Clouds cloudinessForecast = new Clouds();
		cloudinessForecast.setAll(13);
		long fakeTime = new Date().getTime();
		List<Task> tasksToStart = this.taskScheduler.getTasksToBeScheduled(inputTasks, fakeTime, batteryCurrentCapacity,
				energyProduction, energyPriceSell, energyPriceBuy, cloudinessForecast.getAll(), maxCapacity);
		
		System.out.println(tasksToStart);
	}


	@After
	public void clean() throws Exception {
		
	}

}
