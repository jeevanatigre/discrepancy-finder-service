package com.dfs.util;

public class Constants {
	
	public static enum ADVICE_ENUM { remove_import };
	public static enum ACTION_ENUM { remove, replace };
	public static String XML_FILE_PATTERN = "*.xml";
	public static final String MANDATORY = "mandatory";
	public static final String OPTIONAL = "optional";
	public static enum FILE_OPERATION { batch, individual };
	public static enum REQUIRED_LISTS { codeLineList, discrepancyDetailsList, removeDiscrepancyList };

}
