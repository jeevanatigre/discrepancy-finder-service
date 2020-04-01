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
import com.dfs.util.Constants;

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
		
		new AppMainNew().startWork(args);
		File ruleXml = readJavaRuleXml(args);
		discrepancyFinder(args, ruleXml);
		
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
		System.out.println("Continue work");
		

	}
	
	public static File readJavaRuleXml(String[] args) {
		File jaavaRulrXml = new File(args[0]);
		return jaavaRulrXml;
	}

	public static void discrepancyFinder(String[] args, File javaRulrXml) {
		try {
			List<String> result = null;
			List<Discrepancy> descrepancyDetailsList = new ArrayList<Discrepancy>();
			try (Stream<Path> walk = Files.walk(Paths.get(args[1]))) {
					result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
					for (String fileName : result) {
						File file = new File(args[3]+"\\" + fileName);
						String fileExt = file.getName().substring(file.getName().lastIndexOf('.'));
						if(args[3].equalsIgnoreCase("1") && fileExt.equalsIgnoreCase(Constants.XML_FILE_PATTERN)){
							DiscrepancyFinder.copyFileToApplicationServer(file.getName(), args);
						}else if(fileExt.equalsIgnoreCase(".java")){
							descrepancyDetailsList.addAll(DiscrepancyFinder.findDiscrepancy(file, args, javaRulrXml));
						}
					}
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
