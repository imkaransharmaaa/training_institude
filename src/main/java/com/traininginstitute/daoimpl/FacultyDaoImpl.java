package com.traininginstitute.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.traininginstitute.dao.FacultyDao;
import com.traininginstitute.pojo.Faculty;

@Component
public class FacultyDaoImpl implements FacultyDao {

	@Autowired
	private JdbcTemplate jTemplate;

	@Override
	public boolean addNewFaculty(Faculty faculty) {
		String query = "INSERT INTO faculty(facultyName,"
				+ "instituteID,courseID) VALUES(?,?,?)";
		
		try {
			int count = jTemplate.update(query,
			new Object[] {faculty.getFacultyName(),
					faculty.getInstituteID(),
			faculty.getCourseID()});
			
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
	public boolean deleteFaculty(int facultyID) {
		String query = "DELETE FROM faculty WHERE"
				+ " facultyID = ?";
		
		try {
			int count = jTemplate.update(query,facultyID);
			
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
	public List<Faculty> getAllFaculties() {
		List<Faculty> lst = new ArrayList<>();
		String query = "SELECT * FROM faculty";
		
		try {
			lst = jTemplate.query(query, 
			new BeanPropertyRowMapper<Faculty>(
			Faculty.class));
			
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			lst.clear();
			return lst;
		}
	}

	@Override
	public List<Faculty> getAllFacultiesByInstitute(
			String instituteID) {
		List<Faculty> lst = new ArrayList<>();
		String query = "SELECT * FROM faculty WHERE"
				+ " instituteID = ?";
		
		try {
			lst = jTemplate.query(query, 
			new BeanPropertyRowMapper<Faculty>(
			Faculty.class), instituteID);
			
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			lst.clear();
			return lst;
		}
	}
}
