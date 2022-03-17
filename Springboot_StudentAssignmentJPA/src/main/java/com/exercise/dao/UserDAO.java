package com.exercise.dao;

import java.util.List;

import com.exercise.dto.UserDTO;

public interface UserDAO {

	public int insert(UserDTO dto);
	
	public int update(UserDTO dto);
	
	public int delete(UserDTO dto);
	
	public List<UserDTO> select();
	
	public List<UserDTO> selectOne(UserDTO dto);
	
	public UserDTO selectone(UserDTO dto);
}
