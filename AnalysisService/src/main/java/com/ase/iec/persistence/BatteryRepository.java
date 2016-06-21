package com.ase.iec.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ase.iec.persistence.entity.Battery;

@Repository
public interface BatteryRepository extends JpaRepository<Battery, Long>{

	 Battery findByIpPlusServiceAddress(String ipplusserviceaddress);
	
}