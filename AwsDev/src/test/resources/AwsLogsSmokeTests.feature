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
#@RunMe
@Tested
Scenario: GetLogGroups	
	When the user retrieves the log groups
					
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
#@RunMe
@Tested
Scenario Outline: GetLogsByGroupName	
	When the user retrieves the latest log from the log group named "<LogGroupName>"
		
Examples:
	|LogGroupName			|
	|/aws/lambda/HelloWorld	|

	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
#@RunMe
@Tested	
Scenario Outline: GetLogStream	
	When the user retrieves the Log Streams for the Log Group named "<LogGroupName>"
		
Examples:
	|LogGroupName				|
	|/aws/lambda/HelloWorld		|	
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
#@RunMe
@Tested	
Scenario Outline: GetLatestLogStreamFromGroup	
	When the user retrieves the latest Log Stream for the Log Group named "<LogGroupName>"
		
Examples:
	|LogGroupName				|
	|/aws/lambda/HelloWorld		|	
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
#@RunMe
@Tested	
Scenario Outline: GetLatestEventLogsFromLatestEvent	
	When the user retrieves the latest event logs for the Log Group named "<LogGroupName>"
		
Examples:
	|LogGroupName				|
	|/aws/lambda/HelloWorld		|	
	
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@RunMe
@NotTested	
Scenario Outline: GetLatestEventLogFromLatestEvent	
	When the user retrieves the latest event log for the Log Group named "<LogGroupName>"
		
Examples:
	|LogGroupName				|
	|/aws/lambda/HelloWorld		|	
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
#@RunMe
@Tested
Scenario Outline: GetLogsByGroupNameAndStreamName	
	When the user retrieves the latest log from the log group named "<LogGroupName>" and the stream named "<StreamName>"
		
Examples:
	|LogGroupName					|StreamName												|
	|/aws/lambda/HelloWorld			|2018/11/18/[$LATEST]20291844bcae426ea97432a4b132d107	|
	
	
##---------------------------------------------------------------------------
##
##---------------------------------------------------------------------------		
#@NotTested
#Scenario: GetLogStream
#	When the user invokes the Lambda function named "<FunctionName>"
#	
#	And the user retrieves the log stream(s) as described below:
#	|LogStreamDescriptor1	|LogStreamDescriptor2	|LogStreamDescriptor3	|LogStreamDescriptor4	|LogStreamDescriptor5	|
#	|						|						|						|						|						|	
#	
#	
##---------------------------------------------------------------------------
##
##---------------------------------------------------------------------------		
#@NotTested
#Scenario: GetLogEvent
#	When the user invokes the Lambda function named "<FunctionName>"
#	
#	And the user retrieves the log event(s) as described below:
#	|GroupName				|
#	|						|	
	
	
	