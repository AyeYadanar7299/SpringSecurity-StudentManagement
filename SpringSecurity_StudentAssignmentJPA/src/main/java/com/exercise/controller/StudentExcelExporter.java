package com.exercise.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.exercise.dto.Student;

public class StudentExcelExporter 
{
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private List<Student> listStudent;
	
	private void writeHeaderRows()
	{
		Row row = sheet.createRow(0);
		
		Cell cell= row.createCell(0);
		cell.setCellValue("Student Id");
		
		cell= row.createCell(1);
		cell.setCellValue("Student Name");
		
		cell= row.createCell(2);
		cell.setCellValue("Class Name");
		
		cell= row.createCell(3);
		cell.setCellValue("Register");
		
		cell= row.createCell(4);
		cell.setCellValue("Status");
		
	}
	
	private void writeDataRows()
	{
		int rowCount = 1;
		
		for(Student student: listStudent)
		{
			Row row = sheet.createRow(rowCount++);
			Cell cell= row.createCell(0);
			cell.setCellValue(student.getStudentId());
			
			cell= row.createCell(1);
			cell.setCellValue(student.getStudentName());
			
			cell= row.createCell(2);
			cell.setCellValue(student.getClassName());
			
			cell= row.createCell(3);
			cell.setCellValue(student.getRegister());
			
			cell= row.createCell(4);
			cell.setCellValue(student.getStatus());
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

	public StudentExcelExporter(List<Student> listStudent) 
	{
		super();
		this.listStudent = listStudent;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Students");
	}

}
