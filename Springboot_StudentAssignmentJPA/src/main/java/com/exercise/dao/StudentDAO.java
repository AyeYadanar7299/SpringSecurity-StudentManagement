package com.exercise.dao;

import java.util.List;

import com.exercise.dto.StudentDTO;

public interface StudentDAO {
	
	public int insert(StudentDTO dto);
	
	public int update(StudentDTO dto);
	
	public int delete(StudentDTO dto);
	
	public List<StudentDTO> select();
	
	public List<StudentDTO> selectOne(StudentDTO dto);
	
	public StudentDTO selectone(StudentDTO dto);

}
