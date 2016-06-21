package com.ase.iec.persistence.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.iec.persistence.TaskRepository;
import com.ase.iec.persistence.UserRepository;
import com.ase.iec.persistence.entity.Task;
import com.ase.iec.persistence.entity.Task.State;

@Service
public class TaskDAO implements GenericDAO<Task> {

	private static TaskRepository repository;
	
	@Autowired
    public TaskDAO(TaskRepository repository) {
        this.repository = repository;
    }
	
	@Override
	public Task findById(Long id) {
		return repository.findOne(id);
		
	}

	@Override
	public List<Task> findAll() {
		return repository.findAll();
	}

	@Override
	public Task save(Task entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Task entity) {
		repository.delete(entity);
	}
	
	public List<Task> findAllByUserAndState(Long userID, State state){
		return repository.findByUserAndState(userID, state);
	}

}
