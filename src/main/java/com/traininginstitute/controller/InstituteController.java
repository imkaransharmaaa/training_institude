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
import com.traininginstitute.pojo.Institute;
import com.traininginstitute.pojo.Student;

@Controller
public class InstituteController {

	@Autowired
	private InstituteDao daoImpl;
	@Autowired
	private CourseDao cdaoImpl;
	@Autowired
	private StudentDao sdaoImpl;
	
	@RequestMapping(value="/acceptInstitute",
			method=RequestMethod.POST)
	public ModelAndView checkInstitute(Institute institute,
			HttpServletRequest request)
	{
		HttpSession session = null;
		
		if(daoImpl.checkInstitute(institute)) {
			session = request.getSession(true);
			session.setAttribute("instituteID",
					institute.getInstituteID());
			session.setAttribute("instituteName", 
					institute.getInstituteName());
			
			ModelAndView mv = new ModelAndView(
					"institute-home","INSTOBJ", 
					institute);
			return mv;
		}
		else {
			ModelAndView mv = new ModelAndView(
					"institute-errorLogin","",null);
			return mv;
		}
	}
	
	
	@RequestMapping(value="/registerInstitute",
			method=RequestMethod.POST)
	public String regInstitute(Institute institute)
	{
		if(daoImpl.addNewInstitute(institute))
			return "instituteSuccessRegister";
		else
			return "errorRegister";
	}
	
	/*
	@RequestMapping(value="/viewInststudents")
	public String viewInststudents(Model m,
			HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		String instituteID = (String)
				session.getAttribute("instituteID");
		
		List<Course> lstCourses = 
		cdaoImpl.getAllCoursesByInstitute(instituteID);
		
		List<List<Student>> lst = 
				new ArrayList<List<Student>>();
		
		if(lstCourses.size() > 0) {
			for(Course c : lstCourses) {
				List<Student> lstStudent = 
				sdaoImpl.getAllStudentsForCourse(
						c.getCourseID());
				if(lstStudent.size() > 0)
					lst.add(lstStudent);
			}		
		}
		
		m.addAttribute("LIST",lst);
		return "student-table";
	}*/
	
	@RequestMapping("/viewinstitutes")
	public String viewInstitutes(Model m) {
		List<Institute> lst = daoImpl.getAllInstitutes();
		m.addAttribute("ILIST", lst);
		return "institute-table";
	}
}








