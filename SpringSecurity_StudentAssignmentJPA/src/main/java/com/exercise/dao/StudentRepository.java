package com.exercise.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercise.dto.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,String> 
{
	List <Student> findByStudentIdOrStudentNameOrClassName(String studentId,String studentName,String className);

}
