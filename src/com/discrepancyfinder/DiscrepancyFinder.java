package com.discrepancyfinder;

import java.io.File;
import java.io.FileNotFoundException;
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

public class DiscrepancyFinder {

	public static void findDiscrepancy(File javaFile, String userInput) {
		File fXmlFile = new File("E:\\java-rules.xml");
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		List<String> codeLineList = new ArrayList<String>();
		List<String> discrepancyLineList = new ArrayList<String>();

		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();

			if (doc.getElementsByTagName("rule").getLength() > 0) {
				NodeList nList = doc.getElementsByTagName("imports");
				try {
					Scanner scanner = new Scanner(new File("E:\\java-files-input\\" + javaFile.getName()));
					while (scanner.hasNextLine()) {
						codeLineList.add(scanner.nextLine().trim());
					}
					scanner.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				for (int temp = 0; temp < nList.getLength(); temp++) {
					Node nNode = nList.item(temp);
					System.out.println("\nCurrent Element :" + nNode.getNodeName());
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
						Element eElement = (Element) nNode;
						NodeList missingImportList = eElement.getElementsByTagName("import_tag");
						for(int i = 0; i < missingImportList.getLength(); i++) {
							Node missingImportNode = missingImportList.item(i);
							String importName = ((Element) missingImportNode).getAttribute("name").trim();
							if (codeLineList.contains(importName) && userInput.equalsIgnoreCase("1")) 
								discrepancyLineList.add(importName + "  Line number: "+codeLineList.indexOf(importName));
							else if (codeLineList.contains(importName) && userInput.equalsIgnoreCase("2")) {
								discrepancyLineList.add(importName + "  Line number: "+codeLineList.indexOf(importName));
								codeLineList.remove(importName);
							}						
						}
					}
				}
				if (userInput.equalsIgnoreCase("1")) {
					String directory = "E:\\java-files-output";
					File dir = new File(directory);
				    if (!dir.exists()) dir.mkdirs();
					Path file = Paths.get(directory + "\\info-" + javaFile.getName() + ".txt");
					Files.write(file, discrepancyLineList, StandardCharsets.UTF_8);
				} else if (userInput.equalsIgnoreCase("2")) {
					String directory = "E:\\java-files-output-remidiator";
					File dir = new File(directory);
					if (!dir.exists()) dir.mkdirs();
					Path file = Paths.get(directory + "\\remediated-" + javaFile.getName() + ".java");
					Files.write(file, codeLineList, StandardCharsets.UTF_8);

					file = Paths.get(directory + "\\info-" + javaFile.getName() + ".txt");
					Files.write(file, discrepancyLineList, StandardCharsets.UTF_8);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
