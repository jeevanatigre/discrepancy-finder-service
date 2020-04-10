package com.dfs.model;


@SuppressWarnings("all")
public class Discrepancy {
	
	private String fileType;
	
	private String fileName;
	
	private int lineNo;
	
	private String category;
	
	private String ruleType;
	
	private String pattern;
	
	private String recommendation;
	
	private String action;
	
	private int complexity;
	
	private int autoRemediation;
	
	private int timeSavingsInMin;

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public int getLineNo() {
		return lineNo;
	}

	public void setLineNo(int lineNo) {
		this.lineNo = lineNo;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRuleType() {
		return ruleType;
	}

	public void setRuleType(String ruleType) {
		this.ruleType = ruleType;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public int getComplexity() {
		return complexity;
	}

	public void setComplexity(int complexity) {
		this.complexity = complexity;
	}

	public int getAutoRemediation() {
		return autoRemediation;
	}

	public void setAutoRemediation(int autoRemediation) {
		this.autoRemediation = autoRemediation;
	}

	public int getTimeSavingsInMin() {
		return timeSavingsInMin;
	}

	public void setTimeSavingsInMin(int timeSavingsInMin) {
		this.timeSavingsInMin = timeSavingsInMin;
	}

	@Override
	public String toString() {
		return fileType + ", " + fileName + ", " + lineNo + ", "
				+ category + ", " + ruleType + ", " + pattern + ", " + recommendation
				+ ", " + complexity + ", " + autoRemediation + ", "
				+ timeSavingsInMin;
	}
}