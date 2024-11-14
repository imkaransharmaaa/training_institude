package com.traininginstitute.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;
import com.traininginstitute.dao.InstituteDao;
import com.traininginstitute.pojo.Institute;

@Component
public class InstituteDaoImpl implements InstituteDao {

	@Autowired
	private JdbcTemplate jTemplate;

	@Override
	public boolean updateInstitute(Institute institute) {
		String query = "UPDATE institute SET "
+ "instituteName = ?, institutePassword = ? , "
+ "startDate = ? , address = ? WHERE instituteID= ?";
		
		try {
			int count = jTemplate.update(query,
new Object[] {institute.getInstituteName(),
institute.getInstitutePassword(),institute.getStartDate(),
institute.getAddress()});
			
			if(count > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteInstitute(String instituteID) {
		String query = "DELETE FROM institute "
				+ "WHERE instituteID= ?";
						
						try {
							int count = 
				jTemplate.update(query,instituteID);
							
							if(count > 0)
								return true;
							else
								return false;
						} catch (Exception e) {
							e.printStackTrace();
							return false;
						}
	}

	@Override
	public boolean addNewInstitute(Institute institute) {
		String query = 
		"INSERT INTO institute(instituteID,instituteName,"
		+ "institutePassword,startDate,address) "
		+ "VALUES(?,?,?,?,?) ";
				
		try {
			int count = jTemplate.update(query,
			new Object[] {institute.getInstituteID()
					,institute.getInstituteName(),
			institute.getInstitutePassword(),
			institute.getStartDate(),
			institute.getAddress()});
			
			if(count > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean checkInstitute(Institute institute) {
		String query = "SELECT * FROM institute WHERE"
				+ " instituteID = ? AND "
				+ "institutePassword = ?";
		
		try {
			Institute ref = 
		jTemplate.queryForObject(query,new 
		BeanPropertyRowMapper<Institute>(Institute.class),
		new Object[] {institute.getInstituteID(),
		institute.getInstitutePassword()});
			
			if(ref != null)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public List<Institute> getAllInstitutes() {
		List<Institute> lst = new ArrayList<>();
		String query = "SELECT * FROM institute";
		
		try {
			lst = 
		jTemplate.query(query,new 
		BeanPropertyRowMapper<Institute>(Institute.class));
			
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			lst.clear();
			return lst;
		}
	}

	@Override
	public Institute getInstituteDetails(
			String instituteID) {
		String query = "SELECT * FROM institute WHERE"
				+ " instituteID = ?";
		
		try {
			Institute ref = 
		jTemplate.queryForObject(query,new 
		BeanPropertyRowMapper<Institute>(Institute.class),
		instituteID);
			
			if(ref != null)
				return ref;
			else
				return null;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public Institute getInstituteByCourse(
			int courseID) {
		String query1 = "SELECT instituteID FROM course_institute"
				+ " WHERE courseID = ?";
		
		try {
			String  instituteID = 
					jTemplate.execute(query1, 
		new PreparedStatementCallback<String>() {
				@Override
				public String doInPreparedStatement(
				PreparedStatement ps) 
			throws SQLException, DataAccessException {
					ps.setInt(1, courseID);
					ResultSet rs = ps.executeQuery();
					rs.next();
					return rs.getString("instituteID");
				}		
		});
			
					Institute obj = 
					getInstituteDetails(instituteID);
					return obj;
					
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
