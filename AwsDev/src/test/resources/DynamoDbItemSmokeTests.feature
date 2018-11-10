Feature: DynamoDbItemSmokeTests 
	In order to maintain items in the database
  	As a DynamoDb User
  	I want to create, read, update, and delete items in DynamoDb

Background: 
	Given the following information: 
		| Name   	| Value     		|
		| region 	| us-east-2 		|
		
		
		
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: PutItemUsingGivenInformation

When the user puts an item using the given information


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario Outline: PutItemUsingExplicitInformation

When the user puts an item named "<ItemName>" into the table "<TableName>"

Examples:
|ItemName		|TableName			|
|				|					|



#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: PutItemUsingDataTable

When the user puts an item as described below:
|TableName		|ItemName			|
|				|					|


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: GetItemUsingGivenInformation

When the user puts an item using the given information


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario Outline: GetItemUsingExplicitInformation

When the user gets an item with the key "<ItemKey>" from the table "<TableName>"

Examples:
|ItemKey		|TableName			|
|				|					|



#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: GetItemUsingDataTable

When the user gets an item(s) as described below:
|TableName		|ItemKey			|
|				|					|




#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: DeleteItemUsingGivenInformation

When the user deletes an item using the given information


#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario Outline: DeleteItemUsingExplicitInformation

When the user deletes an item with the key "<ItemKey>" from the table "<TableName>"

Examples:
|TableName		|ItemKey			|
|				|					|



#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario: DeleteItemUsingDataTable

When the user deletes an item(s) as described below:
|TableName		|ItemKey			|
|				|					|




###################################################################################################
#TO-DO
###################################################################################################

##---------------------------------------------------------------------------
##
##---------------------------------------------------------------------------		
#@NotTested
#Scenario: UpdateItemUsingGivenInformation
#
#When the user updates an item using the given information
#
#
##---------------------------------------------------------------------------
##
##---------------------------------------------------------------------------		
#@NotTested
#Scenario Outline: UpdateItemUsingExplicitInformation
#
#When the user updates an item named "<ItemKey>" in the table "<TableName>"
#
#Examples:
#|TableName		|ItemKey			|
#|				|					|
#
#
#
##---------------------------------------------------------------------------
##
##---------------------------------------------------------------------------		
#@NotTested
#Scenario: UpdateItemUsingDataTable
#
#When the user updates an item(s) as described below:
#|TableName		|ItemKey			|
#|				|					|		