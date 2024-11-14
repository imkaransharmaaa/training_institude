package com.traininginstitute.dao;

import java.util.List;

import com.traininginstitute.pojo.Faculty;

public interface FacultyDao {

	//add Faculty
	boolean addNewFaculty(Faculty faculty);
		
	//del faculty
	boolean deleteFaculty(int facultyID);
		
	//show all faculties
	List<Faculty> getAllFaculties();
		
	//show all faculties by Institute
	List<Faculty> getAllFacultiesByInstitute(
	    		String instituteID);
}
