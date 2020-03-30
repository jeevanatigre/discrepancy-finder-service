# Discrepancy-finder-service

## Introduction
Discrepancy-finder-service is a java application which checks discrepancies in java / .net / other source files based on input rule xml and creates two output files:
 -  Discrepancies file - list of all discrepancies found in source files (and if applicable, corresponding line numbers)
 -  Remediated file - output file with possible fix for discrepancy


## Usage

Program takes two arguments

 - 1st arg - Rule xml file
 - 2nd arg -  0 for find and 1 for find-and-replace


```sh
#################################################
#                                               #
#   java AppMain <rule_file> <option>           #
#   example : java AppMain sample-rules.xml 0   #	
#                                               #	
#################################################
```

## Sample output file
![Screenshot](sample-output.png)

# More Details

## Benefits

 - Provides application insights like types of files in application inventory
 - Find discrapencies in code based on rules and provides complexity of fixes
 - Remediates common discrapencies, saves human efforts and erronous actions
 - Assists in determining effort estimations for tech upgrades
 - Aims is to provide 'Discrepancy Rule Repository' for various tech upgrades like below

| Languages | Frameworks | Application Servers | Databases | Operating Systems |
| --------- | ---------- | ------------------- | --------- | ----------------- |
| Java 1.6 | Spring 2.x | Weblogic migration | Oracle 11 | Solaris 5.1 |
| Java 1.7 | Spring 3.x | Websphere migratin | Oracle 12 | RHEL 5.0 |
| java 1.8 | Spring 4.x | Tomcat upgrade| SQL Server 2008 | RHEL 6.0 |
| .NET 2.0 | JSP 1.x | | | 
| .NET 3.0 | IE 1.8 | | |

See  [Current Rule Repository](https://github.com/jeevanatigre/discrepancy-finder-service/tree/master/rule-repository)


## TODOs

 - Implement rules based on new sample-rule.xml
 - Create report with Apache POI

