# Discrepancy-finder-service

Discrepancy-finder-service is a java project which removes the discrepancies present in the given xml input file and creates two types of files:
1. File with all the discrepancies and their respective line numbers in file
2. File with removed discrepancies

Through this project we are building the java objects taking discrepancy xml file as input.

## Prerequisites

Code will create following directories in the project folder:

'//discrepancy-output-files' and '//remidiated-output-files'.

Following are the external jars used in this project. These jar files are added in the lib folder and referenced in the project.

jackson-annotations-2.10.3

jackson-core-2.10.3

jackson-databind-2.10.3

jackson-dataformat-xml-2.10.3

jackson-module-jaxb-annotations-2.10.3

jakarta.activation-api-1.2.1

jakarta.xml.bind-api-2.3.2

stax2-api-4.2

woodstox-core-6.1.1


## Usage

Add the discrepancies in java-rule.xml file in resources folder.

Once you are done with above steps, run the AppMain.java which contains the main method. Provide the necessary input to proceed.

## Project Status

Currently the project is in development phase. Generating output files   only for java discrepancies mentioned in 'java-rule.xml'. Planning for .Net and tomcat in the next phase.
