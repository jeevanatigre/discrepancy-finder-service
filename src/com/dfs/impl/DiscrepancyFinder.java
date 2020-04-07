package com.dfs.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.dfs.model.Discrepancy;
import com.dfs.model.Rule;
import com.dfs.service.DiscrepancyFinderService;
import com.dfs.util.Constants;

public class DiscrepancyFinder implements DiscrepancyFinderService{

	public Map<String, List<Object>> findDiscrepancy(File file, String findOrRemediateMode, Rule rule,
			String sourceLocation, String targetLocation, String[] args, List<Object> codeLineList) throws IOException {
		Map<String, List<Object>> listDetailsMap = new HashMap<String, List<Object>>();
		List<Object> discrepancyDetailsList = new ArrayList<Object>();

		try {
			List<Object> removeDiscrepancyList = new ArrayList<Object>();

			String textPattern = rule.getText_pattern() == null ? null : rule.getText_pattern().getValue().trim();
			if (textPattern != null && codeLineList.contains(textPattern) && rule.getRemediation().getAction().trim()
					.equalsIgnoreCase(Constants.ACTION_ENUM.remove.toString())) {
				int discrepancyLineNumber = codeLineList.indexOf(rule.getText_pattern().getValue().trim()) + 1;
				discrepancyDetailsList.add(setDiscrepancyData(rule, discrepancyLineNumber, file, args));
				if (findOrRemediateMode.equalsIgnoreCase("1")) {
					removeDiscrepancyList.add(rule.getText_pattern().getValue().trim());
				}
			} else if (textPattern != null && rule.getRemediation().getAction().trim()
					.equalsIgnoreCase(Constants.ACTION_ENUM.replace.toString())) {
				String replaceCondition = rule.getRemediation().getCondition().toLowerCase().trim();
				List<Integer> deprecatedLineNumberList = new ArrayList<Integer>();
				for (int lineCounter = 0; lineCounter < codeLineList.size(); lineCounter++) {
					if (codeLineList.get(lineCounter).toString().contains(textPattern))
						deprecatedLineNumberList.add(lineCounter);
				}
				for (int lineNumber : deprecatedLineNumberList)
					discrepancyDetailsList.add(setDiscrepancyData(rule, lineNumber, file, args));
				if (findOrRemediateMode.equalsIgnoreCase("1")) {
					for (int lineNumber : deprecatedLineNumberList) {
						if (codeLineList.get(lineNumber).toString().toLowerCase().trim()
								.contains(replaceCondition.toLowerCase()))
							codeLineList.set(lineNumber, codeLineList.get(lineNumber).toString().replaceAll(textPattern,
									rule.getRemediation().getReplace_with().trim()));
					}
				}
			} else if (rule.getFile() != null && rule.getFile().size() > 0) {
				if (rule.getFile().stream().anyMatch(file.getName()::equalsIgnoreCase))
					discrepancyDetailsList.add(setDiscrepancyData(rule, 0, file, args));
			}
			listDetailsMap.put(Constants.REQUIRED_LISTS.codeLineList.toString(), codeLineList);
			listDetailsMap.put(Constants.REQUIRED_LISTS.removeDiscrepancyList.toString(), removeDiscrepancyList);
			listDetailsMap.put(Constants.REQUIRED_LISTS.discrepancyDetailsList.toString(), discrepancyDetailsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listDetailsMap;
	}

	public Discrepancy setDiscrepancyData(Rule rule, int discrepancyLineNumber, File file, String[] args) {
		Discrepancy discrepancy = new Discrepancy();
		try {
			discrepancy.setFileName(file.getName());
			discrepancy.setFileType(file.getName().substring(file.getName().lastIndexOf('.')));
			discrepancy.setLineNo(discrepancyLineNumber);
			discrepancy.setCategory(rule.getCategory() == null ? "" : rule.getCategory());
			discrepancy.setRuleType(rule.getType() == null ? "" : rule.getType());
			discrepancy.setPattern(rule.getText_pattern() == null ? "" : rule.getText_pattern().getValue().trim());
			discrepancy
					.setRecommendation(rule.getRemediation() == null ? "" : rule.getRemediation().getRecommendation());
			discrepancy.setComplexity(rule.getRemediation().getComplexity() == null ? 0
					: Integer.parseInt(rule.getRemediation().getComplexity()));
			discrepancy.setAutoRemediation(((rule.getRemediation().getSavings() == null)
					|| (rule.getRemediation().getSavings().equalsIgnoreCase("0")) ? 0 : 1));
			discrepancy.setTimeSavingsInMin(rule.getRemediation().getSavings() == null ? 0
					: Integer.parseInt(rule.getRemediation().getSavings()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return discrepancy;
	}

	public void writeDiscrepancyFile(List<Object> descrepancyDetailsList, String targetLocation) {
		// System.out.println("Creating txt report at given target location");
		File dir = new File(targetLocation);
		if (!dir.exists())
			dir.mkdirs();
		Path file = Paths.get(targetLocation + "\\" + "discrepancy-list.txt");
		List<String> discrepancyList = new ArrayList<String>();
		for (Object discrepancy : descrepancyDetailsList)
			discrepancyList.add(discrepancy.toString());
		try {
			Files.write(file, discrepancyList, StandardCharsets.UTF_8);
			// System.out.println("Created txt report at given target location
			// successfully");
		} catch (IOException e) {
			System.out.println("Failed creating discrepancy txt file at given target location");
			e.printStackTrace();
		}
	}

	public void writeRemidiatedFile(List<Object> codeLineList, File inputFile, String targetLocation) {
		File dir = new File(targetLocation);
		if (!dir.exists())
			dir.mkdirs();
		Path file = Paths.get(targetLocation + "\\remediated-" + inputFile.getName());
		try {
			if (!inputFile.getName().substring(inputFile.getName().lastIndexOf('.')).equalsIgnoreCase(".xml")) {
				System.out.println("Creating remediated file '" + inputFile.getName() + "'");
				Files.write(file, codeLineList.stream().map(object -> Objects.toString(object, null))
						.collect(Collectors.toList()), StandardCharsets.UTF_8);
				System.out.println("Created remediated file '" + inputFile.getName() + "'");
			}
		} catch (IOException e) {
			System.out.println("Failed creating remediated '" + inputFile.getName() + "'");
			e.printStackTrace();
		}
	}

	public Map<String, List<Object>> fileBatchOperation(String findOrRemediateMode, Rule rule,
			String sourceLocation, String targetLocation, String[] args) throws IOException {
		Map<String, List<Object>> listDetailsMap = new HashMap<String, List<Object>>();
		List<Object> discrepancyDetailsList = new ArrayList<Object>();
		try {
			for(String fileExtension: rule.getFile()) {
				DirectoryStream<Path> files = Files.newDirectoryStream(Paths.get(sourceLocation), path -> path.toString().endsWith(fileExtension.substring(fileExtension.lastIndexOf('.'))));
				for(Path file : files) {
					discrepancyDetailsList.add(setDiscrepancyData(rule, 0, file.toFile(), args));
				}
			}
			listDetailsMap.put(Constants.REQUIRED_LISTS.discrepancyDetailsList.toString(), discrepancyDetailsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listDetailsMap;
	}

}
