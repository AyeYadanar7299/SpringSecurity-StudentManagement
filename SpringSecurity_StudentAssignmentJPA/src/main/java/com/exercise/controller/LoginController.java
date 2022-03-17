package com.exercise.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.model.ListBean;
import com.exercise.model.UserBean;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import com.exercise.dao.UserService;
import com.exercise.dto.User;

@Controller
@SessionAttributes("session")
public class LoginController {
	@Autowired
	private UserService dao;

	@Autowired
	private BCryptPasswordEncoder encode;
	
	@ModelAttribute("UserBean")
	public UserBean getBookBean() {
		return new UserBean();
	}
	

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView login() {
		return new ModelAndView("LGN001", "user", new UserBean());
	}

	// login - post
	@RequestMapping(value = "/setuplogin", method = RequestMethod.POST)
	public String setuplogin(@ModelAttribute("user") @Validated UserBean bean, BindingResult rs, ModelMap map) {

		if (rs.hasErrors()) {

			return "LGN001";
		}
		User dto = new User();

		dto.setId(bean.getId());
		List<User> list = dao.select();
		if (list.size() == 0) {

			map.addAttribute("err", "User not found!!");
			return "LGN001";
		} else if (bean.getPassword().equals(list.get(0).getPassword())) {

			map.addAttribute("session", list.get(0));
			return "M00001";
		} else {
			map.addAttribute("err", "Password is incorrect!!");
			return "LGN001";
		}

	}

	// logout
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		return "redirect:/";
	}

	// usersearch - get
	@RequestMapping(value = "/usersearch", method = RequestMethod.GET)

	public ModelAndView usersearch() {
		return new ModelAndView("USR001", "user", new UserBean());
	}

	// user register - get
	@RequestMapping(value = "useradd", method = RequestMethod.GET)
	public ModelAndView useradd() {
		return new ModelAndView("USR002", "user", new UserBean());
	}

	// user register - post
	@RequestMapping(value = "/setupadduser", method = RequestMethod.POST)
	public String setupuseradd(@ModelAttribute("user") @Validated UserBean bean, BindingResult bs, ModelMap map) {
		if (bs.hasErrors()) {

			return "USR002";
		}
		if (bean.getPassword().equals(bean.getConfirm())) {
			User dto = new User();
			dto.setId(bean.getId());
			dto.setName(bean.getName());
			dto.setPassword(bean.getPassword());
			
				dao.insert(dto);
				map.addAttribute("msg", "Insert successfully");

				return "USR002";
			

		} else
			map.addAttribute("err", "Password are not match");

		return "USR002";
	}

	// user register - usersearch
	@RequestMapping(value = "/setupusersearch", method = RequestMethod.POST)
	public String setupusersearch(@ModelAttribute("user") UserBean bean, ModelMap map) {
		User dto = new User();
		List<User> list = new ArrayList<>();
		dto.setId(bean.getId());
		dto.setName(bean.getName());
		if(!dto.getId().equals("")|| !dto.getName().equals(""))
		{
			list= dao.findByIdandName(dto.getId(),dto.getName());
		}
		else
			list = dao.select();
		map.addAttribute("userlist", list);
		return "USR001";
	}

	// user delete - get
	@RequestMapping(value = "/userdelete", method = RequestMethod.GET)
	public String userdelete(@RequestParam("id") String id, ModelMap map) {
		User dto = new User();
		dto.setId(id);
		dao.delete(id);

		map.addAttribute("msg", "Delete successful!!");

		return "redirect:/usersearch";
	}

	@RequestMapping(value = "/setupuserupdate", method = RequestMethod.GET)

	public ModelAndView userupdate(@RequestParam("id") String id) {
		User dto = new User();
		dto.setId(id);
		return new ModelAndView("USR002-01", "user", dao.selectOne(id));
	}

	@RequestMapping(value = "userupdate", method = RequestMethod.POST)
	public String updateuser(@ModelAttribute("user") @Validated UserBean bean, BindingResult bs, ModelMap map) {
		if (bs.hasErrors()) {

			return "USR002-01";
		}
		if (bean.getPassword().equals(bean.getConfirm())) {
			User dto = new User();
			dto.setId(bean.getId());
			dto.setName(bean.getName());
			dto.setPassword(bean.getPassword());
			dao.update(dto, dto.getId());
			return "USR002-01";
		}

		return "redirect:/";
	}

	@RequestMapping(value = "userreset", method = RequestMethod.GET)
	public String reset() {
		return "redirect:/usersearch";
	}
	
	@GetMapping("/usreport")
	public ResponseEntity<byte[]> generatePdf() throws Exception, JRException 
	{
	
		JRBeanCollectionDataSource bcdataSource= new JRBeanCollectionDataSource(dao.select());
		JasperReport compileReport=JasperCompileManager.compileReport(new FileInputStream("src/main/resources/reports/UserReport.jrxml"));
		
		HashMap<String, Object> map= new HashMap<>();
		JasperPrint report=JasperFillManager.fillReport(compileReport, map, bcdataSource);

		
		byte[] data=JasperExportManager.exportReportToPdf(report);
		
		HttpHeaders headers=new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=UserReport.pdf");
		
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
		
	}
	
	@GetMapping("/userexport")
	public void exportToExcel(HttpServletResponse response) throws IOException
	{
		response.setContentType("application/octet-stream");
		
		String headerKey="Content-Disposition";
		
		String headerValue="attachment; filename=users.xlsx";
		
		response.setHeader(headerKey, headerValue);
		
		List<User> listUsers = dao.select();
		
		 UserExcelExporter excelExporter = new UserExcelExporter(listUsers);
		 excelExporter.export(response);
	}
	
	@GetMapping("/mainreport")
	public ResponseEntity<byte[]> subPdf() throws Exception, JRException 
	{
	
		List<ListBean> list=new ArrayList<>();
		list.add(dao.subreport());
		JRBeanCollectionDataSource bcdataSource= new JRBeanCollectionDataSource(list);
		JasperReport compileReport=JasperCompileManager.compileReport(new FileInputStream("src/main/resources/mainreport/MainReport.jrxml"));
		
		String clist="src/main/resources/mainreport/ClassSub.jasper";
		String slist="src/main/resources/mainreport/StudentSub.jasper";
		String ulist="src/main/resources/mainreport/UserSub.jasper";
		
		HashMap<String, Object> map= new HashMap<>();
		map.put("userlist", ulist);
		map.put("studentlist", slist);
		map.put("classlist", clist);
		JasperPrint report=JasperFillManager.fillReport(compileReport, map, bcdataSource);

		
		byte[] data=JasperExportManager.exportReportToPdf(report);
		
		HttpHeaders headers=new HttpHeaders();
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline;filename=UserReport.pdf");
		
		
		return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
		
	}
}
