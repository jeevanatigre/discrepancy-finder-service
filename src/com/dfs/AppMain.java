package com.dfs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dfs.impl.DiscrepancyFinder;
import com.dfs.model.DiscrepancyRules;
import com.dfs.model.Rule;
import com.dfs.util.Constants;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class AppMain {
	
	private String discrapencyRuleFile;
	private String sourceLocation;
	private String targetLocation;
	private String findOrRemediateMode;

	public static void main(String[] args) throws IOException {

		if (null != args && 4 != args.length) {
			System.err.println("Invalid inputs, please provide inputs based on below usage example");
			System.err.println("###########################################################################################");
			System.err.println("#                                                                                         #");
			System.err.println("#   java AppMain <rule_file> <source_location> <target_location> <find_remediate_mode>    #");
			System.err.println("#   example : java AppMain discrapency-rules.xml C:/input C:/output 0                     #");	
			System.err.println("#                                                                                         #");
			System.err.println("###########################################################################################");
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
			List<String> result = null;
			List<Object> descrepancyDetailsList = new ArrayList<Object>();
			List<Object> codeLineList;
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
			Map<String, List<Object>> listDetailsMap = new HashMap<String, List<Object>>();
			List<Object> removeDiscrepancyList;
			if (ruleList.size() > 0) {
				try (Stream<Path> walk = Files.walk(Paths.get(sourceLocation))) {
					result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
					for (String fileName : result) {
						codeLineList = new ArrayList<Object>();
						removeDiscrepancyList = new ArrayList<Object>();
						File file = new File(fileName);
						scanner = new Scanner(new File(sourceLocation + "\\" + file.getName()));
						while (scanner.hasNextLine()) {
							codeLineList.add(scanner.nextLine().trim());
						}
						System.out.println("Processing file '" + file.getName() + "'");
						for (int ruleIndex = 0; ruleIndex < ruleList.size(); ruleIndex++) {
							listDetailsMap = DiscrepancyFinder.findDiscrepancy(file, findOrRemediateMode, ruleList.get(ruleIndex), sourceLocation, targetLocation, args, codeLineList);
							descrepancyDetailsList.addAll(listDetailsMap.get(Constants.REQUIRED_LISTS.discrepancyDetailsList.toString()));
							removeDiscrepancyList.addAll(listDetailsMap.get(Constants.REQUIRED_LISTS.removeDiscrepancyList.toString()));
							codeLineList = listDetailsMap.get(Constants.REQUIRED_LISTS.codeLineList.toString());
							for (Object discrepancy : removeDiscrepancyList)
								codeLineList.removeAll(Collections.singleton(discrepancy.toString()));
						}
						if (findOrRemediateMode.equalsIgnoreCase("1")) 
							DiscrepancyFinder.writeRemidiatedFile(codeLineList, file, targetLocation);
						System.out.println("Completed processing file '" + file.getName() + "'");
					}
					/*new ExcelReport().createReport(descrepancyDetailsList, targetLocation);*/
					DiscrepancyFinder.writeDiscrepancyFile(descrepancyDetailsList, targetLocation);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void dotNetDiscrepancyFinder() {

	}

	public static void tomcatDiscrepancyFinder() {

	}
}
