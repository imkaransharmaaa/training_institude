package com.traininginstitute.controller;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.traininginstitute.dao.AdmissionRequestDao;
import com.traininginstitute.dao.CourseDao;
import com.traininginstitute.dao.InstituteDao;
import com.traininginstitute.pojo.AdmissionRequest;
import com.traininginstitute.pojo.Institute;

@Controller
public class AdmissionRequestController {

	@Autowired
	private AdmissionRequestDao daoImpl;
	
	@Autowired
	private InstituteDao idaoImpl;
	
	@Autowired
	private CourseDao cdaoImpl;
	
	@RequestMapping(
	"/viewstudentrequests/{instituteID}")
	public String showStudentRequests(
			@PathVariable("instituteID")
			String instituteID, Model m) {
		
		List<AdmissionRequest> lst = 
		daoImpl.viewAllRequestsForInstitute(instituteID);
		m.addAttribute("REQUESTLIST", lst);
		return "request-table";
	}
	
	@RequestMapping("/submitRequestForm")
	public String submitRequestForm(Model m) {
		List<Institute> lst = idaoImpl.getAllInstitutes();
		m.addAttribute("instituteList", lst);
		return "request-form";
	}
	
	@RequestMapping(value="/submitRequest" , 
			method=RequestMethod.POST)
	public String submitRequest(AdmissionRequest request 
			, Model m) {
		if(daoImpl.submitRequest(request)) {
			return "reqSentSuccess";
		}
		else
			return "reqSentError";
	}
	
	@RequestMapping("/approveStudentRequest/{requestID}")
	public String approveStudentRequest(
			@PathVariable("requestID") String reqID,
			Model m , HttpServletRequest request) {
		int requestID = Integer.parseInt(reqID);
		
		HttpSession session = request.getSession(false);
		String instituteID = (String)
				session.getAttribute("instituteID");
		if(daoImpl.approveRequest(requestID)) {
			AdmissionRequest adreq = 
					daoImpl.getRequestInfo(requestID);
			cdaoImpl.addCourseForStudent(
					adreq.getCourseID(), adreq.getUserID());
			List<AdmissionRequest> lst = 
			daoImpl.viewAllRequestsForInstitute(instituteID);
			m.addAttribute("REQUESTLIST", lst);
			return "request-table";
		}
		else
			return "delFError";
	}
	
	@RequestMapping("/viewresponses/{userID}")
	public String viewResponses(
			@PathVariable("userID") String userID, Model m) {
		
		List<AdmissionRequest> lst = 
				daoImpl.getAllRequestsForUser(userID);
		m.addAttribute("REQUESTLIST", lst);
		return "response-table";
	}
	
	@RequestMapping("/rejectStudentRequest/{requestID}")
	public String rejectStudentRequest(
			@PathVariable("requestID") String reqID,
			Model m , HttpServletRequest request) {
		int requestID = Integer.parseInt(reqID);
		
		HttpSession session = request.getSession(false);
		String instituteID = (String)
				session.getAttribute("instituteID");
		if(daoImpl.rejectRequest(requestID)) {
			List<AdmissionRequest> lst = 
			daoImpl.viewAllRequestsForInstitute(instituteID);
			m.addAttribute("REQUESTLIST", lst);
			return "request-table";
		}
		else
			return "delFError";
	}
}




