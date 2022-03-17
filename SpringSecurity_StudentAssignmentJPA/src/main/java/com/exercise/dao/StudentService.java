package com.exercise.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.dto.Student;

@Service
public class StudentService 
{
	@Autowired
	StudentRepository studentRepository;
	
	public int insert(Student student) 
	{
		int res=0;
		studentRepository.save(student);
		res=1;
		return res;
	}
	
	public int update(Student student, String student_id) 
	{
		int res=0;
		studentRepository.save(student);
		res=1;
		return res;
	}
	
	public int delete (String student_id)
	{
		int res=0;
		studentRepository.deleteById(student_id);
		res=1;
		return res;
	}
	
	public Optional<Student> selectOne(String student_id)
	{
		return studentRepository.findById(student_id);
	}
	
	public List<Student> select()
	{
		List<Student> list = (List<Student>) studentRepository.findAll();
		return list;
	}

	public List<Student> findByStudentIdOrStudentNameOrClassName(String studentId, String studentName,String className)
	{
		return studentRepository.findByStudentIdOrStudentNameOrClassName(studentId, studentName, className);
	}
	
	


}
