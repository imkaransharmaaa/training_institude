package com.traininginstitute.dao;

import java.util.List;

import com.traininginstitute.pojo.Course;

public interface CourseDao {

	int addNewCourse(Course course);
	boolean addCourseForInstitute(int courseID,
			String instituteID);
	boolean updateCourse(Course course);
	List<Course> getAllCourses();
	List<Course> getAllCoursesByInstitute(
			String instituteId);
	boolean isSeatAvailable(int courseID);
    boolean incrementFilledSeatByOne(int courseID);
    boolean updateStudentWithCourse(
    		String userID,int courseID);
    boolean addCourseForStudent(int courseID, 
			String studentID);
    List<Course> getAllCoursesByStudent(String studentID);
    Course getCourseInfo(int courseID);
}
