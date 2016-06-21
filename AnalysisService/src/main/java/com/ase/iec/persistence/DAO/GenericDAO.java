package com.ase.iec.persistence.DAO;

import java.util.List;

public interface GenericDAO<T> {
	T findById(Long id);
	List<T> findAll();
	T save(T entity);
	void delete(T entity);
}
