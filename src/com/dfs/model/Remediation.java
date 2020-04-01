package com.dfs.model;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Remediation {

    @JacksonXmlProperty(localName = "recommendation", isAttribute = true)
    private String recommendation;

    @JacksonXmlProperty(localName = "complexity", isAttribute = true)
    private String complexity;

    @JacksonXmlProperty(localName = "action", isAttribute = true)
    private String action;

    @JacksonXmlProperty(localName = "savings", isAttribute = true)
    private String savings;

    @JacksonXmlProperty(localName = "replace_with", isAttribute = true)
    private String replace_with;

    @JacksonXmlProperty(localName = "condition", isAttribute = true)
    private String condition;

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getSavings() {
        return savings;
    }

    public void setSavings(String savings) {
        this.savings = savings;
    }

    public String getReplace_with() {
        return replace_with;
    }

    public void setReplace_with(String replace_with) {
        this.replace_with = replace_with;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    @Override public String toString() {
        return "Remediation {" + "recommendation='" + recommendation + '\'' + "complexity='" + complexity + '\''
                + "action='" + action + '\'' +  "savings='" + savings + '\'' +  "replace_with='" + replace_with + '\''
                +  "condition='" + condition + '\'' +
               + '}';
    }
}
