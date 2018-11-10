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
@NotTested
Scenario: Get a List of Lambdas
	When the user gets a list of functions
	
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: RunFunctionNoPayload
	When the user invokes the Lambda function named ""
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: RunFunctionWithPayload
	When the user invokes the Lambda function named "" with the payload ""	
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: RunFunctionWithPayload
	Given the following information: 
		| Name   		| Value     						|
		| functionName 	| us-east-2 						|
		| payload		| {"data":{"prop1":"prop1_value"}}	|
	
	When the user invokes the Lambda function using the given data	
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: RunFunctionUsingTable
		
	When the user invokes the Lambda function as described below:
	|FunctionName		|Payload												|
	|					|														|	
	
	 