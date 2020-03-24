package com.xmlobjectmapper;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.util.List;

public class Imports {

    @JacksonXmlElementWrapper(localName = "import_tag", useWrapping = false)
    private List<Import> import_tag;

    public List<Import> getImport_tag() {
        return import_tag;
    }

    public void setImport_tag(List<Import> import_tag) {
        this.import_tag = import_tag;
    }

    @Override public String toString() {
        return "Imports{" +
                "type='" + import_tag + '\'' +
                '}';
    }
}
