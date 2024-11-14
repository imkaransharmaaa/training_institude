package com.traininginstitute.daoimpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Component;

import com.traininginstitute.dao.StudentDao;
import com.traininginstitute.pojo.Student;

@Component
public class StudentDaoImpl implements StudentDao {

	@Autowired
	private JdbcTemplate jTemplate;

	@Override
	public boolean checkStudent(Student student) {
		String query = "SELECT * FROM student"
+ " WHERE userID = ? AND studentPassword = ?";
		
		try {
			Student ref = jTemplate.queryForObject(query,
new BeanPropertyRowMapper<Student>(Student.class),
new Object[] {student.getUserID(),
		student.getStudentPassword()});
		
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

	@Override
	public boolean updateStudent(Student student) {
		String query = "UPDATE student SET emailID = ?,"
+ "studentName = ? , qualification = ? , "
+ "studentPassword = ? , number = ? , address = ? WHERE"
+ " userID = ?";
		
		try {
			int count = jTemplate.update(query,
new Object[] {student.getEmailID(),student.getStudentName(),
student.getQualification(),student.getStudentPassword(),
student.getNumber(),student.getAddress(),
student.getUserID()});
			
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
	public boolean addNewStudent(Student student) {
		String query = "INSERT INTO student VALUES"
				+ "(?,?,?,?,?,?,?)";
						
						try {
							int count = jTemplate.update(
									query,
new Object[] {student.getUserID() , 
student.getEmailID(),student.getStudentName(),
student.getQualification(),student.getStudentPassword(),
student.getNumber(),student.getAddress()});
							
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
	public List<Student> getAllStudents() {
		String query = "SELECT * FROM student";
		List<Student> lst = new ArrayList<>();				
			try {
				lst = jTemplate.query(query,
				new BeanPropertyRowMapper<Student>
				(Student.class));
							
				return lst;
			} catch (Exception e) {
				e.printStackTrace();
				lst.clear();
				return lst;
			}
	}

	@Override
	public Student getStudentDetails(String studentID) {
		String query = "SELECT * FROM student"
				+ " WHERE userID = ?";
						
			try {
				Student ref = jTemplate.queryForObject(query,
				new BeanPropertyRowMapper<Student>(
						Student.class),studentID);
						
						return ref;
				} 
				catch(EmptyResultDataAccessException ex)
				{
					return null;
				}
				catch (Exception e) {
					e.printStackTrace();
					return null;
				}
	}

	@Override
	public List<Student> getAllStudentsForCourse(
			int courseID) {
		List<String> userIdList = new ArrayList<>();
		List<Student> lst = new ArrayList<>();
		
		String query = 
		"SELECT userID FROM student_course WHERE "
		+ "courseID = ?";
		
		try {
		userIdList = 
				jTemplate.execute(query, 
		new PreparedStatementCallback<List<String>>() {
			@Override
			public List<String> doInPreparedStatement(
		PreparedStatement ps) 
				throws SQLException, DataAccessException {
				List<String> lst = new ArrayList<>();
				ps.setInt(1, courseID);
				ResultSet rs = ps.executeQuery();
				while(rs.next()) {
					lst.add(rs.getString(1));
				}
				
				return lst;
			}
		});
		
		}
		catch(Exception exc) {
			exc.printStackTrace();
		}
		
		if(userIdList.size() > 0) {
		for(String id : userIdList) {
			Student s = getStudentDetails(id);
			lst.add(s);
		}
		}
		
		return lst;
	}
}
