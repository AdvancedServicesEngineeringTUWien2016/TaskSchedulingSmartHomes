package com.ase.iec.persistence.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.iec.DTO.TaskDTO;
import com.ase.iec.persistence.TaskRepository;
import com.ase.iec.persistence.TaskDTORepository;
import com.ase.iec.persistence.entity.Task;
import com.ase.iec.persistence.entity.Task.State;

@Service
public class TaskDTODAO implements GenericDAO<TaskDTO> {

	private static TaskDTORepository repository;
	
	@Autowired
    public TaskDTODAO(TaskDTORepository repository) {
        this.repository = repository;
    }
	
	@Override
	public TaskDTO findById(Long id) {
		return repository.findOne(id);
		
	}

	@Override
	public List<TaskDTO> findAll() {
		return repository.findAll();
	}

	@Override
	public TaskDTO save(TaskDTO entity) {
		return repository.save(entity);
	}

	public List<TaskDTO> saveAll(List<TaskDTO> entity) {
		return repository.save(entity);
	}
	
	@Override
	public void delete(TaskDTO entity) {
		repository.delete(entity);
	}
	

}
