package com.dfs;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.dfs.rule.DiscrepancyRules;
import com.dfs.rule.Rule;
import com.dfs.util.Constants;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

public class DiscrepancyFinder {

	public static void findDiscrepancy(File javaFile, String userInput, File javaRulrXml) throws IOException {
		List<String> discrepancyLineList = new ArrayList<String>();
		List<String> javaCodeLineList = new ArrayList<String>();

		try {
			List<String> xmlCodeLineList = new ArrayList<String>();
			Scanner scanner;
			scanner = new Scanner(javaRulrXml);
			while (scanner.hasNextLine()) {
				xmlCodeLineList.add(scanner.nextLine().trim());
			}
			scanner = new Scanner(new File("input-files\\" + javaFile.getName()));
			while (scanner.hasNextLine()) {
				javaCodeLineList.add(scanner.nextLine().trim());
			}
			scanner.close();
			XmlMapper xmlMapper = new XmlMapper();
			StringBuilder xml = new StringBuilder();
			xmlCodeLineList.forEach(xml::append);
			DiscrepancyRules discrepancyRules = xmlMapper.readValue(xml.toString(), DiscrepancyRules.class);
			List<Rule> ruleList = discrepancyRules.getRule();
	        
	        if (ruleList.size() > 0) {
				for (int temp = 0; temp < ruleList.size(); temp++) {
					String textPattern = ruleList.get(temp).getText_pattern() == null ? null : ruleList.get(temp).getText_pattern().getValue().trim();
					if(textPattern != null && ruleList.get(temp).getFile_pattern().getValue().equalsIgnoreCase(Constants.JAVA_FILE_PATTERN) 
							&& javaCodeLineList.contains(textPattern) && ruleList.get(temp).getRemediation().getAction().trim().equalsIgnoreCase(Constants.ACTION_ENUM.remove.toString())) {
						int elementIndex = javaCodeLineList.indexOf(ruleList.get(temp).getText_pattern().getValue().trim());
						if(userInput.equalsIgnoreCase("1")) {
							/*discrepancyLineList.add(ruleList.get(temp).getText_pattern().getValue().trim() + "  Line number: "+ (elementIndex + 1));*/
						} else if (userInput.equalsIgnoreCase("2")) {
							discrepancyLineList.add(ruleList.get(temp).getText_pattern().getValue().trim() + "  Line number: "+ (elementIndex + 1));
							javaCodeLineList.remove(ruleList.get(temp).getText_pattern().getValue().trim());
						}
					} else if(textPattern != null && ruleList.get(temp).getFile_pattern().getValue().equalsIgnoreCase(Constants.JAVA_FILE_PATTERN) 
							&& ruleList.get(temp).getRemediation().getAction().trim().equalsIgnoreCase(Constants.ACTION_ENUM.replace.toString())) {
						String replaceCondition = ruleList.get(temp).getRemediation().getCondition().toLowerCase().trim();
						List<Integer> deprecatedLineNumberList = new ArrayList<Integer>();
					    for(int lineCounter=0; lineCounter < javaCodeLineList.size(); lineCounter++){
					    	if (javaCodeLineList.get(lineCounter).contains(textPattern)) 
				            	deprecatedLineNumberList.add(lineCounter);
					    }
						if(userInput.equalsIgnoreCase("1")) {
							/*for(int lineNumber: deprecatedLineNumberList) {
								discrepancyLineList.add(ruleList.get(temp).getText_pattern().getValue().trim() + "  Line number: "+ (lineNumber + 1));
							}*/
						} else if (userInput.equalsIgnoreCase("2")) {
							for(int lineNumber: deprecatedLineNumberList) {
								if(javaCodeLineList.get(lineNumber).toLowerCase().trim().contains(replaceCondition.toLowerCase())) {
									discrepancyLineList.add(ruleList.get(temp).getText_pattern().getValue().trim() + "  Line number: "+ (lineNumber + 1));
									javaCodeLineList.set(lineNumber, javaCodeLineList.get(lineNumber).replaceAll(textPattern, ruleList.get(temp).getRemediation().getReplace_with().trim()));
								}
							}
						}
					}
				}
				if (userInput.equalsIgnoreCase("1")) {
					/*writeDiscrepancyFile(discrepancyLineList, javaFile);*/
				} else if (userInput.equalsIgnoreCase("2")) {
					writeDiscrepancyFile(discrepancyLineList, javaFile);
					writeRemidiatedFile(discrepancyLineList, javaCodeLineList, javaFile);
				}
	        }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void writeDiscrepancyFile(List<String> discrepancyLineList, File javaFile) {
		String directory = "discrepancy-output-files";
		File dir = new File(directory);
	    if (!dir.exists()) dir.mkdirs();
		Path file = Paths.get(directory + "\\" + "discrepancy-list.txt");
		try {
			Files.write(file, discrepancyLineList, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeRemidiatedFile(List<String> discrepancyLineList, List<String> javaCodeLineList, File javaFile) {
		String directory = "remidiated-output-files";
		File dir = new File(directory);
		if (!dir.exists()) dir.mkdirs();
		Path file = Paths.get(directory + "\\" + javaFile.getName());
		try {
			Files.write(file, javaCodeLineList, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
