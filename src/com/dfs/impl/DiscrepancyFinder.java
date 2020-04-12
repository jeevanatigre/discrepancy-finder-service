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
import com.dfs.model.RemidiationPattern;
import com.dfs.model.Rule;
import com.dfs.model.TextPattern;
import com.dfs.service.DiscrepancyFinderService;
import com.dfs.util.Constants;

public class DiscrepancyFinder implements DiscrepancyFinderService {

	public Map<String, List<Object>> findDiscrepancy(File file, String findOrRemediateMode, Rule rule,
			String sourceLocation, String targetLocation, String[] args, List<Object> codeLineList) throws IOException {
		Map<String, List<Object>> listDetailsMap = new HashMap<String, List<Object>>();
		List<Object> discrepancyDetailsList = new ArrayList<Object>();

		try {
			List<Object> removeDiscrepancyList = new ArrayList<Object>();
			for (RemidiationPattern remidiationPattern : rule.getPattern_list()) {
				if (rule.getRemediation().getAction() != null && rule.getRemediation().getAction().trim()
						.equalsIgnoreCase(Constants.REMIDIATION_ENUM.remove.toString())) {
					ruleRemove(file, findOrRemediateMode, rule, args, codeLineList, discrepancyDetailsList,
							removeDiscrepancyList, remidiationPattern);
				}else if (rule.getRemediation().getAction() != null && rule.getRemediation().getAction().trim()
						.equalsIgnoreCase(Constants.REMIDIATION_ENUM.information.toString())) {
					ruleInfo(file, findOrRemediateMode, rule, args, codeLineList, discrepancyDetailsList,
							removeDiscrepancyList, remidiationPattern);
				}else if (rule.getRemediation().getAction() != null && rule.getRemediation().getAction().trim()
						.equalsIgnoreCase(Constants.REMIDIATION_ENUM.replace.toString())) {
					ruleReplace(file, findOrRemediateMode, rule, args, codeLineList, discrepancyDetailsList,
							remidiationPattern);
				}
			}
			listDetailsMap.put(Constants.REQUIRED_LISTS.codeLineList.toString(), codeLineList);
			listDetailsMap.put(Constants.REQUIRED_LISTS.removeDiscrepancyList.toString(), removeDiscrepancyList);
			listDetailsMap.put(Constants.REQUIRED_LISTS.discrepancyDetailsList.toString(), discrepancyDetailsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listDetailsMap;
	}

	private void ruleReplace(File file, String findOrRemediateMode, Rule rule, String[] args, List<Object> codeLineList,
			List<Object> discrepancyDetailsList, RemidiationPattern remidiationPattern) {
		for (TextPattern textPattern : remidiationPattern.getText_pattern()) {
			String textValue = textPattern.getValue() == null ? null : textPattern.getValue().trim();
			if (textPattern != null) {
				String replaceCondition = rule.getRemediation().getCondition().toLowerCase()
						.trim();
				List<Integer> deprecatedLineNumberList = new ArrayList<Integer>();
				for (int lineCounter = 0; lineCounter < codeLineList.size(); lineCounter++) {
					if (codeLineList.get(lineCounter).toString().contains(textValue))
						deprecatedLineNumberList.add(lineCounter);
				}
				for (int lineNumber : deprecatedLineNumberList)
					discrepancyDetailsList.add(setDiscrepancyData(rule, remidiationPattern, textPattern,
							lineNumber, file, args));
				if (findOrRemediateMode.equalsIgnoreCase("1")) {
					for (int lineNumber : deprecatedLineNumberList) {
						if (codeLineList.get(lineNumber).toString().toLowerCase().trim()
								.contains(replaceCondition.toLowerCase()))
							codeLineList.set(lineNumber,
									codeLineList.get(lineNumber).toString().replaceAll(textValue,
											rule.getRemediation().getReplace_with().trim()));
					}
				}
			}
		}
	}

	private void ruleInfo(File file, String findOrRemediateMode, Rule rule, String[] args, List<Object> codeLineList,
			List<Object> discrepancyDetailsList, List<Object> removeDiscrepancyList,
			RemidiationPattern remidiationPattern){
		for (TextPattern textPattern : remidiationPattern.getText_pattern()) {
			String textValue = textPattern.getValue() == null ? null : textPattern.getValue().trim();
			if (textValue != null){
				int discrepancyLineNumber = 0;
				for (int lineCounter = 0; lineCounter < codeLineList.size(); lineCounter++) {
					if (codeLineList.get(lineCounter).toString().contains(textValue)) {
						discrepancyLineNumber = lineCounter;
						discrepancyDetailsList.add(setDiscrepancyData(rule, remidiationPattern, textPattern,
								discrepancyLineNumber, file, args));
					}
				}
			}
		}
	}

	private void ruleRemove(File file, String findOrRemediateMode, Rule rule, String[] args, List<Object> codeLineList,
			List<Object> discrepancyDetailsList, List<Object> removeDiscrepancyList,
			RemidiationPattern remidiationPattern) {
		for (TextPattern textPattern : remidiationPattern.getText_pattern()) {
			String textValue = textPattern.getValue() == null ? null : textPattern.getValue().trim();
			if (textValue != null && codeLineList.contains(textValue)) {
				int discrepancyLineNumber = codeLineList.indexOf(textValue) + 1;
				discrepancyDetailsList.add(setDiscrepancyData(rule, remidiationPattern, textPattern,
						discrepancyLineNumber, file, args));
				if (findOrRemediateMode.equalsIgnoreCase("1")) {
					removeDiscrepancyList.add(textValue);
				}
			}
		}
	}

	public Discrepancy setDiscrepancyData(Rule rule, RemidiationPattern remidiationPattern, TextPattern textPattern,
			int discrepancyLineNumber, File file, String[] args) {
		Discrepancy discrepancy = new Discrepancy();
		try {
			addData(rule, remidiationPattern, textPattern, discrepancyLineNumber, file, discrepancy);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return discrepancy;
	}

	private void addData(Rule rule, RemidiationPattern remidiationPattern, TextPattern textPattern,
			int discrepancyLineNumber, File file, Discrepancy discrepancy) {
		discrepancy.setFileName(file.getName());
		discrepancy.setFileType(file.getName().substring(file.getName().lastIndexOf('.')));
		discrepancy.setLineNo(discrepancyLineNumber);
		discrepancy.setCategory(rule.getCategory() == null ? "" : rule.getCategory());
		discrepancy.setRuleType(rule.getType() == null ? "" : rule.getType());
		if (rule.getType().equalsIgnoreCase(Constants.FILE_OPERATION.text_finder.toString()))
			discrepancy.setPattern(textPattern.getValue() == null ? "" : textPattern.getValue().trim());
		discrepancy.setRecommendation(rule.getRemediation() == null ? ""
				: rule.getRemediation().getRecommendation());
		discrepancy.setAction(rule.getRemediation().getAction());
		discrepancy.setComplexity(rule.getRemediation().getComplexity() == null ? 0
				: Integer.parseInt(rule.getRemediation().getComplexity()));
		discrepancy.setAutoRemediation(((rule.getRemediation().getSavings() == null)
				|| (rule.getRemediation().getSavings().equalsIgnoreCase("0")) ? 0 : 1));
		discrepancy.setTimeSavingsInMin(rule.getRemediation().getSavings() == null ? 0
				: Integer.parseInt(rule.getRemediation().getSavings()));
	}

	public void writeDiscrepancyFile(List<Object> descrepancyDetailsList, String targetLocation) {
		File dir = new File(targetLocation);
		if (!dir.exists())
			dir.mkdirs();
		Path file = Paths.get(targetLocation + "\\" + "discrepancy-list.txt");
		List<String> discrepancyList = new ArrayList<String>();
		for (Object discrepancy : descrepancyDetailsList)
			discrepancyList.add(discrepancy.toString());
		try {
			Files.write(file, discrepancyList, StandardCharsets.UTF_8);
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
			System.out.println("Creating remediated file '" + inputFile.getName() + "'");
			Files.write(file, codeLineList.stream().map(object -> Objects.toString(object, null))
					.collect(Collectors.toList()), StandardCharsets.UTF_8);
			System.out.println("Created remediated file '" + inputFile.getName() + "'");
		} catch (IOException e) {
			System.out.println("Failed creating remediated '" + inputFile.getName() + "'");
			e.printStackTrace();
		}
	}

	public Map<String, List<Object>> fileBatchOperation(String findOrRemediateMode, Rule rule, String sourceLocation,
			String targetLocation, String[] args) throws IOException {
		Map<String, List<Object>> listDetailsMap = new HashMap<String, List<Object>>();
		List<Object> discrepancyDetailsList = new ArrayList<Object>();
		try {
			for (RemidiationPattern remidiationPattern : rule.getPattern_list()) {
				for (String fileExtension : remidiationPattern.getFile()) {
					if(fileExtension.contains("*.")) {
						DirectoryStream<Path> files = Files.newDirectoryStream(Paths.get(sourceLocation),
								path -> path.toString().endsWith(fileExtension.substring(fileExtension.lastIndexOf('.'))));
						for (Path file : files) {
							discrepancyDetailsList
									.add(setDiscrepancyData(rule, remidiationPattern, null, 0, file.toFile(), args));
						}
					} else {
						DirectoryStream<Path> files = Files.newDirectoryStream(Paths.get(sourceLocation),
								path -> path.toString().toLowerCase().contains(fileExtension.toLowerCase()));
						for (Path file : files) {
							discrepancyDetailsList
									.add(setDiscrepancyData(rule, remidiationPattern, null, 0, file.toFile(), args));
						}
					}
				}
			}
			listDetailsMap.put(Constants.REQUIRED_LISTS.discrepancyDetailsList.toString(), discrepancyDetailsList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return listDetailsMap;
	}

}
