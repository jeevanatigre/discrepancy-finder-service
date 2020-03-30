# Discrepancy-finder-service

Discrepancy-finder-service is a java application which checks discrepancies in java / .net / other source code based on input rule xml and creates two output files:
 -  Discrepancies file - discrepancies found in source files and their respective line numbers 
 -  Remediated file - output file with possible fix for discrepancy


## Usage

Program takes two arguments

 - 1st arg - Rule xml file
 - 2nd arg -  0 for find and 1 for find-and-replace


```sh
#################################################
#                                               #
#	 java AppMain <rule_file> <option>           #
#	 example : java AppMain sample-rules.xml 0   #	
#                                               #	
#################################################
```

## Sample output file
![Screenshot](sample-output.png)

## TODOs

 - Implement rules based on new sample-rule.xml
 - Create report with Apache POI

