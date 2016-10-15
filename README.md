# Homework
ThoughtWorks 2017 Homework about organizing activity
[repository](https://github.com/jie5025566/Homework)

## Install

1.Install java environment:JDK1.7
  I use eclipse with windows system and the unit test with JUnit framework.
  
2.clone the project:
     go get https://github.com/jie5025566/Homework
     
## Usage

1.run the example data:
  you can run the Homework.jar directly
  ```
  the example data is in data.txt. 
  you can open the windows cmd and input
  java -jar Homework.jar
  then you can see the output.
  ```
  or you can unzip the Homework.jar and add the Summary.class and run the Example.class.
  
2.run the unit test:
  add the Summary.class
  run the SummaryTest.class

## Summary

There are three important class in this project.
One is Summary.java,SummaryTest.java and Example.java.In the Summary.java, String generateSummary(String input) is the interface function.Others are the function modules to achieve the goal.generateSummary function can call the other modules.
The other is SummaryTest.java to realize unit test uses JUnit framework.In the SummaryTest.java, we test the generateSummary function
and other function modules.
The last is Example.java. It is the main function. It read input string from data.txt and print the output string.
