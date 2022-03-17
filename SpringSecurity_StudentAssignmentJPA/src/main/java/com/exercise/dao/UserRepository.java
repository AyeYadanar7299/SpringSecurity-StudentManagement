package com.exercise.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exercise.dto.User;

@Repository
public interface UserRepository extends JpaRepository<User,String> 
{
	List<User> findByIdOrName(String id,String name);
	
}
