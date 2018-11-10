Feature: LambdaSmokeTests 
	In order to execute functionality in a serverless environment 
  	As an AWS user
  	I want to execute functions using the Lambda Service

Background: 
	Given the following information: 
		| Name   	| Value     		|
		| region 	| us-east-2 		|		
		
		
		
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------	
@Tested
Scenario: Get a List of Lambdas
	When the user gets a list of functions
	
	Then the following lambdas should be returned:
	|LambdaName				|
	|HelloWorld				|
	|cy-store-data			|
	|cy-get-data			|
	#|badfilename			|
	
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------
@Tested
Scenario: RunFunctionNoPayload
	When the user invokes the Lambda function named "HelloWorld"
	
	Then the status code "200" should be returned
	
	
##---------------------------------------------------------------------------
##
##---------------------------------------------------------------------------		
@RunMe
Scenario Outline: RunFunctionWithPayload
	When the user invokes the Lambda function named '<FunctionName>' with the payload '<Payload>'
	
	Then the status code '<StatusCode>' should be returned
	And the value returned from the Lambda function should be '<ExpectedValue>'
	
	Examples:
	|FunctionName	|Payload						|StatusCode	|ExpectedValue				|	
	|HelloWorld		|"Kevin"						|200		|"Hello from Lambda!Kevin"	|
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@Tested
Scenario: RunFunctionWithPayload
	Given the following information: 
		| Name   		| Value     						|
		| functionName 	| HelloWorld 						|
		| payload		| Kevin								|
	
	When the user invokes the Lambda function using the given data
	And the output from the Lambda function is displayed on the console		
	
	Then the status code "200" should be returned	
	And the value returned from the Lambda function should be '"Hello from Lambda!Kevin"'		
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
#@RunMe
@Tested
Scenario: RunFunctionUsingTable
		
	When the user invokes the Lambda function as described below:
	|FunctionName		|Payload									|
	|HelloWorld			|"Test"										|

	And the output from the Lambda function is displayed on the console
		
	Then the status code "200" should be returned
			
	And the value(s) returned from the Lambda function should match the following conditions:
	|Description				|ExpectedValue						|
	|Expected Message			|"Hello from Lambda!Test"			|	
	
	
	
	 