package application;

import java.util.ArrayList;
import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entites.Department;

public class Program2 {
	public static void main(String[] args) {
		DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
		Department department = departmentDao.findById(3);
		System.out.println("======= Find by id =======");
		System.out.println(department);
		
		System.out.println("======= Find All =======");
		List<Department> list = new ArrayList<Department>();
		list = departmentDao.findAll();
		list.forEach(System.out::println);
		
		System.out.println("======= Insert =======");
		Department temp = new Department(null, "Music");
		departmentDao.insert(temp);
		System.out.printf("Inserido com secesso o departamento: %s com o id %d%n", temp.getName(), temp.getId());
		
		
		DB.closeConnection();
	}
}
