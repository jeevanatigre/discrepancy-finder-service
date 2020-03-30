package com.xmlobjectmapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

public class Replatform {

    @JacksonXmlProperty(localName = "advice", isAttribute = true)
    private String advice;

    @JacksonXmlProperty(localName = "action", isAttribute = true)
    private String action;

    @JacksonXmlProperty(localName = "replace_with", isAttribute = true)
    private String replace_with;

    @JacksonXmlProperty(localName = "condition", isAttribute = true)
    private String condition;

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
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
        return "Replatform {" + "advice='" + advice + '\'' + "action='" + action + '\''
                + "replace_with='" + replace_with + '\'' + "condition='" + condition + '\'' + '}';
    }
}
