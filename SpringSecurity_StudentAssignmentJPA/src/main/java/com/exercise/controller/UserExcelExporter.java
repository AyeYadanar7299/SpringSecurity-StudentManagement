package com.exercise.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.exercise.dto.User;

public class UserExcelExporter 
{
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private List<User> listUsers;
	
	private void writeHeaderRows()
	{
		Row row = sheet.createRow(0);
		
		Cell cell= row.createCell(0);
		cell.setCellValue("User Id");
		
		cell= row.createCell(1);
		cell.setCellValue("User Name");
		
	}
	
	private void writeDataRows()
	{
		int rowCount = 1;
		
		for(User user: listUsers)
		{
			Row row = sheet.createRow(rowCount++);
			Cell cell= row.createCell(0);
			cell.setCellValue(user.getId());
			
			cell= row.createCell(1);
			cell.setCellValue(user.getName());
		}
	}
	
	public void export(HttpServletResponse response) throws IOException
	{
		writeHeaderRows();
		writeDataRows();
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

	public UserExcelExporter(List<User> listUsers) 
	{
		super();
		this.listUsers = listUsers;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Users");
	}

}
