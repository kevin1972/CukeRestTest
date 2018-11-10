Feature: AwsLogsSmokeTests 
	In order to get information about services and executions 
  	As an AWS user
  	I want to use AWS logs to get information

Background: 
	Given the following information: 
		| Name   	| Value     		|
		| region 	| us-east-2 		|
		


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario Outline: GetLogGroups
	When the user invokes the Lambda function named "<FunctionName>"
	And the user retrieves the log groups
		
Examples:
	|FunctionName	|
	|blah			|		
				
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario Outline: GetLogsByGroupName
	When the user invokes the Lambda function named "<FunctionName>"
	And the user retrieves the latest log from the log group named "<functionName>"
		
Examples:
	|FunctionName	|
	|blah			|	
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario Outline: GetLogsByGroupNameAndStreamName
	When the user invokes the Lambda function named "<FunctionName>"
	And the user retrieves the latest log from the log group named "<functionName>" and the stream named "<StreamName>"
		
Examples:
	|FunctionName	|StreamName	|
	|blah			|			|
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: GetLogStream
	When the user invokes the Lambda function named "<FunctionName>"
	
	And the user retrieves the log stream(s) as described below:
	|LogStreamDescriptor1	|LogStreamDescriptor2	|LogStreamDescriptor3	|LogStreamDescriptor4	|LogStreamDescriptor5	|
	|						|						|						|						|						|	
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: GetLogEvent
	When the user invokes the Lambda function named "<FunctionName>"
	
	And the user retrieves the log event(s) as described below:
	|GroupName				|
	|						|	
	
	
	