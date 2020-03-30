# Discrepancy-finder-service

Discrepancy-finder-service is a java project which checks discrepancies based on input rule xml and creates two types of files:
 -  Discrepancies file - with all the discrepancies found and their respective line numbers 
 -  Remediated file - with possible discrepancies fixes 


## Usage

Program takes two arguments

 - 1st arg - Rule xml file
 - 2nd arg -  0 for find and 1 for find-and-replace


```sh
################################################
#															 #
#	 java AppMain <rule_file> <option> 			 #
#	 example : java AppMain sample-rules.xml 0 	 #	
#															 #	
################################################
```

## Sample output file
![Screenshot](sample-output.png)

## TODOs

 - Implement rules based on new sample-rule.xml
 - Create report with Apache POI

