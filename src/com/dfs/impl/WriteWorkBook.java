package com.dfs.impl;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.dfs.model.DiscrepancyRules;
import com.dfs.model.Rule;
import com.dfs.model.Discrepancy;
import com.dfs.util.Constants;
import org.apache.poi.ss.usermodel.Font;

import java.io.FileOutputStream;

import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

@SuppressWarnings("all")
public class WriteWorkBook {
	
	public static void addInExcel(Discrepancy discrepancy, String[] args) throws InvalidFormatException, IOException {
		copyFile(args);
		FileInputStream inputStream;
		FileInputStream myxls = new FileInputStream(args[2]+"\\discrepancy-list.xls");
	    HSSFWorkbook workBook = new HSSFWorkbook(myxls);
	    String  lineNo ="";
	    String category = "";
	    String pattern = "";
	    String fileType = "";
		String complexityValue = "";
		String ruleType = "";
		String recommendation = "";
		String autoRemediation = "";
		String timeSavingsInMin = "";
		try {
			
		       HSSFSheet worksheet = workBook.getSheetAt(0);
		       int lastRow=worksheet.getLastRowNum();
		       System.out.println(lastRow);
		       Row row = worksheet.createRow(++lastRow);
		       pattern = (discrepancy.getPattern()== null) ? "": discrepancy.getPattern();
		       fileType = (discrepancy.getFileType()== null) ? "": discrepancy.getFileType();
		       ruleType = (discrepancy.getRuleType()== null) ? "": discrepancy.getRuleType();
		       recommendation = (discrepancy.getRecommendation()== null) ? "": discrepancy.getRecommendation();
		       complexityValue = (discrepancy.getComplexity()== null) ? "": discrepancy.getComplexity();
		       autoRemediation = (discrepancy.getAutoRemediation()== null) ? "": discrepancy.getAutoRemediation();
		       timeSavingsInMin = (discrepancy.getTimeSavingsInMin()== null) ? "": discrepancy.getTimeSavingsInMin();
		       //fileName = 
		       if(null != discrepancy.getLineNo()) {
		      lineNo = Integer.toString(discrepancy.getLineNo());} 
		       row.createCell(0).setCellValue(fileType);
		       row.createCell(1).setCellValue(discrepancy.getFileName());
		       row.createCell(2).setCellValue(lineNo);
		       category = (discrepancy.getCategory()== null) ? "" :discrepancy.getCategory();
		       if(category.equalsIgnoreCase(Constants.MANDATORY)) {
		    	   CellStyle style = workBook.createCellStyle();
		    	   style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		    	   style.setFillForegroundColor(HSSFColor.ROSE.index);
		    	   Font font = workBook.createFont();
		           font.setColor(HSSFColor.RED.index);
		           style.setFont(font);
		    	   row.createCell(3).setCellValue(category);
		    	   row.getCell(3).setCellStyle(style);
		       }else if(category.equalsIgnoreCase(Constants.OPTIONAL)) {
		    	   CellStyle style = workBook.createCellStyle();
		    	   style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		    	   style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		    	   Font font = workBook.createFont();
		           font.setColor(HSSFColor.GREEN.index);
		           style.setFont(font);
		    	   row.createCell(3).setCellValue(category);
		    	   row.getCell(3).setCellStyle(style);
		       }
		       row.createCell(4).setCellValue(ruleType);
		       row.createCell(5).setCellValue(pattern);
		       row.createCell(6).setCellValue(recommendation);
		       row.createCell(7).setCellValue(complexityValue);
		       row.createCell(8).setCellValue(autoRemediation);
		       row.createCell(9).setCellValue(timeSavingsInMin);
		       myxls.close();
		       FileOutputStream output_file =new FileOutputStream(new File(args[2]+"\\discrepancy-list.xls"));  
		       //write changes
		       workBook.write(output_file);
		       output_file.close();
		       System.out.println(" is successfully written");
		       
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
public static void copyFile(String[] args) throws InvalidFormatException, IOException {
	try {
		    File dir = new File(args[2]);
			//File dir = new File(directory);
			if (!dir.exists()) {
				dir.mkdirs();
				System.out.println("Directory created");
			}
			File from = new File("resources\\sample-report.xls"); 
			File to = new File(args[2]+"\\discrepancy-list.xls");
			if(!to.exists()) {
			FileUtils.copyFile(from, to);
			System.out.println("file copied");
			}else {
			System.out.println("file exist already");
			}
	}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

	}
}
}
	