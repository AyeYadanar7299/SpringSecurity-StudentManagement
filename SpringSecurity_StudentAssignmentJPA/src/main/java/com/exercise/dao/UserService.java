package com.exercise.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exercise.dto.User;
import com.exercise.model.ListBean;

@Service
public class UserService 
{
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ClassService cservice;
	
	@Autowired
	StudentService sservice;
	
	public int insert(User user) 
	{
		int res=0;
		userRepository.save(user);
		res=1;
		return res;
	}
	
	public int update(User user, String id) 
	{
		int res=0;
		userRepository.save(user);
		res=1;
		return res;
	}
	
	public int delete (String id)
	{
		int res=0;
		userRepository.deleteById(id);
		res=1;
		return res;
	}
	
	public Optional<User> selectOne(String id)
	{
		return userRepository.findById(id);
	}
	
	
	public List<User> select()
	{
		List<User> list =(List<User>) userRepository.findAll();
		return list;
	}
	
	public List<User> findByIdandName(String id,String name)
	{
		return userRepository.findByIdOrName(id, name);
	}
	
	public ListBean subreport()
	{
		ListBean lbean=new ListBean();
		lbean.setClassList(cservice.select());
		lbean.setStudentList(sservice.select());
		lbean.setUserList(userRepository.findAll());
		
		return lbean;
		
	}

}
