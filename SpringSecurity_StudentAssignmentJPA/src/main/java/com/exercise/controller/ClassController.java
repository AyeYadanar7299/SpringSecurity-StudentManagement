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
import com.exercise.dao.ClassService;
import com.exercise.dto.Class;

@Controller
public class ClassController {
	@Autowired
	private ClassService dao;
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
		Class dto=new Class();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		List<Class> list=dao.select();
		
			dao.insert(dto);
				map.addAttribute("msg","Insert successful");
			
			return "BUD003";
	}
}
