package com.ase.iec.persistence;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ase.iec.persistence.entity.Task;
import com.ase.iec.persistence.entity.TickInfo;

@Repository
public interface TickInfoRepository extends JpaRepository<TickInfo, Long>{

	@Query( "select t from TickInfo t where timestamp >= ?1 and timestamp < ?2" )
	List<TickInfo> findByDate(Date from, Date to);

	
}