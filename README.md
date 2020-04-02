# Discrepancy Finder

## Introduction
*Discrepancy-finder* is a java application which takes input as source code  like Java / .NET / other OR config files like Websphere / Weblogic and creates two output files on the basis of specified rules. The two output files created are:
 -  **discrapency-list.xls** - list of all discrepancies found in source files based on rules (and if applicable, corresponding line numbers)
 -  **remediated-{source-file}** - output file with possible fix for discrepancy


## Usage Example

Program takes four arguments

 - **1st arg** - Discrapency rule xml file
 - **2nd arg** - Source / Input location
 - **3rd arg** - Target / Output location
 - **4th arg** - Mode - 0 for find and 1 for find-and-remediate


```sh
##############################################################################
#                                                                            #
#   <rule_file> <source_location> <target_location> <find_remediate_mode>    #
#   example : discrapency-rules.xml C:/input C:/output 0                     #
#                                                                            #
##############################################################################
```

## Sample Output File
![Screenshot](sample-output.png) <!-- .element height="100%" width="100%" -->

# More Details

## Benefits

 - **Inventory Insights** - Provides application inventory details like number of files, types of files, files with DB interactions, etc 
 - **Discrapency & Complexity** -  Find exact discrapencies in code on the basis on rules, provides complexity level for fixes
 - **Remediatation** - Remediates common discrapencies, saves human efforts and erronous actions
 - **Better Estimations** - Assists in determining effort estimations for tech upgrades
 - **Rule Repository** - Aim is to provide 'Discrepancy Rule Repository' for various tech upgrades / migrate like below

| Languages | Frameworks | Application Servers | Databases | Operating Systems |
| --------- | ---------- | ------------------- | --------- | ----------------- |
| Java 6 | Spring 2.x | Weblogic migrate | Oracle 11 | Solaris 5.1 |
| Java 7 | Spring 3.x | Websphere migrate | Oracle 12 | RHEL 5.0 |
| Java 8 | Spring 4.x | Tomcat upgrade| SQL Server 2008 | RHEL 6.0 |
| .NET 2.0 | JSP 1.x | | | 
| .NET 3.0 | IE 1.8 | | |

See  [Rule Repository](https://github.com/jeevanatigre/discrepancy-finder-service/tree/master/rule-repository)


## TODOs

 - Implement rules based on new sample-rule.xml
 - Create report with Apache POI

