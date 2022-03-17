package com.exercise;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.exercise.dao.UserRepository;
import com.exercise.dao.UserService;
import com.exercise.dto.User;

@SpringBootTest
public class ServiceTest 
{
	@Mock
	UserRepository urepo;
	
	@InjectMocks
	UserService uservice;
	
	@Test
	public void getAllUsers()
	{
		List<User> list= new ArrayList<>();
		User u1 = new User();
		u1.setId("U1");
		u1.setName("BB");
		u1.setPassword("123");
		u1.setConfirm("123");
		
		User u2 = new User();
		u2.setId("U2");
		u2.setName("KK");
		u2.setPassword("123");
		u2.setConfirm("123");
		
		list.add(u1);
		list.add(u2);
		
		when(urepo.findAll()).thenReturn(list);
		List<User>ulist = uservice.select();
		assertEquals(2, ulist.size());
		verify(urepo, times(1)).findAll();
	}


}
