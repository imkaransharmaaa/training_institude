package com.traininginstitute.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.traininginstitute.dao.AdminDao;
import com.traininginstitute.pojo.Admin;

@Controller
public class AdminController {

	@Autowired
	private AdminDao daoImpl;
	
	@RequestMapping(value="/acceptAdmin" , 
			method = RequestMethod.POST)
	public String checkAdmin(Admin admin) {
		
		if(daoImpl.checkAdmin(admin)) {
			return "admin-home";
		}
		else {
			return "admin-errorLogin";
		}
	}
}