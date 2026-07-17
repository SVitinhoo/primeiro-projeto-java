package model.dao;

import java.util.List;

import model.entites.Department;

public interface DepartmentDao {
	void insert(DepartmentDao obj);

	void update(DepartmentDao obj);

	void deleteById(Integer id);

	Department findById(Integer id);

	List<Department> findAll();
}
