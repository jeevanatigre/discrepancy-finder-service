package com.dfs.model;

import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class RemidiationPattern {
	
	@JacksonXmlElementWrapper(localName = "text_pattern", useWrapping = false)
    private List<TextPattern> text_pattern;
	
    @JacksonXmlElementWrapper(localName = "files")
    private List<String> file;
	
    @JacksonXmlProperty(localName = "value", isAttribute = true)
    private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<TextPattern> getText_pattern() {
		return text_pattern;
	}

	public void setText_pattern(List<TextPattern> text_pattern) {
		this.text_pattern = text_pattern;
	}

	public List<String> getFile() {
		return file;
	}

	public void setFile(List<String> file) {
		this.file = file;
	}

}
