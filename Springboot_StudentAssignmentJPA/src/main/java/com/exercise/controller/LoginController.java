package com.exercise.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.model.UserBean;
import com.exercise.dao.UserDAOImpl;
import com.exercise.dto.UserDTO;

@Controller
@SessionAttributes("session")
public class LoginController {
	@Autowired
	private UserDAOImpl dao;
	@ModelAttribute("UserBean")
	public UserBean getBookBean() {
	return new UserBean();
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("LGN001", "user", new UserBean());
	}
	//login - post
	@RequestMapping(value = "/setuplogin", method = RequestMethod.POST)
	public String setuplogin(@ModelAttribute("user") @Validated UserBean bean, BindingResult rs, ModelMap map) {

	if(rs.hasErrors()) {
		
		return "LGN001";
	}
 		UserDTO dto = new UserDTO();

		dto.setId(bean.getId());
		List<UserDTO> list = dao.select();
		if (list.size() == 0) {
		
			map.addAttribute("err", "User not found!!");
			return "LGN001";
		} 
		else if (bean.getPassword().equals(list.get(0).getPassword())) 
		{
			
			map.addAttribute("session", list.get(0));
			return "M00001";
		}
		else 
		{
			map.addAttribute("err", "Password is incorrect!!");
			return "LGN001";
		}

	}
	//logout
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		return "redirect:/";
	}
	//usersearch - get
	@RequestMapping(value="/usersearch",method=RequestMethod.GET)

	public ModelAndView usersearch() {
		return new ModelAndView("USR001","user",new UserBean());
	}
	//user register - get
	@RequestMapping(value="useradd",method=RequestMethod.GET)
	public ModelAndView useradd() {
		return new ModelAndView("USR002","user",new UserBean());
	}
	//user register - post
	@RequestMapping(value="/setupadduser",method=RequestMethod.POST)
	public String setupuseradd(@ModelAttribute("user")@Validated UserBean bean,BindingResult bs,ModelMap map) {
		if(bs.hasErrors()) {
		
			return "USR002";
		}
				if(bean.getPassword().equals(bean.getConfirm())) {
				UserDTO dto=new UserDTO();
				dto.setId(bean.getId());
				dto.setName(bean.getName());
				dto.setPassword(bean.getPassword());
			List<UserDTO> list=dao.selectOne(dto);
			if(list.size()!=0)
				map.addAttribute("err","User Id has been already!!");
			else {
				int res=dao.insert(dto);
				if(res>0) 
					map.addAttribute("msg", "Insert successfully");
				else
					map.addAttribute("err","Insert fail");
					return "USR002";
			}
			
			}else
				map.addAttribute("err","Password are not match");
		
		return "USR002";
	}
	//user register - usersearch
	@RequestMapping(value="/setupusersearch",method = RequestMethod.POST)
	public String setupusersearch(@ModelAttribute("user")UserBean bean,ModelMap map) {
		UserDTO dto=new UserDTO();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		List<UserDTO> list = new ArrayList<>();
		if(!dto.getId().equals("")||!dto.getName().equals(""))
			list=dao.selectOne(dto);
	
		else
			list=dao.select();
		
			map.addAttribute("userlist", list);
	return "USR001";
	}
	//user delete - get
	@RequestMapping(value="/userdelete",method = RequestMethod.GET)
	public String userdelete(@RequestParam ("id") String id ,ModelMap map) {
		UserDTO dto=new UserDTO();
		dto.setId(id);
		int res=dao.delete(dto);
		if(res>0) 
			map.addAttribute("msg","Delete successful!!");
		else
			map.addAttribute("err", "Delete fail");
		
		return "redirect:/";
	}
	@RequestMapping(value="/setupuserupdate",method = RequestMethod.GET)
	
	public ModelAndView userupdate(@RequestParam ("id") String id) {
		UserDTO dto=new UserDTO();
		dto.setId(id);
		return new ModelAndView("USR002-01","user",dao.selectone(dto));
	}
	@RequestMapping(value="userupdate",method=RequestMethod.POST)
	public String updateuser(@ModelAttribute("user")@Validated UserBean bean,BindingResult bs,ModelMap map) {
		if(bs.hasErrors()) {
		
			return "USR002-01";
		}
				if(bean.getPassword().equals(bean.getConfirm())) 
			{
				UserDTO dto=new UserDTO();
				dto.setId(bean.getId());
				dto.setName(bean.getName());
				dto.setPassword(bean.getPassword());
				dao.update(dto);
				return "USR002-01";
			}
			
		return "redirect:/usersearch";
	}
	
	@RequestMapping(value="userreset",method=RequestMethod.GET)
	public String reset() {
		return "redirect:/usersearch";
	}
}
