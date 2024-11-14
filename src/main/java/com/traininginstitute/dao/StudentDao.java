package com.traininginstitute.dao;

import java.util.List;

import com.traininginstitute.pojo.Student;

public interface StudentDao {

	boolean checkStudent(Student student);
    boolean updateStudent(Student student);
	boolean addNewStudent(Student student);
	List<Student> getAllStudents();
	Student getStudentDetails(String studentID);
	List<Student> getAllStudentsForCourse(int courseID);
}
