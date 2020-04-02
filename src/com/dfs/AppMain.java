package com.dfs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dfs.impl.DiscrepancyFinder;
import com.dfs.model.Discrepancy;
import com.dfs.report.ExcelReport;

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
			List<Discrepancy> descrepancyDetailsList = new ArrayList<Discrepancy>();
			try (Stream<Path> walk = Files.walk(Paths.get(sourceLocation))) {
					result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
					for (String fileName : result) {
						File file = new File(fileName);
						System.out.println("Processing file: " + file.getName());
						descrepancyDetailsList.addAll(DiscrepancyFinder.findDiscrepancy(file, findOrRemediateMode, javaRulrXml, sourceLocation, targetLocation, args));
						System.out.println("Completed processing file: " + file.getName());
					}
					new ExcelReport().createReport(descrepancyDetailsList, targetLocation);
					DiscrepancyFinder.writeDiscrepancyFile( descrepancyDetailsList, targetLocation);
			} catch (IOException e) {
				e.printStackTrace();
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
