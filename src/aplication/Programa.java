package aplication;

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

		Seller seller = new Seller(1, "Dennis", "d@ho", new Date(), 2500.00, dep);
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

		List<Seller> list2 = sellerDao.findAll();
		
		for (Seller obj : list2) {
			System.out.println(obj);
		}
		System.out.println(seller);

		Seller sellerInsert = new Seller(null, "Giovana", "gi@hotmail", new Date(), 20000.00, dep2); 
		sellerInsert.getDeparment().setId(2);
		
		sellerDao.insert(sellerInsert);
		System.out.println("Funcional Gerada" + sellerInsert.getId());
		
	}

}
