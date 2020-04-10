package com.dfs.util;

public class Constants {
	public static enum ADVICE_ENUM { remove_import };
	public static enum ACTION_ENUM { remove, replace };
	public static enum FILE_OPERATION { text_finder, file_finder };
	public static enum REQUIRED_LISTS { codeLineList, discrepancyDetailsList, removeDiscrepancyList };
	public static enum REMIDIATION_ENUM { remove, replace, batch_operation ,information};
	public static String FILE_TYPE= "pom.xml";
}
