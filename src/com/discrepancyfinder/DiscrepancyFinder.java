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

	public static void findDiscrepancy(File javaFile) {
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
				System.out.println("----------------------------");
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
						NodeList missingImportList = eElement.getElementsByTagName("import");
						for(int i = 0; i < missingImportList.getLength(); i++) {
							Node missingImportNode = missingImportList.item(i);
							if (codeLineList.contains(((Element) missingImportNode).getAttribute("name").trim()))
								discrepancyLineList.add(((Element) missingImportNode).getAttribute("name") + "  Line number: "+codeLineList.indexOf(((Element) missingImportNode).getAttribute("name").trim()));
						}
					}
					Path file = Paths.get("E:\\java-files-output\\info-" + javaFile.getName() + ".txt");
					Files.write(file, discrepancyLineList, StandardCharsets.UTF_8);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
