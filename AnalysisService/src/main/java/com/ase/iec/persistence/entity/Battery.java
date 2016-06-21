package com.ase.iec.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Battery {

	public enum BuySell{
		BOUGHT, SOLD
	}
	
	@Id
	@GeneratedValue
	private Long id;
	
	private double maxCapacity = 0; //W/h
	
	private double currentCapacity = 0; //W/h
	
	@Column(nullable = false, unique = true)
	private String ipPlusServiceAddress;
	
	private double sumBuy;
	private double sumSell;
	
	private double earned; //€
	private double spent; //€
	
	public Battery() {
		
	}
	public Battery(User user, double maxCapacity, double currentCapacity, String ipPlusServiceAddress) {
		super();
		this.maxCapacity = maxCapacity;
		this.currentCapacity = currentCapacity;
		this.ipPlusServiceAddress = ipPlusServiceAddress;
		sumBuy = 0;
		sumSell = 0;
		spent = 0;
		earned = 0;
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
	public String getIpPlusServiceAddress() {
		return ipPlusServiceAddress;
	}
	public void setIpPlusServiceAddress(String ipPlusServiceAddress) {
		this.ipPlusServiceAddress = ipPlusServiceAddress;
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
	@Override
	public String toString() {
		return "Battery [id=" + id + ", maxCapacity=" + maxCapacity
				+ ", currentCapacity=" + currentCapacity
				+ ", ipPlusServiceAddress=" + ipPlusServiceAddress
				+ ", sumBuy=" + sumBuy + ", sumSell=" + sumSell + ", earned="
				+ earned + ", spent=" + spent + "]";
	}
	
	
	
	
	
		
}
