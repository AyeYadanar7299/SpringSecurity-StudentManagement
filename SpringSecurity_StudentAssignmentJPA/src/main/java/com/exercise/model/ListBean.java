package com.exercise.model;

import java.util.ArrayList;
import java.util.List;

import com.exercise.dto.Student;
import com.exercise.dto.User;

public class ListBean {

	private List<User> userList = new ArrayList<>();
	private List<Student> studentList = new ArrayList<>();
	private List<com.exercise.dto.Class> classList = new ArrayList<>();

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	public List<com.exercise.dto.Class> getClassList() {
		return classList;
	}

	public void setClassList(List<com.exercise.dto.Class> classList) {
		this.classList = classList;
	}

}
