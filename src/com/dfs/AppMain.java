package com.dfs;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.dfs.util.Constants;

public class AppMain {

	public static void main(String[] args) throws IOException {
		String userInput;
		Scanner sn = new Scanner(System.in);

		while (true) {
			System.out.println("*****Discrepancy finder and remediator*****");
			System.out.println("Press 1 for Java: Find");
			System.out.println("Press 2 for Java: Find and remidiate");
			System.out.println("Press 3 for .Net: Find");
			System.out.println("Press 4 for .Net: Find and remidiate");
			System.out.println("Press 5 for Tomcat: Find");
			System.out.println("Press 6 for Tomcat: Find and remidiate");
			System.out.println("Press 7 to exit");
			System.out.println("Enter your choice:");

			userInput = sn.next();
			File javaRuleXml;
			switch (userInput) {
			case "1":
				javaRuleXml = readJavaRuleXml();
				discrepancyFinder(userInput, javaRuleXml);
				break;
			case "2":
				javaRuleXml = readJavaRuleXml();
				discrepancyFinder(userInput, javaRuleXml);
				break;
			case "3":
				dotNetDiscrepancyFinder();
				break;
			case "4":
				break;
			case "5":
				tomcatDiscrepancyFinder();
				break;
			case "6":
				break;
			case "7":
				System.out.println("Exited");
				sn.close();
				System.exit(0);
			default:
				System.out.println("Invalid choice. Read the options carefully...");
			}
		}
	}
	
	public static File readJavaRuleXml() {
		File jaavaRulrXml = new File("resources\\java-rules.xml");
		return jaavaRulrXml;
	}

	public static void discrepancyFinder(String userInput, File javaRulrXml) {
		try {
			List<String> result = null;
			try (Stream<Path> walk = Files.walk(Paths.get("input-files"))) {
					result = walk.filter(Files::isRegularFile).map(x -> x.toString()).collect(Collectors.toList());
					for (String fileName : result) {
						File file = new File("input-files\\" + fileName);
						String fileExt = file.getName().substring(file.getName().lastIndexOf('.'));
						if(userInput.equalsIgnoreCase("1") && fileExt.equalsIgnoreCase(Constants.XML_FILE_PATTERN)){
							DiscrepancyFinder.copyFileToApplicationServer(file.getName());
						}else if(fileExt.equalsIgnoreCase(".java")){
							DiscrepancyFinder.findDiscrepancy(file, userInput, javaRulrXml);
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
