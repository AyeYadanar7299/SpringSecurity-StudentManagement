package com.exercise.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.model.ClassBean;
import com.exercise.dao.ClassDAOImpl;
import com.exercise.dto.ClassDTO;

@Controller
public class ClassController {
	@Autowired
	private ClassDAOImpl dao;
	@ModelAttribute("ClassBean")
	public ClassBean getBookBean() 
	{
		return new ClassBean();
	}
	@RequestMapping(value = "/classform", method = RequestMethod.GET)
	public ModelAndView addclass() {
		return new ModelAndView("BUD003", "classes", new ClassBean());
	}
	@RequestMapping(value="/setupaddclass",method=RequestMethod.POST)
	public String setupaddclass(@ModelAttribute("classes")@Validated ClassBean bean,BindingResult bs,ModelMap map) {
		if(bs.hasErrors()) {
			return "BUD003";
		}
		ClassDTO dto=new ClassDTO();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		List<ClassDTO> list=dao.select();
		if(list.size()!=0)
			map.addAttribute("err", "Class has been already exist!!");
		else {
			int res=dao.insert(dto);
			if(res>0)
				map.addAttribute("msg","Insert successful");
			else
				map.addAttribute("err","Insert fail");
		}
			return "BUD003";
	}
}
