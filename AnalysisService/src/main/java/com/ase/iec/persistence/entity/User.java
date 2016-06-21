package com.ase.iec.persistence.entity;

import java.util.Collection;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity
public class User implements UserDetails{

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	private String username;
	private String password;
	
	
	private String igPlusServiceAddress;
	private String homeautomationAddress;
	private String energyPriceServiceAddress;
	private String latitude;
	private String longitude;
	private boolean userIsPremium;
	
	@OneToMany(mappedBy="user", fetch=FetchType.EAGER)
	private List<Task> tasks;
	
	@ManyToOne
	private Battery battery;

	
	public User(){
		
	}
	
	public User( String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public User(String username, String password,
			String igPlusServiceAddress, String homeautomationAddress, String energyPriceServiceAddress,
			String latitude, String longitude, List<Task> tasks, boolean userIsPremium) {
		super();
		this.username = username;
		this.password = password;
		this.igPlusServiceAddress = igPlusServiceAddress;
		this.energyPriceServiceAddress = energyPriceServiceAddress;
		this.homeautomationAddress = homeautomationAddress;
		this.latitude = latitude;
		this.longitude = longitude;
		this.tasks = tasks;
		this.userIsPremium = userIsPremium;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIgPlusServiceAddress() {
		return igPlusServiceAddress;
	}

	public void setIgPlusServiceAddress(String igPlusServiceAddress) {
		this.igPlusServiceAddress = igPlusServiceAddress;
	}

	public String getHomeautomationAddress() {
		return homeautomationAddress;
	}

	public void setHomeautomationAddress(String homeautomationAddress) {
		this.homeautomationAddress = homeautomationAddress;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public List<Task> getTask() {
		return tasks;
	}

	public void setTask(List<Task> tasks) {
		this.tasks = tasks;
	}
	
	

	public Battery getBattery() {
		return battery;
	}

	public void setBattery(Battery battery) {
		this.battery = battery;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public String getEnergyPriceServiceAddress() {
		return energyPriceServiceAddress;
	}

	public void setEnergyPriceServiceAddress(String energyPriceServiceAddress) {
		this.energyPriceServiceAddress = energyPriceServiceAddress;
	}

	public boolean isUserIsPremium() {
		return userIsPremium;
	}

	public void setUserIsPremium(boolean userIsPremium) {
		this.userIsPremium = userIsPremium;
	}
	
	
	
	
	
}
