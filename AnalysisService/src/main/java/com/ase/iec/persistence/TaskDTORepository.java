package com.ase.iec.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ase.iec.DTO.TaskDTO;

@Repository
public interface TaskDTORepository extends JpaRepository<TaskDTO, Long>{

	
}