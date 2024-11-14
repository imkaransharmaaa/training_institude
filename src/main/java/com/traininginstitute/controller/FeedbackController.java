package com.traininginstitute.controller;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.traininginstitute.dao.CourseDao;
import com.traininginstitute.dao.FeedbackDao;
import com.traininginstitute.dao.InstituteDao;
import com.traininginstitute.pojo.Course;
import com.traininginstitute.pojo.Feedback;
import com.traininginstitute.pojo.Institute;

@Controller
public class FeedbackController {

	@Autowired
	private FeedbackDao daoImpl;
	
	@Autowired
	private CourseDao cdaoImpl;
	
	@Autowired
	private InstituteDao idaoImpl;
	
	@RequestMapping("/submitFeedbackPage")
	public String showFeedbackPage(HttpServletRequest request,
			Model m) {
		HttpSession session = request.getSession(false);
		String studentID = (String) 
				session.getAttribute("studentID");
		
		List<Course> lstCourses =  
		cdaoImpl.getAllCoursesByStudent(studentID);
		
		List<Institute> instList = new ArrayList<>();
		
		for(Course c : lstCourses)
		{
			Institute inst = 
			idaoImpl.getInstituteByCourse(c.getCourseID());
			instList.add(inst);
		}
		m.addAttribute("INSTITUTELIST", instList);
		return "feedback-form";
	}
	
	@RequestMapping("/viewInstfeedbacks")
	public String viewInstfeedbacks(HttpServletRequest
			request , Model m) {
		HttpSession session = 
				request.getSession(false);
		String instituteID = (String)
				session.getAttribute("instituteID");
		List<Feedback> lst = 
		daoImpl.getAllFeedbacksForInstitute(instituteID);
		m.addAttribute("FEEDBACKLIST", lst);
		return "feedback-table";
	}
	
	@RequestMapping("/showfeedbacks")
	public String showFeedbacks(Model m) {
		List<Feedback> lst = daoImpl.getAllFeedbacks();
		m.addAttribute("FEEDBACKLIST", lst);
		return "feedback-table";
	}
	
	@RequestMapping(value="/submitFeedback" ,
			method = RequestMethod.POST)
	public String submitFeedback(Feedback feedback) {
		
		if (daoImpl.addNewFeedback(feedback))
			return "feedbackSuccess";
		else
			return "feedbackError";
	}
}




