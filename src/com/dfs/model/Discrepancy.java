package com.dfs.model;


@SuppressWarnings("all")
public class Discrepancy {
	
	private String fileType;
	
	private String fileName;
	
	private Integer lineNo;
	
	private String category;
	
	private String ruleType;
	
	private String pattern;
	
	private String recommendation;
	
	private String complexity;
	
	private String autoRemediation;
	
	private String timeSavingsInMin;

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

	public Integer getLineNo() {
		return lineNo;
	}

	public void setLineNo(Integer lineNo) {
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

	public String getComplexity() {
		return complexity;
	}

	public void setComplexity(String complexity) {
		this.complexity = complexity;
	}

	public String getAutoRemediation() {
		return autoRemediation;
	}

	public void setAutoRemediation(String autoRemediation) {
		this.autoRemediation = autoRemediation;
	}

	public String getTimeSavingsInMin() {
		return timeSavingsInMin;
	}

	public void setTimeSavingsInMin(String timeSavingsInMin) {
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