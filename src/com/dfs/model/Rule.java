package com.dfs.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.util.List;

@SuppressWarnings("all")
public class Rule {

    @JacksonXmlProperty(localName = "type", isAttribute = true)
    private String type;

    @JacksonXmlProperty(localName = "category", isAttribute = true)
    private String category;

    @JacksonXmlElementWrapper(localName = "complexity", useWrapping = false)
    private Complexity complexity;

    @JacksonXmlElementWrapper(localName = "file_pattern", useWrapping = false)
    private FilePattern file_pattern;

    @JacksonXmlElementWrapper(localName = "files")
    private List<String> file;

    @JacksonXmlElementWrapper(localName = "text_pattern", useWrapping = false)
    private TextPattern text_pattern;

    @JacksonXmlElementWrapper(localName = "remediation", useWrapping = false)
    private Remediation remediation;
    
    @JacksonXmlElementWrapper(localName = "file_operation_type", useWrapping = false)
    private FileOperationType file_operation_type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Complexity getComplexity() {
        return complexity;
    }

    public void setComplexity(Complexity complexity) {
        this.complexity = complexity;
    }

    public FilePattern getFile_pattern() {
        return file_pattern;
    }

    public void setFile_pattern(FilePattern file_pattern) {
        this.file_pattern = file_pattern;
    }

    public List<String> getFile() {
		return file;
	}

	public void setFile(List<String> file) {
		this.file = file;
	}

	public TextPattern getText_pattern() {
        return text_pattern;
    }

    public void setText_pattern(TextPattern text_pattern) {
        this.text_pattern = text_pattern;
    }

    public Remediation getRemediation() {
        return remediation;
    }

    public void setRemediation(Remediation remediation) {
        this.remediation = remediation;
    }

	public FileOperationType getFile_operation_type() {
		return file_operation_type;
	}

	public void setFile_operation_type(FileOperationType file_operation_type) {
		this.file_operation_type = file_operation_type;
	}

	public Rule() {
    }

    public Rule(String type, String  category) {
        this.type = type;
        this.category = category;
    }

    @Override public String toString() {
        return "Rule{" + "complexity='" + complexity + '\'' + "file_pattern='" + file_pattern + '\'' +
                + '\'' + "file='" + file + '\'' + "file_operation_type=" + file_operation_type +
                "text_pattern='" + text_pattern + '\'' + "remediation='" + remediation + '\'' + '}';
    }
}