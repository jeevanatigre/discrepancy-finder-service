package com.xmlobjectmapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;


@JacksonXmlRootElement(localName = "discrepancy_rules")
public class DiscrepancyRules {

    @JacksonXmlElementWrapper(localName = "rule", useWrapping = false)
    private Rule rule;

    public Rule getRule() {
        return rule;
    }

    public void setRule(Rule rule) {
        this.rule = rule;
    }

    @Override 
    public String toString() {
        return "Rules{" +
                "Rules=" + rule +
                '}';
    }
}