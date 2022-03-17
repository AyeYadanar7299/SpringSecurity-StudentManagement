package com.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.exercise.model.StudentBean;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.exercise.dao.ClassService;
import com.exercise.dao.StudentService;
import com.exercise.dto.Class;
import com.exercise.dto.Student;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
@Controller
public class studentController {
	@Autowired
	private StudentService dao;
	@Autowired
	private ClassService cdao;

	
	@RequestMapping(value="/addstudent",method=RequestMethod.GET)
	public ModelAndView addstudent(ModelMap map) {
		Class dto=new Class();
		dto.setId("");
		dto.setName("");
		List<Class> list=cdao.select();
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
		Student dto=new Student();
		 dto.setStudentId(bean.getStudentId());
		 dto.setStudentName(bean.getStudentName());
		 dto.setClassName(bean.getClassName());
		 dto.setRegister(bean.getRegister());
		 dto.setStatus(bean.getStatus());
		 List<Student> list=dao.select();
	 
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
		Student dto=new Student();
		List<Student> list=new ArrayList<>();
		dto.setStudentId(bean.getStudentId());
		dto.setStudentName(bean.getStudentName());
		dto.setClassName(bean.getClassName());
		if(!dto.getStudentId().equals("")|| !dto.getStudentName().equals("")|| !dto.getClassName().equals("")|| !dto.getRegister().equals("") || !dto.getStatus().equals(""))
		{
			list= dao.findByStudentIdOrStudentNameOrClassName(dto.getStudentId(),dto.getStudentName(),dto.getClassName());
		}
		else
			list = dao.select();
		
			map.addAttribute("stulist", list);
		return "BUD001";
		
	}
	@RequestMapping(value="/studentupdate",method = RequestMethod.GET)
	public ModelAndView updatestudent(@RequestParam ("studentId") String studentId,ModelMap map) {
		
		Class cdto=new Class();
		cdto.setId("");
		cdto.setName("");
		List<Class> list=cdao.select();
		List<String> l=new ArrayList<>();
		for (int i=0;i<list.size();i++)
		{
			l.add(list.get(i).getName());
		}
		map.addAttribute("clist", l);
		
		Student dto=new Student();
		dto.setStudentId(studentId);
		return new ModelAndView("BUD002-01","student",dao.selectOne(studentId));
	}
	@RequestMapping(value="setupupdatestudent",method = RequestMethod.POST)
	public String setupupdatestudent(@ModelAttribute("student")@Validated StudentBean bean,BindingResult bs,ModelMap map) {
		if(bs.hasErrors()) 
		{
			
			return "BUD002-01";
		}
		Student dto=new Student();
	 dto.setStudentId(bean.getStudentId());
	 dto.setStudentName(bean.getStudentName());
	 dto.setClassName(bean.getClassName());
	 dto.setRegister(bean.getRegister());
	 dto.setStatus(bean.getStatus());
	 dao.update(dto, dto.getStudentId());
	 
	 return "BUD002-01";
	}
	@RequestMapping(value="/deletestudent",method = RequestMethod.GET)
	public String deletestudent(@RequestParam String studentId,ModelMap map) {
		Student dto=new Student();
		dto.setStudentId(studentId);
		dao.delete(studentId);
		
			map.addAttribute("msg","Delete successful!!");
		
		return "redirect:/searchstudent";
	}
	@RequestMapping(value="studentreset",method=RequestMethod.GET)
	public String reset() {
		return "redirect:/searchstudent"; 
	}
	
	@GetMapping("/studentreport")
	public ResponseEntity<byte[]> generatePdf() throws Exception, JRException 
	{
		JRBeanCollectionDataSource bcdataSource= new JRBeanCollectionDataSource(dao.select());
		JasperReport compileReport=JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reports/StudentReport.jrxml"));
		
		HashMap<String, Object> map= new HashMap<>();
		JasperPrint report=JasperFillManager.fillReport(compileReport, map, bcdataSource);

		
		byte[] data=JasperExportManager.exportReportToPdf(report);
		
		HttpHeaders headers=new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=StudentReport.pdf");
		
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
		
	}
	
	@GetMapping("/studentexport")
	public void exportToExcel(HttpServletResponse response) throws IOException
	{
		response.setContentType("application/octet-stream");
		
		String headerKey="Content-Disposition";
		
		String headerValue="attachment; filename=students.xlsx";
		
		response.setHeader(headerKey, headerValue);
		
		List<Student> listStudent = dao.select();
		
		 StudentExcelExporter excelExporter = new StudentExcelExporter(listStudent);
		 excelExporter.export(response);
	}
	
}

