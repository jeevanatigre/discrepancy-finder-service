package com.dfs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.dfs.impl.DiscrepancyFinder;
import com.dfs.model.Discrepancy;
import com.dfs.model.DiscrepancyRules;
import com.dfs.model.Rule;
import com.dfs.report.ExcelReport;
import com.dfs.util.Constants;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class AppMain {
	
	private String discrapencyRuleFile;
	private String sourceLocation;
	private String targetLocation;
	private String findOrRemediateMode;

	public static void main(String[] args) throws IOException {

		if (null != args && 4 != args.length) {
			System.err.println("Please provide inputs based on below usage example");
			System.err.println("######################################################################################");
			System.err.println("#                                                                                    #");
			System.err.println("#   run.bat <rule_file> <source_location> <target_location> <find_remediate_mode>    #");
			System.err.println("#   example : run.bat discrapency-rules.xml C:/input C:/output 0                     #");	
			System.err.println("#                                                                                    #");
			System.err.println("######################################################################################");
			System.exit(0);
		}

		System.out.println("##################### Discrapency Finder Service Started #####################");
		AppMain appmain = new AppMain();
		appmain.startWork(args);
		File ruleXml = appmain.readJavaRuleXml();
		appmain.discrepancyFinder(ruleXml, args);
		System.out.println("##################### Discrapency Finder Service Stopped #####################");
		
	}
	
	public void startWork(String args[]) {
		discrapencyRuleFile = args [0];
		sourceLocation = args [1];
		targetLocation = args [2];
		findOrRemediateMode = args [3];
		System.out.println("Discrapency Rule File = '" + discrapencyRuleFile + "'");
		System.out.println("Source Location = '" + sourceLocation + "'");
		System.out.println("Target Location = '" + targetLocation + "'");
		System.out.println("Mode - ( 0 for Find & 1 for Find + Remediate) = '" + findOrRemediateMode + "'");
		

	}
	
	public File readJavaRuleXml() {
		File jaavaRulrXml = new File(discrapencyRuleFile);
		return jaavaRulrXml;
	}

	public void discrepancyFinder(File javaRulrXml, String[] args) {
		try {
			List<Object> descrepancyDetailsList = new ArrayList<Object>();
			List<String> xmlCodeLineList = new ArrayList<String>();
			Scanner scanner;
			scanner = new Scanner(javaRulrXml);
			while (scanner.hasNextLine()) {
				xmlCodeLineList.add(scanner.nextLine().trim());
			}
			scanner.close();
			XmlMapper xmlMapper = new XmlMapper();
			StringBuilder xml = new StringBuilder();
			xmlCodeLineList.forEach(xml::append);
			DiscrepancyRules discrepancyRules = xmlMapper.readValue(xml.toString(), DiscrepancyRules.class);
			List<Rule> ruleList = discrepancyRules.getRule();
			if (ruleList.size() > 0) {
				readInputFile(args, descrepancyDetailsList, ruleList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void readInputFile(String[] args, List<Object> descrepancyDetailsList, List<Rule> ruleList)
			throws InvalidFormatException {
		Map<String, List<Object>> listDetailsMap = new HashMap<String, List<Object>>();
		boolean writeRemediatedFile = false;
		List<Object> removeDiscrepancyList;
		List<String> result;
		List<Object> codeLineList;
		Scanner scanner;
		try (Stream<Path> walk = Files.walk(Paths.get(sourceLocation))) {
			result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
			for (String fileName : result) {
				codeLineList = new ArrayList<Object>();
				removeDiscrepancyList = new ArrayList<Object>();
				writeRemediatedFile = false;
				File file = new File(fileName);
				scanner = new Scanner(new File(sourceLocation + "\\" + file.getName()));
				while (scanner.hasNextLine()) {
					codeLineList.add(scanner.nextLine().trim());
				}
				scanner.close();
				System.out.println("Processing file '" + file.getName() + "'");
				for (int ruleIndex = 0; ruleIndex < ruleList.size(); ruleIndex++) {
					if(ruleList.get(ruleIndex).getType().equalsIgnoreCase(Constants.FILE_OPERATION.text_finder.toString())) {
						listDetailsMap = new DiscrepancyFinder().findDiscrepancy(file, findOrRemediateMode, ruleList.get(ruleIndex), sourceLocation, targetLocation, args, codeLineList);
						descrepancyDetailsList.addAll(listDetailsMap.get(Constants.REQUIRED_LISTS.discrepancyDetailsList.toString()));
						removeDiscrepancyList.addAll(listDetailsMap.get(Constants.REQUIRED_LISTS.removeDiscrepancyList.toString()));
						codeLineList = listDetailsMap.get(Constants.REQUIRED_LISTS.codeLineList.toString());
						
						Iterator<Object> iterator = listDetailsMap.get(Constants.REQUIRED_LISTS.discrepancyDetailsList.toString()).iterator();
						while (iterator.hasNext()) {
							Object discrepancy = iterator.next();
							if (!((Discrepancy) discrepancy).getAction()
									.equalsIgnoreCase(Constants.REMIDIATION_ENUM.information.toString()))
								writeRemediatedFile = true;
						}
						for (Object discrepancy : removeDiscrepancyList)
							codeLineList.removeAll(Collections.singleton(discrepancy.toString()));
					}
				}
				if (findOrRemediateMode.equalsIgnoreCase("1") && writeRemediatedFile) 
					new DiscrepancyFinder().writeRemidiatedFile(codeLineList, file, targetLocation);
				System.out.println("Completed processing file '" + file.getName() + "'");
			}
			for (int ruleIndex = 0; ruleIndex < ruleList.size(); ruleIndex++) {
				if (ruleList.get(ruleIndex).getType().equalsIgnoreCase(Constants.FILE_OPERATION.file_finder.toString())) {
					listDetailsMap = new DiscrepancyFinder().fileBatchOperation(findOrRemediateMode, ruleList.get(ruleIndex), sourceLocation, targetLocation, args);
					descrepancyDetailsList.addAll(listDetailsMap.get(Constants.REQUIRED_LISTS.discrepancyDetailsList.toString()));
				}
			}
			new ExcelReport().createReport(descrepancyDetailsList, targetLocation);
			new DiscrepancyFinder().writeDiscrepancyFile(descrepancyDetailsList, targetLocation);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void dotNetDiscrepancyFinder() {

	}

	public static void tomcatDiscrepancyFinder() {

	}
}
