package application;

import java.util.List;

import db.DB;
import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class Program {
	public static void main(String[] args) {
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		System.out.println("=========== Find by id ===========");
		Seller seller = sellerDao.findById(3);
		System.out.println(seller);
		
		System.out.println("=========== Seller by Department ===========");
		Department temp = new Department(2, null);
		List<Seller> list = sellerDao.findByDepartment(temp);
		list.forEach(System.out::println);
		
		DB.closeConnection();
	}
}
