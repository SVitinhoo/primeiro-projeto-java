package model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.DB;
import db.DbException;
import model.dao.DepartmentDao;
import model.entites.Department;

public class DepartmentDaoJDBC implements DepartmentDao {
	
	private Connection conn;
	
	public DepartmentDaoJDBC(Connection conn) {
		this.conn = conn;
	}

	private Department instantiateDepartment(ResultSet rs) throws SQLException {
		Department temp = new Department();
		temp.setId(rs.getInt("Id"));
		temp.setName(rs.getString("Name"));
		return temp;
	}
	
	@Override
	public void insert(Department dep) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("INSERT INTO department (Name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
			st.setString(1, dep.getName());
			int row = st.executeUpdate();
			if (row > 0) {
				ResultSet rs = st.getGeneratedKeys();
				if (rs.next()) {
					dep.setId(rs.getInt(1));
				}
				DB.closeResultSet(rs);
			} else {
				throw new DbException("Um erro ocorreu! nenhuma linha foi afetada");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
	}

	@Override
	public void update(Department dep) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("UPDATE department SET Name = ? WHERE Id = ?");
			st.setString(1, dep.getName());
			st.setInt(2, dep.getId());
			int row = st.executeUpdate();
			if (row == 0) {
				throw new DbException("A atualização falhou, nenhuma linha foi alterada");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public void deleteById(Integer id) {
		PreparedStatement st = null;
		try {
			st = conn.prepareStatement("DELETE FROM department WHERE id = ?");
			st.setInt(1, id);
			int row = st.executeUpdate();
			if (row == 0) {
				throw new DbException("O id não existe!");
			}
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
		}
		
	}

	@Override
	public Department findById(Integer id) {
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department d " +
					"WHERE d.Id = ?");
			st.setInt(1, id);
			rs = st.executeQuery();
			if (rs.next()) {
				return instantiateDepartment(rs);
			}
			return null;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

	@Override
	public List<Department> findAll() {
		PreparedStatement st = null;
		ResultSet rs = null;
		List<Department> list = new ArrayList<Department>();
		try {
			st = conn.prepareStatement(
					"SELECT * FROM department d " +
					"ORDER BY d.Name ");
			rs = st.executeQuery();
			while (rs.next()) {
				Department temp = instantiateDepartment(rs);
				list.add(temp);
			}
			return list;
		} catch (SQLException e) {
			throw new DbException(e.getMessage());
		}
		finally {
			DB.closeStatement(st);
			DB.closeResultSet(rs);
		}
	}

}
