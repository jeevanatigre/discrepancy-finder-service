package com.dfs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.dfs.rule.DiscrepancyRules;
import com.dfs.rule.Rule;
import java.io.FileOutputStream;
 
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;

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
			FileInputStream myxls = new FileInputStream("resources\\sample-report.xls");
		       HSSFWorkbook studentsSheet = new HSSFWorkbook(myxls);
		       HSSFSheet worksheet = studentsSheet.getSheetAt(0);
		       int lastRow=worksheet.getLastRowNum();
		       System.out.println(lastRow);
		       Row row = worksheet.createRow(++lastRow);
		       if(null!=filePattern && null!=fileName.toString() && null!=lineNum 
		    		   && null!=cateGory && null!=fileType && null!=textPattern && null!=getRepl) {
		       row.createCell(0).setCellValue(filePattern);
		       row.createCell(1).setCellValue(fileName.toString());
		       row.createCell(2).setCellValue(lineNum);
		       row.createCell(3).setCellValue(cateGory);
		       row.createCell(4).setCellValue(fileType);
		       row.createCell(5).setCellValue(textPattern);
		       row.createCell(6).setCellValue(getRepl);
		       row.createCell(7).setCellValue(1);
		       row.createCell(8).setCellValue("yes");
		       row.createCell(9).setCellValue(2);
		       }
		       myxls.close();
		       FileOutputStream output_file =new FileOutputStream(new File("resources\\sample-report.xls"));  
		       //write changes
		       studentsSheet.write(output_file);
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
				String filePatternVal = ruleList.get(temp).getFile_pattern().getValue();
				ruleList.get(temp).getFile_pattern().setValue(filePatternVal.replaceAll("[*.]",""));
				System.out.println(filePatternVal);
				System.out.println(ruleList.get(temp).getFile_pattern().getValue());
				System.out.println(ruleList.get(temp).getComplexity().getValue());
				System.out.println(ruleList.get(temp).getReplatform().getAction());
				System.out.println(ruleList.get(temp).getReplatform().getAdvice());
				System.out.println(ruleList.get(temp).getText_pattern().getValue());
				System.out.println(ruleList.get(temp).getCategory());
				addInExcel(ruleList.get(temp).getFile_pattern().getValue(),jaavaRulrXml,ruleList.get(temp).getComplexity().getValue(),ruleList.get(temp).getCategory(),ruleList.get(temp).getType(),ruleList.get(temp).getText_pattern().getValue(),ruleList.get(temp).getReplatform().getAdvice());
				
}
        }
	}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}     

	}
}
	