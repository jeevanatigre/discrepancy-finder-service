# Discrepancy-finder-service

Discrepancy-finder-service is a java project which checks discrepancies present in the given xml input file and creates two types of files:
1. File with all the discrepancies and their respective line numbers in file
2. File with removed discrepancies

Through this project we are building the java objects taking discrepancy xml file as input.

## Prerequisites

Code will create following directories in the project folder:

'//discrepancy-output-files' and '//remidiated-output-files'.


## Usage

Program takes two arguments

1. Rule_File
2. Option - 0 for find and 1 for find-and-replace


#####################################################################
#																	#
#	 java AppMain <rule_file> <option> 					#
#	 example : java AppMain sample-rules.xml 0 			#	
#																	#	
####################################################################

## TODOs

1. Implement rules based on new sample-rule.xml
2. Create report with Apache POI