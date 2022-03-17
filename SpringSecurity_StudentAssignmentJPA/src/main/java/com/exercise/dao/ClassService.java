package com.exercise.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.dto.Class;

@Service
public class ClassService 
{
	@Autowired
	ClassRepository classRepository;	
	
	public int insert(Class classes)
	{
		int res=0;
		classRepository.save(classes);
	    res=1;
	    return res;
		
	}
	
	
	public Optional<Class> selectOne(String id)
	{
		return classRepository.findById(id);
	
	}
	
	public List<Class> select()
	{
		List<Class> list = (List<Class>) classRepository.findAll();
		return list;
	}
}
