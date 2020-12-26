package aplication;

import java.util.Date;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Deparment;
import model.entities.Seller;

public class Programa {
	public static void main (String [] args) {
		
		Deparment dep = new Deparment(1,"books");
		System.out.println(dep.toString());
		
		Seller seller = new Seller(1,"Dennis", "d@ho", new Date(), 2500.00 );
		System.out.println(seller.toString());
		
		SellerDao sellerDao = DaoFactory.createSellerDao();
		
		
	}

}
