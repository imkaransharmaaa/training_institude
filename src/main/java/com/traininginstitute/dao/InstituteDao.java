package com.traininginstitute.dao;

import java.util.List;

import com.traininginstitute.pojo.Institute;

public interface InstituteDao {

	//Update institute profile
	boolean updateInstitute(Institute institute);
	
	//delete Institute
	boolean deleteInstitute(String instituteID);
	
	//add new Institute
	boolean addNewInstitute(Institute institute);
	
	//check Institute
	boolean checkInstitute(Institute institute);
	
	//get all Institutes
	List<Institute> getAllInstitutes();
	
	//get Institute by course
	Institute getInstituteByCourse(int courseID);
		
	//get details of specified institute
	Institute getInstituteDetails(String instituteID);
}



