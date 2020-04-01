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
import com.dfs.util.Constants;
import org.apache.poi.ss.usermodel.Font;

import java.io.FileOutputStream;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

@SuppressWarnings("all")
public class WriteWorkBook {
	public static void main(String[] args) throws IOException {
	try {
		readXml();
	} catch (Exception e) {
		e.printStackTrace();
	}
	}
	public static void addInExcel(String filePattern, File fileName,String lineNum, String cateGory, String fileType, String textPattern, String getRepl) throws InvalidFormatException, IOException {
		FileInputStream inputStream;
		try {
			FileInputStream myxls = new FileInputStream("discrepancy-output-files\\discrepancy-list.xls");
		       HSSFWorkbook workBook = new HSSFWorkbook(myxls);
		       HSSFSheet worksheet = workBook.getSheetAt(0);
		       int lastRow=worksheet.getLastRowNum();
		       System.out.println(lastRow);
		       Row row = worksheet.createRow(++lastRow);
		       if(null!=filePattern && null!=fileName.toString() && null!=lineNum 
		    		   && null!=cateGory && null!=fileType && null!=textPattern && null!=getRepl) {
		       row.createCell(0).setCellValue(filePattern);
		       row.createCell(1).setCellValue(fileName.toString());
		       row.createCell(2).setCellValue(lineNum);
		       if(cateGory.equalsIgnoreCase(Constants.MANDATORY)) {
		    	   CellStyle style = workBook.createCellStyle();
		    	   style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		    	   style.setFillForegroundColor(HSSFColor.ROSE.index);
		    	   Font font = workBook.createFont();
		           font.setColor(HSSFColor.RED.index);
		           style.setFont(font);
		    	   row.createCell(3).setCellValue(cateGory);
		    	   row.getCell(3).setCellStyle(style);
		       }else if(cateGory.equalsIgnoreCase(Constants.OPTIONAL)) {
		    	   CellStyle style = workBook.createCellStyle();
		    	   style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); 
		    	   style.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
		    	   Font font = workBook.createFont();
		           font.setColor(HSSFColor.GREEN.index);
		           style.setFont(font);
		    	   row.createCell(3).setCellValue(cateGory);
		    	   row.getCell(3).setCellStyle(style);
		       }
		       row.createCell(4).setCellValue(fileType);
		       row.createCell(5).setCellValue(textPattern);
		       row.createCell(6).setCellValue(getRepl);
		       row.createCell(7).setCellValue(1);
		       row.createCell(8).setCellValue("yes");
		       row.createCell(9).setCellValue(2);
		       }
		       myxls.close();
		       FileOutputStream output_file =new FileOutputStream(new File("discrepancy-output-files\\\\discrepancy-list.xls"));  
		       //write changes
		       workBook.write(output_file);
		       output_file.close();
		       System.out.println(" is successfully written");
		       
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
        
public static void readXml() throws InvalidFormatException, IOException {
	File jaavaRulrXml = new File("resources\\java-rules.xml");
	try {
		List<String> xmlCodeLineList = new ArrayList<String>();
		String filePatternVal = "";
		String complexityValue = "";
		String category = "";
		String ruleType = "";
		String textPattern = "";
		String replatformAdvice = "";
		Scanner scanner;
		scanner = new Scanner(jaavaRulrXml);
		while (scanner.hasNextLine()) {
			xmlCodeLineList.add(scanner.nextLine().trim());
		}
		XmlMapper xmlMapper = new XmlMapper();
		StringBuilder xml = new StringBuilder();
		xmlCodeLineList.forEach(xml::append);
		DiscrepancyRules discrepancyRules = xmlMapper.readValue(xml.toString(), DiscrepancyRules.class);
		List<Rule> ruleList = (List<Rule>) discrepancyRules.getRule();
        
        if (ruleList.size() > 0) {
			for (int temp = 0; temp < ruleList.size(); temp++) {
				//Updated code for null pointer error
				complexityValue = (ruleList.get(temp).getComplexity()== null) ? "": ruleList.get(temp).getComplexity().getValue();
				filePatternVal = (ruleList.get(temp).getFile_pattern()== null) ? "" :ruleList.get(temp).getFile_pattern().getValue();
				filePatternVal = ruleList.get(temp).getFile_pattern().getValue();
				filePatternVal =  filePatternVal.replaceAll("[*.]","");
				category = (ruleList.get(temp).getCategory()== null) ? "" :ruleList.get(temp).getCategory();
				ruleType = (ruleList.get(temp).getType()== null) ? "" :ruleList.get(temp).getType();
				textPattern = (ruleList.get(temp).getText_pattern()== null) ? "": ruleList.get(temp).getText_pattern().getValue();
				replatformAdvice = (ruleList.get(temp).getRemediation()== null) ? "":ruleList.get(temp).getRemediation().getRecommendation();
				addInExcel(filePatternVal,jaavaRulrXml,complexityValue
						,category,ruleType,textPattern,replatformAdvice);
}
			}
	}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(NullPointerException e) {
			// TODO Auto-generated catch block
						e.printStackTrace();
		}

	}
}
	