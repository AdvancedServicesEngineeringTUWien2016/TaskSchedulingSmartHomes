package com.ase.iec.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ase.iec.persistence.entity.User;
import java.lang.String;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	 User findByUsername(String username);
	 List<User> findByIgPlusServiceAddress(String igplusserviceaddress);
	
}