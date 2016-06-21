package com.ase.iec.persistence.DAO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ase.iec.persistence.BatteryRepository;
import com.ase.iec.persistence.entity.Battery;
import com.ase.iec.persistence.entity.User;

@Service
public class BatteryDAO implements GenericDAO<Battery>{

	private static BatteryRepository repository;
	
	@Autowired
    public BatteryDAO(BatteryRepository repository) {
        this.repository = repository;
    }
	 
	@Override
	public Battery findById(Long id) {
		return repository.findOne(id);
	}

	@Override
	public List<Battery> findAll() {
		return repository.findAll();
	}

	@Override
	public Battery save(Battery entity) {
		return repository.save(entity);
	}

	@Override
	public void delete(Battery entity) {
		repository.delete(entity);
		
	}
	

	public Battery findByIPPlusAddress(String address){
		return repository.findByIpPlusServiceAddress(address);
	}


	
}
