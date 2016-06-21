package com.ase.iec.persistence;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ase.iec.persistence.entity.Task;
import com.ase.iec.persistence.entity.Task.State;
import com.ase.iec.persistence.entity.User;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

	List<Task> findByHomeAutomationEndpoint(String homeautomationendpoint);
	List<Task> findByUser(User user);
	
	@Query( "select t from Task t where user_id = ?1 and state = ?2" )
	List<Task> findByUserAndState(Long userid, State status);
	
}