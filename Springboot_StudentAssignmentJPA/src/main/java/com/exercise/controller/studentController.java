package com.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.exercise.model.StudentBean;
import com.exercise.dao.ClassDAOImpl;
import com.exercise.dao.StudentDAOImpl;
import com.exercise.dto.ClassDTO;

import com.exercise.dto.StudentDTO;
import com.exercise.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;
@Controller
public class studentController {
	@Autowired
	private StudentDAOImpl dao;
	@Autowired
	private ClassDAOImpl cdao;

	
	@RequestMapping(value="/addstudent",method=RequestMethod.GET)
	public ModelAndView addstudent(ModelMap map) {
		ClassDTO dto=new ClassDTO();
		dto.setId("");
		dto.setName("");
		List<ClassDTO> list=cdao.select();
		List<String> l=new ArrayList<>();
		for (int i=0;i<list.size();i++)
		{
			l.add(list.get(i).getName());
		}
		map.addAttribute("clist", l);
		return new ModelAndView("BUD002","student",new StudentBean());
	}
	@RequestMapping(value="/setupaddstudent",method = RequestMethod.POST)
	public String setupaddstudent(@ModelAttribute("student")@Validated StudentBean bean,BindingResult bs,ModelMap map) {
		if(bs.hasErrors()) 
		{
			return "BUD002";
		}
		StudentDTO dto=new StudentDTO();
		 dto.setStudentId(bean.getStudentId());
		 dto.setStudentName(bean.getStudentName());
		 dto.setClassName(bean.getClassName());
		 dto.setRegister(bean.getRegister());
		 dto.setStatus(bean.getStatus());
		 List<StudentDTO> list=dao.select();
	 
		 int res=dao.insert(dto);
		 if(res>0)
			 map.addAttribute("msg","Insert successfully!!");
		 else
			 map.addAttribute("err","Insert fail");
	
	 	 return "BUD002";
	}
	@RequestMapping(value="searchstudent",method = RequestMethod.GET)
	public ModelAndView searchstudent() {
		return new ModelAndView("BUD001","student",new StudentBean());
	}
	@RequestMapping(value="setupsearchstudent",method = RequestMethod.POST)
	public String setupsearchstudent(@ModelAttribute("student")StudentBean bean,ModelMap map) {
		StudentDTO dto=new StudentDTO();
		dto.setStudentId(bean.getStudentId());
		dto.setStudentName(bean.getStudentName());
		dto.setClassName(bean.getClassName());
		List<StudentDTO> list = new ArrayList<>();
		if(!dto.getStudentId().equals("")||!dto.getStudentName().equals("")||!dto.getClassName().equals(""))
			list=dao.selectOne(dto);
	
		else
			list=dao.select();
		
			map.addAttribute("stulist", list);

		return "BUD001";
		
	}
	@RequestMapping(value="/studentupdate",method = RequestMethod.GET)
	public ModelAndView updatestudent(@RequestParam ("studentId") String studentId,ModelMap map) {
		
		ClassDTO cdto=new ClassDTO();
		cdto.setId("");
		cdto.setName("");
		List<ClassDTO> list=cdao.select();
		List<String> l=new ArrayList<>();
		for (int i=0;i<list.size();i++)
		{
			l.add(list.get(i).getName());
		}
		map.addAttribute("clist", l);
		
		StudentDTO dto=new StudentDTO();
		dto.setStudentId(studentId);
		return new ModelAndView("BUD002-01","student",dao.selectone(dto));
	}
	@RequestMapping(value="/setupupdatestudent",method = RequestMethod.POST)
	public String setupupdatestudent(@ModelAttribute("student")@Validated StudentBean bean,BindingResult bs,ModelMap map) {
		if(bs.hasErrors()) 
		{
			
			return "BUD002-01";
		}
		StudentDTO dto=new StudentDTO();
	 dto.setStudentId(bean.getStudentId());
	 dto.setStudentName(bean.getStudentName());
	 dto.setClassName(bean.getClassName());
	 dto.setRegister(bean.getRegister());
	 dto.setStatus(bean.getStatus());
	 dao.update(dto);
	 
	 return "BUD002-01";
	}
	@RequestMapping(value="/deletestudent",method = RequestMethod.GET)
	public String deletestudent(@RequestParam ("studentId")  String studentId,ModelMap map) {
		StudentDTO dto=new StudentDTO();
		dto.setStudentId(studentId);
		int res=dao.delete(dto);
		if(res>0) 
			map.addAttribute("msg","Delete successful!!");
	
		else
			map.addAttribute("err", "Delete fail");
		
		return "redirect:/searchstudent";
	}
	@RequestMapping(value="studentreset",method=RequestMethod.GET)
	public String reset() {
		return "redirect:/searchstudent"; 
	}
	
}

