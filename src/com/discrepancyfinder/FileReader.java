package com.discrepancyfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xmlobjectmapper.DiscrepancyRules;
import com.xmlobjectmapper.Import;

public class FileReader {

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
			switch (userInput) {
			case "1":
				javaDiscrepancyFinder(userInput);
				generateJavaObjects();
				break;
			case "2":
				javaDiscrepancyFinder(userInput);
				generateJavaObjects();
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
				System.out.println("Exiting...");
				sn.close();
				System.exit(0);
			default:
				System.out.println("Invalid choice. Read the options carefully...");
			}
		}
	}

	public static void javaDiscrepancyFinder(String userInput) {
		try {
			List<String> result = null;
			try (Stream<Path> walk = Files.walk(Paths.get("input-files"))) {
				result = walk.map(x -> x.toString()).filter(f -> f.endsWith(".java")).collect(Collectors.toList());
				for (String fileName : result) {
					File javaFile = new File("input-files\\" + fileName);
					DiscrepancyFinder.findDiscrepancy(javaFile, userInput);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void generateJavaObjects() throws IOException {
		List<String> xmlCodeLineList = new ArrayList<String>();
		Scanner scanner;
		try {
			scanner = new Scanner(new File("resources\\java-rules.xml"));
			while (scanner.hasNextLine()) {
				xmlCodeLineList.add(scanner.nextLine().trim());
			}
			scanner.close();
			XmlMapper xmlMapper = new XmlMapper();
			StringBuilder xml = new StringBuilder();
			xmlCodeLineList.forEach(xml::append);
			DiscrepancyRules rules = xmlMapper.readValue(xml.toString(), DiscrepancyRules.class);
	        List<Import> imports = rules.getRule().getImports().getImport_tag();
	        for(Import importItem: imports){
	        	System.out.println(importItem);
	        }
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void dotNetDiscrepancyFinder() {

	}

	public static void tomcatDiscrepancyFinder() {

	}

}
