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
import org.springframework.web.servlet.ModelAndView;

import com.traininginstitute.dao.CourseDao;
import com.traininginstitute.dao.InstituteDao;
import com.traininginstitute.dao.StudentDao;
import com.traininginstitute.pojo.Course;
import com.traininginstitute.pojo.Student;

@Controller
public class StudentController {

	@Autowired
	private StudentDao daoImpl;
	
	@Autowired
	private InstituteDao idaoImpl;
	
	@Autowired
	private CourseDao cdaoImpl;
	
	@RequestMapping(value="/acceptStudent" , 
			method = RequestMethod.POST)
	public ModelAndView checkStudent(Student student,
			HttpServletRequest request) {
		
		HttpSession session = null;
		
		if(daoImpl.checkStudent(student)) {
			session = request.getSession(true);
			session.setAttribute("studentID", 
					student.getUserID());
			ModelAndView mv = new ModelAndView(
					"student-home","STUDOBJ",student);
			return mv;
		}
		else {
			ModelAndView mv = new ModelAndView(
					"student-errorLogin","",null);
			return mv;
		}
	}
	
	@RequestMapping(value="/registerStudent" , 
			method = RequestMethod.POST)
	public String regStudent(Student student) {
		
		if(daoImpl.addNewStudent(student)) 	
			return "studentSuccessRegister";
		else
			return "errorRegister";
	}
	
	@RequestMapping("/viewstudents")
	public String viewStudents(Model m) {
		List<Student> slist = daoImpl.getAllStudents();
		m.addAttribute("STUDENTLIST", slist);
		return "student-table";
	}
	
	@RequestMapping("/viewInststudents")
	public String viewInstStudents(Model m , 
	HttpServletRequest request) {
		
		List<Student> slist = new ArrayList<>();
		HttpSession session = request.getSession(false);
		String instituteId = (String)
				session.getAttribute("instituteID");
		List<Course> courses = 
		cdaoImpl.getAllCoursesByInstitute(instituteId);
		
		for(Course c : courses) {
			List<Student> list = daoImpl.getAllStudentsForCourse(
					c.getCourseID());
			slist.addAll(list);
		}
		
		m.addAttribute("STUDENTLIST", slist);
		return "student-table";
	}
}




