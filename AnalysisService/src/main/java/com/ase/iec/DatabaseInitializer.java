package com.ase.iec;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ase.iec.persistence.DAO.UserDAO;
import com.ase.iec.persistence.entity.User;

@Component
public class DatabaseInitializer implements InitializingBean {


	@Autowired
	private UserDAO userService;
	
	@Autowired
	private PasswordEncoder encoder;
	
	public void afterPropertiesSet() throws Exception{
//		userService.save(new User("admin", encoder.encode("password")));
//		System.out.println("user added zu database "+ encoder.encode("password"));
		
	}
}
