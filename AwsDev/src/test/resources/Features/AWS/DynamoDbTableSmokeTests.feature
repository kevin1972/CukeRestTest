Feature: DynamoDbTableSmokeTests 
	In order to maintain data
  	As a DynamoDb User
  	I want to create, read, update, and delete tables in DynamoDb

Background: 
	Given the following information: 
		| Name   	| Value     		|
		| region 	| us-east-2 		|
		
				
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: CreateTableUsingGivenInformation

When the user creates a table using the given information


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario Outline: CreateTableUsingExplicitInformation

When the user creates a table named "<TableName>"

Examples:
|TableName	|
|			|



#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: CreateTableUsingDataTable

When the user creates a table(s) as described below:
|TableName	|
|			|


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: GetTableNames

When the user gets the table names


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: DescribeTableUsingGivenInformation

When the user describes a table using the given information


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario Outline: DescribeTableUsingExplicitInformation

When the user describees a table named "<TableName>"

Examples:
|TableName	|
|			|



#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: DescribeTableUsingDataTable

When the user describes a table(s) as described below:
|TableName	|
|			|


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: UpdateProvisionedThroughputUsingGivenInformation

When the user updates provisioned throughput using the given information


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario Outline: UpdateProvisionedThroughputUsingExplicitInformation

When the user updates provisioned throughput for the table "<TableName>" by "<NumReadUnits>" read units and "<NumWriteUnits>" write units

Examples:
|TableName		|NumReadUnits	|NumWriteUnits	|
|				|				|				|



#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: CreateTableUsingDataTable

When the user updates the provisioned throughput as described below:
|TableName		|ReadUnits	|WriteUnits	|
|				|			|			|



#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: DeleteTableUsingGivenInformation

When the user deletes a table using the given information


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario Outline: DeleteTableUsingExplicitInformation

When the user deletes a table named "<TableName>"

Examples:
|TableName	|
|			|



#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: DeleteTableUsingDataTable

When the user deletes a table(s) as described below:
|TableName	|
|			|






