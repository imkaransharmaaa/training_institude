package com.traininginstitute.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.traininginstitute.dao.CourseDao;
import com.traininginstitute.dao.FacultyDao;
import com.traininginstitute.pojo.Course;
import com.traininginstitute.pojo.Faculty;

@Controller
public class FacultyController {

	@Autowired
	private FacultyDao daoImpl;
	@Autowired
	private CourseDao cdaoImpl;
	
	
	@RequestMapping("/registerFacultyForm")
	public String registerFacultyForm(HttpServletRequest
			 request, Model m) {
		
		HttpSession session = request.getSession(false);
		
		String instituteID = (String)
				session.getAttribute("instituteID");
		List<Course> courses = 
		cdaoImpl.getAllCoursesByInstitute(instituteID);
		
		m.addAttribute("COURSELIST", courses);
		
		return "addFaculty";
	}
	
	@RequestMapping("/registerFaculty")
	public String registerFaculty(Faculty faculty,
			HttpServletRequest request) {
		
		HttpSession session = request.getSession(false);
		
		String instituteID = (String)
				session.getAttribute("instituteID");
		faculty.setInstituteID(instituteID);
		
		if(daoImpl.addNewFaculty(faculty)) {
			return "addFacultySuccess";
		} else {
			return "addFacultyError";
		}
	}
	
	@RequestMapping("/delFacultyForm")
	public String delfacultyForm(HttpServletRequest
			 request , Model m) {
		HttpSession session = request.getSession(false);
		String instituteID = (String)
				session.getAttribute("instituteID");
		
		List<Faculty> lst = 
		daoImpl.getAllFacultiesByInstitute(instituteID);
		
		m.addAttribute("FACULTYLIST", lst);
		return "delete-faculty";
	}
	
	@RequestMapping("/deleteFaculty")
	public String deleteFaculty(
	@RequestParam("facultyID") String fid) {
		
		int facultyID = Integer.parseInt(fid);
		
		if(daoImpl.deleteFaculty(facultyID)) {
			return "delFSuccess";
		}
		else
			return "delFError";
	}
	
	@RequestMapping("/showAllFaculties")
	public String showAllFaculties(HttpServletRequest
			request, Model m) {
		
		HttpSession session = request.getSession(false);
		String instituteID = (String)
				session.getAttribute("instituteID");
		List<Faculty> lst = 
		daoImpl.getAllFacultiesByInstitute(instituteID);
		m.addAttribute("FACULTYLIST", lst);
		
		return "faculty-table";
	}
	
	@RequestMapping("/showfaculties")
	public String showFaculties(Model m) {
		List<Faculty> lst = daoImpl.getAllFaculties();
		m.addAttribute("FACULTYLIST", lst);
		return "faculty-table";
	}
}










