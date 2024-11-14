package com.traininginstitute.daoimpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import com.traininginstitute.dao.CourseDao;
import com.traininginstitute.pojo.Course;

@Component
public class CourseDaoImpl implements CourseDao {

	@Autowired
	private JdbcTemplate jTemplate;

	@Override
	public int addNewCourse(Course course) {
		String query = "INSERT INTO course(courseName,"
		+ "price,seats,filledseats) VALUES(?,?,?,?)";
		
		/*
		String query = "INSERT INTO course(courseName,"
		+ "price,seats,filledseats) VALUES(?,?,?,?)";
		
		try {
			int count = jTemplate.update(query, new 
			Object[] {course.getCourseName(),
			course.getPrice(),course.getSeats(),
			course.getFilledseats()});
			
			if(count > 0)
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}*/
		
		KeyHolder holder = new GeneratedKeyHolder();
		
		try {
			int count = jTemplate.update(
			new PreparedStatementCreator() {
				@Override
				public PreparedStatement 
				createPreparedStatement(Connection con) 
						throws SQLException {
					PreparedStatement ps = 
					con.prepareStatement(
					query, Statement.RETURN_GENERATED_KEYS);
					ps.setString(1, course.getCourseName());
					ps.setInt(2, course.getPrice());
					ps.setInt(3, course.getSeats());
					ps.setInt(4, 0);
					return ps;
				}
			},holder);
			
			if(count > 0) {
				int courseID = holder.getKey().intValue();
				return courseID;
			}
			else
				return -1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	
	@Override
	public boolean addCourseForInstitute(int courseID, 
			String instituteID) {
		String query = "INSERT INTO course_institute"
				+ " VALUES(?,?)";
		
		try {
			int count = jTemplate.update(query,
					new Object[] {courseID,instituteID});
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
	public boolean addCourseForStudent(int courseID, 
			String studentID) {
		String query = "INSERT INTO student_course"	
				+ " VALUES(?,?)";
		
		try {
			int count = jTemplate.update(query,
					new Object[] {studentID,courseID});
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
	public boolean updateCourse(Course course) {
		String query = "UPDATE course SET courseName = ?,"
		+ "price = ?,seats = ?,filledseats = ? "
		+ "WHERE courseID = ?";
				
				try {
					int count = jTemplate.update(query, new 
					Object[] {course.getCourseName(),
					course.getPrice(),course.getSeats(),
					course.getFilledseats() ,
					course.getCourseID()});
					
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
	public List<Course> getAllCourses() {
		
		List<Course> lst = new ArrayList<>();
		String query = "SELECT * FROM course";
		
		try {
			lst = jTemplate.query(query, 
			new BeanPropertyRowMapper<Course>(
					Course.class));
			
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			lst.clear();
			return lst;
		}
	}

	@Override
	public List<Course> getAllCoursesByInstitute(
			String instituteId) {
		List<Course> lst = new ArrayList<>();
		List<Integer> courses = new ArrayList<>();
		String query1 = "SELECT courseID FROM "
		+ "course_institute WHERE instituteID = ?";
//		String query2 = "SELECT * FROM "
//		+ "course WHERE courseID = ?";
		
		try {
//			courses = jTemplate.query(query1, 
//			new BeanPropertyRowMapper<Integer>(
//					Integer.class),instituteId);
			
			courses = 
jTemplate.execute(query1, 
		new PreparedStatementCallback<List<Integer>>() {
	@Override
	public List<Integer> doInPreparedStatement(
			PreparedStatement ps) 
					throws SQLException, 
					DataAccessException {
		List<Integer> lst = new ArrayList<>();
			ps.setString(1, instituteId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				lst.add(rs.getInt(1));
			}
			return lst;
	}
});
			
			if(courses.size() > 0) {
				for(Integer id : courses) {
					
					Course obj = getCourseInfo(id);
					lst.add(obj);
				}
				
				return lst;
			}
			else
				return lst;
		}
		catch(Exception exc) {
			exc.printStackTrace();
			lst.clear();
			return lst;
		}
	}

	public List<Course> getAllCoursesByStudent(
			String studentID)
	{
		List<Course> lst = new ArrayList<>();
		List<Integer> courses = new ArrayList<>();
		String query1 = "SELECT courseID FROM "
		+ "student_course WHERE userID = ?";
//		String query2 = "SELECT * FROM "
//		+ "course WHERE courseID = ?";
	
		try {
			courses = 
			jTemplate.execute(query1, 
			new PreparedStatementCallback<List<Integer>>() {
			@Override
			public List<Integer> doInPreparedStatement(
			PreparedStatement ps) throws SQLException,DataAccessException 
			{
				List<Integer> lst = new ArrayList<>();
				ps.setString(1, studentID);
				ResultSet rs = ps.executeQuery();
					while(rs.next()) {
						lst.add(rs.getInt("courseID"));
					}
				return lst;
			}
			});
								
			if(courses.size() > 0) {
			for(Integer id : courses) {
					Course obj = getCourseInfo(id);
					lst.add(obj);
			}				
				return lst;
			}
				else
					return lst;
			}
			catch(Exception exc) {
			exc.printStackTrace();
			lst.clear();
			return lst;
		}
	}
	
	@Override
	public boolean isSeatAvailable(int courseID) {
		String query = 
		"SELECT * FROM course WHERE seats = filledseats"
		+ "AND courseID = ?";
		
		try {
			Course course = jTemplate.queryForObject(query,
			new BeanPropertyRowMapper<Course>(Course.class),
			courseID);
			
			if(course != null)
				return false;
			else
				return true;
				
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean incrementFilledSeatByOne(int courseID) {
		String query = "UPDATE course SET filledseats = "
				+ "filledseats + 1 WHERE courseID = ?";
						
			try {
			int count = jTemplate.update(query,
					courseID);
							
			if(count > 0)
				return true;
			else
				return false;
			} 
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}

	@Override
	public boolean updateStudentWithCourse(String userID, 
			int courseID) {
		String query = "INSERT INTO student_course "
				+ "VALUES(?,?)";
						
			try {
			int count = jTemplate.update(query,
			new Object[] {userID,courseID});
							
			if(count > 0)
				return true;
			else
				return false;
			} 
			catch (Exception e) {
				e.printStackTrace();
				return false;
			}
	}


	@Override
	public Course getCourseInfo(int courseID) {
		try {
			Course cr = jTemplate.queryForObject(
			"SELECT * FROM course WHERE courseID = ?",
			new BeanPropertyRowMapper<Course>(Course.class)
			, courseID);
			return cr;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
