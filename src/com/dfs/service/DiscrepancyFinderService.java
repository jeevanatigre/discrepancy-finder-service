package com.dfs.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import com.dfs.model.Rule;

public interface DiscrepancyFinderService {
	
	public Map<String, List<Object>> findDiscrepancy(File file, String findOrRemediateMode, Rule rule,
			String sourceLocation, String targetLocation, String[] args, List<Object> codeLineList) throws InvalidFormatException, IOException;

}
