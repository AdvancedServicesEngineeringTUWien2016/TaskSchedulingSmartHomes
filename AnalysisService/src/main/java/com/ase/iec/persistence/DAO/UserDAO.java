package com.ase.iec.persistence.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.ase.iec.persistence.UserRepository;
import com.ase.iec.persistence.entity.User;

@Service
public class UserDAO implements GenericDAO<User>{

	private static UserRepository repository;
	
	@Autowired
    public UserDAO(UserRepository repository) {
        this.repository = repository;
    }
	 
	@Override
	public User findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User save(User entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(User entity) {
		repository.delete(entity);
		
	}
	
	public User findUserByUsername(String username){
		return repository.findByUsername(username);
	}
	
	public List<User> findByIGPlusAddress(String address){
		return repository.findByIgPlusServiceAddress(address);
		
	}

	
}
