package com.ase.iec.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ase.iec.MyUserDetailsService;
import com.ase.iec.DTO.LoginDTO;
import com.ase.iec.DTO.UserDTO;
import com.ase.iec.DTO.UserInfoDTO;
import com.ase.iec.persistence.DAO.UserDAO;
import com.ase.iec.persistence.entity.User;

@RestController
public class UserController {

	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private MyUserDetailsService userDetails;
	
	@RequestMapping(value = "/current")
	@ResponseBody
	public ResponseEntity<UserDTO> user(Principal user) {
		
		if (user != null) {
			
			User current = userDAO.findUserByUsername(user.getName());			
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();						
			return new ResponseEntity<>(new UserDTO(current), HttpStatus.OK);
		} else {			
			return new ResponseEntity<>((UserDTO) null, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public void logout(HttpServletRequest request) {
		SecurityContextHolder.clearContext();
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<UserDTO> registerUser(@RequestBody LoginDTO user) {

		if (user != null) {
			User current = userDAO.save(new User(user.getUsername(), encoder.encode(user.getPassword())));
			
			return new ResponseEntity<>(new UserDTO(current), HttpStatus.OK);
		} else {
			return new ResponseEntity<>((UserDTO) null, HttpStatus.NOT_ACCEPTABLE);
		}
	}
	
	@RequestMapping(value = "/getInfo", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<UserInfoDTO> getInfo() {

		User u = userDetails.getcurrentUser();
		if(u == null)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		List<User> users = userDAO.findByIGPlusAddress(u.getIgPlusServiceAddress());
		
		return new ResponseEntity<UserInfoDTO>(new UserInfoDTO(u,users), HttpStatus.OK);
	}
	
	
	
}
