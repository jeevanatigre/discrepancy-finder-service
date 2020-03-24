package com.xmlobjectmapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Rule {

    @JacksonXmlProperty(localName = "type", isAttribute = true)
    private String type;

    @JacksonXmlElementWrapper(localName = "imports", useWrapping = false)
    private Imports imports;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Imports getImports() {
        return imports;
    }

    public void setImports(Imports imports) {
        this.imports = imports;
    }

    public Rule() {
    }

    public Rule(String type, Imports imports) {
        this.type = type;
        this.imports = imports;
    }

    @Override public String toString() {
        return "Rule{" +
                "type='" + type + '\'' +
                '}';
    }

}
