package com.traininginstitute.daoimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.traininginstitute.dao.AdminDao;
import com.traininginstitute.pojo.Admin;

@Repository
public class AdminDaoImpl implements AdminDao {

	@Autowired
	private JdbcTemplate jTemplate;

	@Override
	public boolean checkAdmin(Admin admin) {
		try {
			String query = "SELECT * FROM admin WHERE"
					+ " adminID = ? AND adminPassword = ?";
			
			Admin ref = jTemplate.queryForObject(query, 
			new BeanPropertyRowMapper<Admin>(Admin.class),
			new Object[] {admin.getAdminID(),
					admin.getAdminPassword()});
			
			return true;
		} 
		catch(EmptyResultDataAccessException exc) {
			return false;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}