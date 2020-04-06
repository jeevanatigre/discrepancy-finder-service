package com.dfs.report;

import java.io.File;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
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
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;

@SuppressWarnings("all")
public class ExcelReport implements IReport {
	
	public void createReport(List<Discrepancy> descrepancyDetailsList , String targetLocation) throws InvalidFormatException, IOException {
		System.out.println("Creating discrepency report 'discrepancy-list.xls'");
		String discrepancyReportFileName = "discrepancy-list.xls";
		ExcelReport.copyFileAtOutputLocation(targetLocation);
		FileInputStream inputStream;
		FileInputStream myxls = new FileInputStream(targetLocation + "\\" +discrepancyReportFileName);
	    HSSFWorkbook workBook = new HSSFWorkbook(myxls);
	    int  lineNo=0;
	    String category = "";
	    String filePattern = "";
	    String fileType = "";
		int complexityValue;
		String ruleType = "";
		String recommendation = "";
		int autoRemediation;
		int timeSavingsInMin;
		try {
			HSSFSheet worksheet = workBook.getSheetAt(0);
		    	   int lastRow=worksheet.getLastRowNum();
		    	   List<String> discrepancyList = new ArrayList<String>();
		   		for (Discrepancy discrepancy : descrepancyDetailsList)
		   		{
		    	   Row row = worksheet.createRow(++lastRow);
		    	   filePattern = (discrepancy.getPattern()== null) ? "": discrepancy.getPattern();
			       fileType = (discrepancy.getFileType()== null) ? "": discrepancy.getFileType();
			       ruleType = (discrepancy.getRuleType()== null) ? "": discrepancy.getRuleType();
			       recommendation = (discrepancy.getRecommendation()== null) ? "": discrepancy.getRecommendation();
			       complexityValue = discrepancy.getComplexity();
			       autoRemediation = discrepancy.getAutoRemediation();
			       category = (discrepancy.getCategory()== null) ? "" :discrepancy.getCategory();
			       timeSavingsInMin =  discrepancy.getTimeSavingsInMin();
			       if(0 != discrepancy.getLineNo()) {
			    	   lineNo = discrepancy.getLineNo();
			       }
		       row.createCell(0).setCellValue(fileType);
		       row.createCell(1).setCellValue(discrepancy.getFileName());
		       row.createCell(2).setCellValue(lineNo);
		       row.createCell(3).setCellValue(category);
		       row.createCell(4).setCellValue(ruleType);
		       row.createCell(5).setCellValue(filePattern);
		       row.createCell(6).setCellValue(recommendation);
		       row.createCell(7).setCellValue(complexityValue);
		       row.createCell(8).setCellValue(autoRemediation);
		       row.createCell(9).setCellValue(timeSavingsInMin);
		   		}
		       myxls.close();
		       FileOutputStream output_file =new FileOutputStream(new File(targetLocation + "\\" +discrepancyReportFileName));  
		       workBook.write(output_file);
		       output_file.close();
		       System.out.println("Created discrepency report 'discrepancy-list.xls'" );
		}catch (FileNotFoundException ex) {
			System.out.println("Failed to Create discrepency report");
			ex.printStackTrace();
		}
	}
        
public static void copyFileAtOutputLocation(String targetLocation) throws IOException {
	       String discrepancyReportFileName = "discrepancy-list.xls";
	       String resourcesFileLocation = "resources\\\\sample-report.xls";
	try {
		    Files.deleteIfExists(Paths.get(targetLocation+ "\\" +discrepancyReportFileName)); 
		    File dir = new File(targetLocation);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			File from = new File(resourcesFileLocation); 
			File to = new File(targetLocation + "\\" + discrepancyReportFileName);
			if(!to.exists()) {
			FileUtils.copyFile(from, to);
			}
	}catch (IOException ex) {
			// TODO Auto-generated catch block
		   ex.printStackTrace();

	}
}
}
	