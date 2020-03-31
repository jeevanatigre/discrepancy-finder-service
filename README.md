# Discrepancy-finder-service

## Introduction
Discrepancy-finder-service is a java application which checks discrepancies in Java / .NET / other source files on the basis of input rule xml and creates two output files:
 -  ***discrapency-list.xls*** - list of all discrepancies found in source files (and if applicable, corresponding line numbers)
 -  ***remediated-{source-file}*** - output file with possible fix for discrepancy


## Usage

Program takes two arguments

 - ***1st arg*** - Rule xml file
 - ***2nd arg*** -  0 for find and 1 for find-and-replace***


```sh
#################################################
#                                               #
#   java AppMain <rule_file> <option>           #
#   example : java AppMain sample-rules.xml 0   #	
#                                               #	
#################################################
```

## Sample output file
![Screenshot](sample-output.png) <!-- .element height="100%" width="100%" -->

# More Details

## Benefits

 - ***Inventory insights*** - Provides application inventory details like number of files, types of files, DB opetations fetc 
 - ***Discrapency Complexity*** -  Find exact discrapencies in code on the basis on rules, provides complexity of fixes
 - ***Remediatation*** - Remediates common discrapencies, saves human efforts and erronous actions
 - ***Better Estimations*** - Assists in determining effort estimations for tech upgrades
 - ***Rule Repository*** - Aim is to provide 'Discrepancy Rule Repository' for various tech upgrades / migrate like below

| Languages | Frameworks | Application Servers | Databases | Operating Systems |
| --------- | ---------- | ------------------- | --------- | ----------------- |
| Java 6 | Spring 2.x | Weblogic migrate | Oracle 11 | Solaris 5.1 |
| Java 7 | Spring 3.x | Websphere migrate | Oracle 12 | RHEL 5.0 |
| Java 8 | Spring 4.x | Tomcat upgrade| SQL Server 2008 | RHEL 6.0 |
| .NET 2.0 | JSP 1.x | | | 
| .NET 3.0 | IE 1.8 | | |

See  [Current Rule Repository](https://github.com/jeevanatigre/discrepancy-finder-service/tree/master/rule-repository)


## TODOs

 - Implement rules based on new sample-rule.xml
 - Create report with Apache POI

