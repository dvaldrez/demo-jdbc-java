package aplication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Deparment;
import model.entities.Seller;

public class Programa {
	public static void main(String[] args) {

		Deparment dep = new Deparment(1, "books");
		System.out.println(dep.toString());

		Seller seller = new Seller(1, "Dennis", "d@ho", new Date(), 2500.00);
		System.out.println(seller.toString());
        seller = null;
		SellerDao sellerDao = DaoFactory.createSellerDao();

		seller = sellerDao.findById(4);

		System.out.println(seller);
		seller = null;
		
		Deparment dep2 = new Deparment(2, null);
		List<Seller> list = sellerDao.findByDep(dep2);
		
		for (Seller obj : list) {
			System.out.println(obj);
		}
		System.out.println(seller);

	}

}
