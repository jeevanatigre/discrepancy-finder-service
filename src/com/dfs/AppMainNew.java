package com.dfs;

import java.io.IOException;

public class AppMainNew {
	
	private String discrapencyRuleFile;
	private String sourceLocation;
	private String targetLocation;
	private String findOrRemediateMode;
	
	public static void main(String[] args) throws IOException {
		if (null != args && 4 != args.length) {
			System.err.println("Invalid inputs, please provide inputs based on below usage example");
			System.err.println("###########################################################################################");
			System.err.println("#                                                                                         #");
			System.err.println("#   java AppMain <rule_file> <source_location> <target_location> <find_remediate_mode>    #");
			System.err.println("#   example : java AppMain discrapency-rules.xml C:/input C:/output 0                     #");	
			System.err.println("#                                                                                         #");
			System.err.println("###########################################################################################");
			System.exit(0);
		}
		new AppMainNew().startWork(args);
	}
	
	public void startWork(String args[]) {
		discrapencyRuleFile = args [0];
		sourceLocation = args [1];
		targetLocation = args [2];
		findOrRemediateMode = args [3];
		System.out.println("Discrapency Rule File = '" + discrapencyRuleFile + "'");
		System.out.println("Source Location = '" + sourceLocation + "'");
		System.out.println("Target Location = '" + targetLocation + "'");
		System.out.println("Mode - ( 0 for Find & 1 for Find + Remediate) = '" + findOrRemediateMode + "'");
		System.out.println("Continue work");

	}
	
	
}
