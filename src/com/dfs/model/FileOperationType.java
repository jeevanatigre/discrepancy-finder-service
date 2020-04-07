package com.dfs.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FileOperationType {
	
    @JacksonXmlProperty(localName = "value", isAttribute = true)
    private String value;

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
