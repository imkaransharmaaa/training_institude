package com.traininginstitute.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.traininginstitute.dao.CourseDao;
import com.traininginstitute.pojo.Course;

@Controller
public class JsonController {

	@Autowired
	private CourseDao cdaoImpl;
	
	@RequestMapping(value = "/getallcoursesbyinstitute" ,
			produces="application/json")
	public @ResponseBody List<Course> getAllCoursesByInstitute(
	@RequestParam("id") String instituteID) {
		
		List<Course> courseList = 
		cdaoImpl.getAllCoursesByInstitute(instituteID);
		
		return courseList;
	} 
	
}







