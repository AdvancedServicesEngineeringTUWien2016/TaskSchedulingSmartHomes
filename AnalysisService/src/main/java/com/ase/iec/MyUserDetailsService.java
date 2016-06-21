package com.ase.iec;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import com.ase.iec.persistence.DAO.UserDAO;
import com.ase.iec.persistence.entity.User;

@Component
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserDAO userService;

	@Override
	public UserDetails loadUserByUsername(String username){

		User user = userService.findUserByUsername(username);
		System.out.println("loaduser by username" + user.getPassword());
		return user;
    	 
    }
    
    public User getcurrentUser(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		

		return userService.findUserByUsername(auth.getName());
		
	}
    
  
}
