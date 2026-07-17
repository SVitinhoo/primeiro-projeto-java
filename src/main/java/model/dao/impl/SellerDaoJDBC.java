package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import db.DB;
import db.DbException;
import model.dao.SellerDao;
import model.entites.Department;
import model.entites.Seller;

public class SellerDaoJDBC implements SellerDao {

	private Connection conn;

	public SellerDaoJDBC(Connection conn) {
		this.conn = conn;
	}
	
	private Seller instantiateSeller(ResultSet rs, Department tempDp) throws SQLException {
		Seller tempSeller = new Seller();
		tempSeller.setId(rs.getInt("Id"));
		tempSeller.setName(rs.getString("Name"));
		tempSeller.setEmail(rs.getString("Email"));
		tempSeller.setBirthDate(rs.getDate("BirthDate"));
		tempSeller.setBaseSalary(rs.getDouble("BaseSalary"));
		tempSeller.setDepartment(tempDp);
		return tempSeller;
	}
	
	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department temp = new Department();
		temp.setId(rs.getInt("DepartmentId"));
		temp.setName(rs.getString("DepName"));
		return temp;
	}

	@Override
	public void insert(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(Seller obj) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteById(Integer id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Seller findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
				"SELECT seller.*,department.Name as DepName " +
				"FROM seller INNER JOIN department " +
				"ON seller.DepartmentId = department.Id " +
				"WHERE seller.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();

			if (rs.next()) {
				Department tempDp = instantiateDepartment(rs);
				Seller tempSeller = instantiateSeller(rs, tempDp);
				return tempSeller;
			}

			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		} finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Seller> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Seller> findByDepartment(Department department) {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Seller> lista = new ArrayList<Seller>();
		Map<Integer, Department> map = new HashMap<Integer, Department>();
		try {
			st = conn.prepareStatement(
					"SELECT seller.*,department.Name as DepName " +
					"FROM seller INNER JOIN department " +
					"ON seller.DepartmentId = department.Id " +
					"WHERE DepartmentId = ? " +
					"ORDER BY Name");
			st.setInt(1, department.getId());
			rs = st.executeQuery();
			while (rs.next()) {
				Department dep = map.get(rs.getInt("DepartmentId"));
				if (dep == null) {
					dep = instantiateDepartment(rs);
					map.put(rs.getInt("DepartmentId"), dep);
				}
				Seller tempSeller = instantiateSeller(rs, dep);
				lista.add(tempSeller);
			}
			return lista;
		}
		catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}
}
