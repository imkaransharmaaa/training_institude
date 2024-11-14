package com.traininginstitute.daoimpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.traininginstitute.dao.FeedbackDao;
import com.traininginstitute.pojo.Feedback;

@Component
public class FeedbackDaoImpl implements FeedbackDao {

	@Autowired
	private JdbcTemplate jTemplate;

	@Override
	public boolean addNewFeedback(Feedback feedback) {
		String query = "INSERT INTO feedback(userID,"
+ "instituteID,description,feedbackDate) VALUES(?,?,?,?)";
		
		try {
			int count = jTemplate.update(query,
			new Object[] {feedback.getUserID(),
			feedback.getInstituteID(),
			feedback.getDescription(),
			feedback.getFeedbackDate()});
			
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
	public List<Feedback> getAllFeedbacks() {
		List<Feedback> lst = new ArrayList<>();
		String query = "SELECT * FROM feedback";
		
		try {
			lst = jTemplate.query(query, new 
			BeanPropertyRowMapper<Feedback>(
					Feedback.class));
			
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			lst.clear();
			return lst;
		}
	}

	@Override
	public List<Feedback> getAllFeedbacksForInstitute(
			String instituteID) {
		List<Feedback> lst = new ArrayList<>();
		String query = "SELECT * FROM feedback"
				+ " WHERE instituteID = ?";
		
		try {
			lst = jTemplate.query(query, new 
			BeanPropertyRowMapper<Feedback>(
					Feedback.class),instituteID);
			
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			lst.clear();
			return lst;
		}
	}

	@Override
	public List<Feedback> getAllFeedbacksGivenByStudent(
			String userID) {
		List<Feedback> lst = new ArrayList<>();
		String query = "SELECT * FROM feedback"
				+ " WHERE userID = ?";
		
		try {
			lst = jTemplate.query(query, new 
			BeanPropertyRowMapper<Feedback>(
					Feedback.class),userID);
			
			return lst;
		} catch (Exception e) {
			e.printStackTrace();
			lst.clear();
			return lst;
		}
	}
}








