package com.ase.iec.DTO;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.aspectj.weaver.tools.Trace;

import com.ase.iec.persistence.entity.Battery;
import com.ase.iec.persistence.entity.User;

public class BatteryDTO {

	private Long id;
		
	private double maxCapacity = 0; //W/h
	
	private double currentCapacity = 0; //W/h
	
	private double sumBuy;
	private double sumSell;
	
	private double earned; //€
	private double spent; //€
	
	public BatteryDTO() {
		
	}

	
	public BatteryDTO(Battery b) {
		super();
		this.id = b.getId();
		
		this.maxCapacity = b.getMaxCapacity();
		this.currentCapacity = b.getCurrentCapacity();
		this.sumBuy = b.getSumBuy();
		this.sumSell = b.getSumSell();
		this.earned = b.getEarned();
		this.spent = b.getSpent();
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}



	public double getMaxCapacity() {
		return maxCapacity;
	}

	public void setMaxCapacity(double maxCapacity) {
		this.maxCapacity = maxCapacity;
	}

	public double getCurrentCapacity() {
		return currentCapacity;
	}

	public void setCurrentCapacity(double currentCapacity) {
		this.currentCapacity = currentCapacity;
	}

	public double getSumBuy() {
		return sumBuy;
	}

	public void setSumBuy(double sumBuy) {
		this.sumBuy = sumBuy;
	}

	public double getSumSell() {
		return sumSell;
	}

	public void setSumSell(double sumSell) {
		this.sumSell = sumSell;
	}

	public double getEarned() {
		return earned;
	}

	public void setEarned(double earned) {
		this.earned = earned;
	}

	public double getSpent() {
		return spent;
	}

	public void setSpent(double spent) {
		this.spent = spent;
	}
	
	
	
	
		
}
