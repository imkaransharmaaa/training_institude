package com.traininginstitute.dao;

import java.util.List;

import com.traininginstitute.pojo.Feedback;

public interface FeedbackDao {

	//add Feedback
	boolean addNewFeedback(Feedback feedback);
		
	//show all feedbacks
	List<Feedback> getAllFeedbacks();
		
	//show all feedbacks for institute
	List<Feedback> 
	getAllFeedbacksForInstitute(
			String instituteID);
	
	//show all feedbacks given by student
	List<Feedback> 
	getAllFeedbacksGivenByStudent(
			String userID);
}
