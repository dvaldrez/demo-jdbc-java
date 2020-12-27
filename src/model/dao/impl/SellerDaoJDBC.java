package model.dao.impl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entities.Deparment;
import model.entities.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	@Override
	public void insert(Seller seller) {

		PreparedStatement st = null;
		try {
			 st = conn.prepareStatement("INSERT INTO seller "
					+ "(Name, Email, BirthDate, BaseSalary, DepartmentId) " + "VALUES " + "(?, ?, ?, ?, ?)",
					Statement.RETURN_GENERATED_KEYS);

			st.setString(1, seller.getNome());
			st.setString(2, seller.getEmail());
			st.setDate(3, new java.sql.Date(seller.getBirthDate().getTime()));
			st.setDouble(4, seller.getBaseSalary());
			st.setInt(5, seller.getDeparment().getId());
			
			int rowAffected = st.executeUpdate();
			
			if (rowAffected > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					int id = rs.getInt(1);
					seller.setId(id);
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Falha ao inserir a linha");
			}
			
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
		}

	}

	@Override
	public void update(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(Seller seller) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName" + " FROM seller INNER JOIN department"
							+ " ON seller.DepartmentId = department.Id" + " WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				Deparment dep = instantiateDep(rs);
				Seller seller = instantiateSeller(rs, dep);
				return seller;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	private Seller instantiateSeller(ResultSet rs, Deparment dep) throws SQLException {
		Seller seller = new Seller();
		seller.setId(rs.getInt("Id"));
		seller.setNome(rs.getString("Name"));
		seller.setEmail(rs.getString("Email"));
		seller.setBirthDate(rs.getDate("BirthDate"));
		seller.setBaseSalary(rs.getDouble("BaseSalary"));
		seller.setDeparment(dep);
		return seller;
	}

	private Deparment instantiateDep(ResultSet rs) throws SQLException {
		Deparment dep = new Deparment();
		dep.setId(rs.getInt("Id"));
		dep.setName(rs.getString("Name"));
		return dep;
	}

	@Override
	public List<Seller> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " + "FROM seller INNER JOIN department "
							+ "ON seller.DepartmentId = department.id " + "ORDER BY Name ");
			rs = st.executeQuery();

			List<Seller> seller = new ArrayList<Seller>();
			Map<Integer, Deparment> map = new HashMap();

			while (rs.next()) {

				Deparment dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					map.put(rs.getInt("DepartmentId"), dep);
				}
				seller.add(instantiateSeller(rs, dep));

			}
			return seller;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public List<Seller> findByDep(Deparment deparment) {
		PreparedStatement st = null;
		ResultSet rs = null;

		try {
			st = conn.prepareStatement("SELECT seller.*,department.Name as DepName "
					+ "FROM seller INNER JOIN department "
					+ "ON seller.DepartmentId = department.id where seller.DepartmentId = ? " + "ORDER BY Name ");
			System.out.println(deparment.getId());
			st.setInt(1, deparment.getId());
			rs = st.executeQuery();

			List<Seller> seller = new ArrayList<Seller>();
			Map<Integer, Deparment> map = new HashMap();

			while (rs.next()) {

				Deparment dep = map.get(rs.getInt("DepartmentId"));

				if (dep == null) {
					map.put(rs.getInt("DepartmentId"), dep);
				}
				seller.add(instantiateSeller(rs, dep));

			}
			return seller;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
