package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import db.DB;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entites.Department;

public class Program2 {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

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

		System.out.println("======= Update =======");
		department.setName("Teste");
		departmentDao.update(department);
		System.out.println("Atualização feita com sucesso!");

		System.out.println("======= Delete =======");
		System.out.print("Insira o id para ser deletado: ");
		int id = sc.nextInt();
		departmentDao.deleteById(id);
		System.out.println("Deletado com sucesso!");
		
		DB.closeConnection();
		sc.close();
	}
}
