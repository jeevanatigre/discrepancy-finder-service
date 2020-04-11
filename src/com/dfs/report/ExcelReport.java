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
	
	public void createReport(List<Object> descrepancyDetailsList , String targetLocation) throws InvalidFormatException, IOException {
		System.out.println("Creating discrepency report 'discrepancy-list.xls'");
		String discrepancyReportFileName = "discrepancy-list.xls";
		copyFile(targetLocation);
		FileInputStream inputStream;
		FileOutputStream output_file;
		FileInputStream myxls = new FileInputStream(targetLocation + "\\" +discrepancyReportFileName);
	    HSSFWorkbook workBook = new HSSFWorkbook(myxls);
	    int  lineNo=0;
	    String category = "";
	    String filePattern = "";
	    String fileType = "";
		int complexityValue=0;
		String ruleType = "";
		String recommendation = "";
		int autoRemediation=0;
		int timeSavingsInMin=0;
		try {
			addData(descrepancyDetailsList, workBook);
			       output_file =new FileOutputStream(new File(targetLocation + "\\" +discrepancyReportFileName));  
			       workBook.write(output_file);
			       output_file.close();
		    	   myxls.close();
		       System.out.println("Created discrepency report 'discrepancy-list.xls'" );
		}catch (FileNotFoundException ex) {
			System.out.println("Failed to Create discrepency report");
			ex.printStackTrace();
		}
	}

	private void addData(List<Object> descrepancyDetailsList, HSSFWorkbook workBook) {
		HSSFSheet worksheet = workBook.getSheetAt(0);
			   int lastRow=worksheet.getLastRowNum();
			   Row row = worksheet.createRow(++lastRow);
			   Iterator<Object> iterator = descrepancyDetailsList.iterator();
			   while (iterator.hasNext()) {
				   Object discrepancy = iterator.next();
			       row.createCell(0).setCellValue(((Discrepancy) discrepancy).getFileType());
			       row.createCell(1).setCellValue(((Discrepancy) discrepancy).getFileName());
			       row.createCell(2).setCellValue(((Discrepancy) discrepancy).getLineNo());
			       row.createCell(3).setCellValue(((Discrepancy) discrepancy).getCategory());
			       row.createCell(4).setCellValue(((Discrepancy) discrepancy).getRuleType());
			       row.createCell(5).setCellValue(((Discrepancy) discrepancy).getPattern());
			       row.createCell(6).setCellValue(((Discrepancy) discrepancy).getRecommendation());
			       row.createCell(7).setCellValue(((Discrepancy) discrepancy).getComplexity());
			       row.createCell(8).setCellValue(((Discrepancy) discrepancy).getAutoRemediation());
			       row.createCell(9).setCellValue(((Discrepancy) discrepancy).getTimeSavingsInMin());
			       row = worksheet.createRow(++lastRow);
			   }
	}
        
public void copyFile(String targetLocation) throws IOException {
	       String discrepancyReportFileName = "discrepancy-list.xls";
	       String resourcesFileLocation = "config\\\\sample-report.xls";
	       // String resourcesFileLocation = this.getClass().getClassLoader().getResource("sample-report.xls").getFile();
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
		   System.out.println("Failed creating discrepancy excel file at given target location");
		   ex.printStackTrace();

	}
}
}
	