package com.traininginstitute.pojo;

public class Course {
	
	private int courseID;
	private String courseName;
	private int price;
	private int seats;
	private int filledseats;
	
	public Course() {
	}

	public int getCourseID() {
		return courseID;
	}

	public void setCourseID(int courseID) {
		this.courseID = courseID;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public int getFilledseats() {
		return filledseats;
	}

	public void setFilledseats(int filledseats) {
		this.filledseats = filledseats;
	}
}
