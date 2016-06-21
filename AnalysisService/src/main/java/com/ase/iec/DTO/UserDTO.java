package com.ase.iec.DTO;

import com.ase.iec.persistence.entity.User;

public class UserDTO {
	
	private Long id;
	private String username;
	
	
	public UserDTO() {
		super();
	}
	
	public UserDTO(User user) {
		super();
		id = user.getId();
		username = user.getUsername();
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
	
	
}
