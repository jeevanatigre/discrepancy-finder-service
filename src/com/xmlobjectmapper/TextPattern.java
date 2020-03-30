package com.xmlobjectmapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class TextPattern {

    @JacksonXmlProperty(localName = "value", isAttribute = true)
    private String value;

    @JacksonXmlProperty(localName = "condition", isAttribute = true)
    private String condition;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override public String toString() {
        return "TextPattern{ value='" + value + '\'' +  "condition='" + condition + '\'' + '}';
    }
}
