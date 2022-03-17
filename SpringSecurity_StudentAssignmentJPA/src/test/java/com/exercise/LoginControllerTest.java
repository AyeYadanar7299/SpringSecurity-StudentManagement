package com.exercise;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.exercise.dao.UserRepository;
import com.exercise.dao.UserService;
import com.exercise.dto.User;
import com.exercise.model.UserBean;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoginControllerTest 
{
	@Autowired
	private MockMvc mockmvc;
	
	@MockBean
	private UserService bservice;
	
	@MockBean
	private UserRepository urepo;
	
	@Test
	public void addusertest() throws Exception
	{
		UserBean ubean=new UserBean();
		User user=new User();
		user.setId(ubean.getId());
		user.setName(ubean.getName());
		user.setPassword(ubean.getPassword());
		user.setConfirm(ubean.getConfirm());
		
		this.mockmvc.perform(post("/setupadduser"))
		.andExpect(status().isOk())
		.andExpect(redirectedUrl("USR002"));
		
	}
	
	@Test
	public void adduservalidate() throws Exception
	{
		this.mockmvc.perform(get("/useradd"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"));
	}
	
	@Test
	public void setupaddusertest() throws Exception
	{
		this.mockmvc.perform(get("/useradd"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"))
		.andExpect(model().attributeExists("user"));
	}
}
