package com.dfs.report;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;


public interface IReport {

	public void createReport(List<Object> descrepancyDetailsList , String targetLocation) throws InvalidFormatException, IOException;
}
