package application;

import java.util.Date;
import java.util.List;
import java.util.Scanner;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class Program {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=========== Find by id ===========");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("=========== Seller by Department ===========");
		Department temp = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(temp);
		list.forEach(System.out::println);
		
		
		System.out.println("=========== Seller FindAll ===========");
		list = sellerDao.findAll();
		list.forEach(System.out::println);
		
		System.out.println("=========== Seller Insert  ===========");
		seller = new Seller(null, "Bruno Black", "bruno@gmail.com", new Date(), 4000.00, temp);
		sellerDao.insert(seller);
		System.out.println("Inserido, novo id: " + seller.getId());
		
		System.out.println("=========== Seller Update  ===========");
		seller = sellerDao.findById(1);
		seller.setName("Martha Waine");
		sellerDao.update(seller);
		System.out.println("Update completo!");
		
		System.out.println("=========== Seller Delete  ===========");
		System.out.print("Id para o teste de deleção: ");
		sellerDao.deleteById(sc.nextInt());
		System.out.println("Deleção completa!");
		
		sc.close();
		DB.closeConnection();
	}
}
