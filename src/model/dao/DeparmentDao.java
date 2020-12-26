package model.dao;

import java.util.List;

import model.entities.Deparment;

public interface DeparmentDao {

	void insert(Deparment deparment);
	void update(Deparment deparment);
	void delete(Deparment deparment);
	Deparment findById(Integer id);
	List<Deparment> findAll();

	
}
