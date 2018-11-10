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
#@RunMe
#Scenario: RunFunctionWithPayload
#	When the user invokes the Lambda function named "HelloWorld" with the payload "{"Kevin":"Test"}"
#	
#	Then the status code "200" should be returned	
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@Tested
Scenario: RunFunctionWithPayload
	Given the following information: 
		| Name   		| Value     						|
		| functionName 	| HelloWorld 						|
		| payload		| 									|
	
	When the user invokes the Lambda function using the given data
	
	Then the status code "200" should be returned		
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@RunMe
@Tested
Scenario: RunFunctionUsingTable
		
	When the user invokes the Lambda function as described below:
	|FunctionName		|Payload									|
	|HelloWorld			|Test										|
	
	Then the status code "200" should be returned	
	
	 