package com.exercise.dao;

import java.util.List;

import com.exercise.dto.ClassDTO;
import com.exercise.dto.UserDTO;

public interface ClassDAO {
	
	public int insert(ClassDTO dto);
	
	public List<ClassDTO> select();
	
	public List<ClassDTO> selectone(ClassDTO dto);
	
	
}
