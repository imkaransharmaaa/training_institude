package com.traininginstitute.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.traininginstitute.dao.CourseDao;
import com.traininginstitute.pojo.Course;

@Controller
public class CourseController {

	@Autowired
	private CourseDao daoImpl;
	
	@RequestMapping("/addCourseForm")
	public String showAddCourseForm() {
		return "add-course";
	}
	
	@RequestMapping(value="/registerCourse",
			method=RequestMethod.POST)
	public String addCourse(Course course,
			HttpServletRequest request) {
		
		HttpSession session = 
				request.getSession(false);
		String instituteID = (String)
				session.getAttribute("instituteID");
		
		int courseID = daoImpl.addNewCourse(course);
		
		if(courseID == -1) {
			return "addCError";
		}
		else {
			if(daoImpl.addCourseForInstitute(courseID,
					instituteID)) {
				return "addCSuccess";
			}
			else {
				return "addCError";
			}
		}
	}
}
