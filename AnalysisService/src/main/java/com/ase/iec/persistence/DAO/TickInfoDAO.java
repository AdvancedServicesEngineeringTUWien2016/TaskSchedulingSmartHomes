package com.ase.iec.persistence.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.iec.persistence.TickInfoRepository;
import com.ase.iec.persistence.entity.TickInfo;

@Service
public class TickInfoDAO implements GenericDAO<TickInfo>{

	private static TickInfoRepository repository;
	
	@Autowired
    public TickInfoDAO(TickInfoRepository repository) {
        this.repository = repository;
    }
	
	@Override
	public TickInfo findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<TickInfo> findAll() {
		return repository.findAll();
	}

	@Override
	public TickInfo save(TickInfo entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(TickInfo entity) {
		repository.delete(entity);		
	}

}
