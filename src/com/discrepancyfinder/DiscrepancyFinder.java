package com.discrepancyfinder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.xmlobjectmapper.DiscrepancyRules;
import com.xmlobjectmapper.Import;

public class DiscrepancyFinder {

	public static void findDiscrepancy(File javaFile, String userInput, File javaRulrXml) {
		List<String> javaCodeLineList = new ArrayList<String>();
		List<String> discrepancyLineList = new ArrayList<String>();

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
			DiscrepancyRules rules = xmlMapper.readValue(xml.toString(), DiscrepancyRules.class);
	        List<Import> imports = rules.getRule().getImports().getImport_tag();
	        
			if (imports.size() > 0) {
				for (int temp = 0; temp < imports.size(); temp++) {
					if (javaCodeLineList.contains(imports.get(temp).getName().trim()) && userInput.equalsIgnoreCase("1"))
						discrepancyLineList.add(imports.get(temp).getName().trim() + "  Line number: "+ javaCodeLineList.indexOf(imports.get(temp).getName().trim()) + 1);
					else if (javaCodeLineList.contains(imports.get(temp).getName().trim()) && userInput.equalsIgnoreCase("2")) {
						discrepancyLineList.add(imports.get(temp).getName().trim() + "  Line number: "+ javaCodeLineList.indexOf(imports.get(temp).getName().trim()) + 1);
						javaCodeLineList.remove(imports.get(temp).getName().trim());
					}
				}
				
				if (userInput.equalsIgnoreCase("1")) {
					writeDiscrepancyFile(discrepancyLineList, javaFile);
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
		Path file = Paths.get(directory + "\\discrepancies-" + javaFile.getName() + ".txt");
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
		Path file = Paths.get(directory + "\\remediated-" + javaFile.getName() + ".java");
		try {
			Files.write(file, javaCodeLineList, StandardCharsets.UTF_8);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
