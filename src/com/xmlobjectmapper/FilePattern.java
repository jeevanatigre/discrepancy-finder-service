package com.xmlobjectmapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class FilePattern {

    @JacksonXmlProperty(localName = "value", isAttribute = true)
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override public String toString() {
        return "Import{value='" + value + '\'' +  '}';
    }
}
