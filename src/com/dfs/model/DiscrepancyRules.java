package com.dfs.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.util.List;

@JacksonXmlRootElement(localName = "discrepancy_rules")
public class DiscrepancyRules {

    @JacksonXmlElementWrapper(localName = "rule", useWrapping = false)
    private List<Rule> rule;

    public List<Rule> getRule() {
        return rule;
    }

    public void setRule(List<Rule> rule) {
        this.rule = rule;
    }

    @Override
    public String toString() {
        return "Rule{" +
                "Rule=" + rule +
                '}';
    }
}