Feature: SqsSmokeTests 
	In order to utilize queues
  	As an Amazon SQS user
  	I want to create, get, update, and delete resources in Sqs

Background: 
	Given the following information: 
		| Name   	| Value     		|
		| region 	| us-east-2 		|
		| queueName	| 					|
		
		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested 
Scenario: Create a Queue 
	When the user creates a queue named "test-queue" in the given region 
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: List Queues 
	When the user gets a list of queues in the given region 
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: GetQueueUrlUsingGivenInfo 
	Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|
		
	When the user gets the URL for the given queue
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario Outline: GetQueueUsingExplicitInfo 
	
	When the user gets the URL for the queue named "<QueueName>"
	
Examples:
	|QueueName|	 
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: DeleteQueueUsingGivenInfo 
	Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|
		
		
	When the user deletes the given queue
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario Outline: DeleteQueueUsingExplicitInfo 
	
	When the user deletes the queue named "<QueueName>"
	
Examples:
	|QueueName|	 
	
	 
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: SendMessageUsingGivenInformation 
	Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|  		
		| messageBody	| {messageBody}		|
		| delayInSeconds| 5					|
						
		
	When the user sends the message with the given information
	
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario Outline: SendMessageUsingExplicitInformation 
	
	#When the user sends the message to the queue "" with a "" second delay 
	
	When the user sends the message "<Message>" to the queue "<QueueName>" with a "<DelayInSeconds>" second delay
	
Examples:
	|Message									|QueueName			|DelayInSeconds	|
	|											|					|				|
	 
	 
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: SendMessageUsingTable	
	When the user sends a message(s) as described below: 
		|QueueName		|DelayInSeconds	|MessageBody													|
		|blah			| 5				|																|
		
		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: GetMessagesUsingGivenInfo 
	Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|  				
		| delayInSeconds| 5					|
		
	When the user gets the messages with the given information
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario Outline: GetMessagesUsingExplicitInfo	 
	
	When the user gets the messages from the queue "<QueueName>" with a "<DelayInSeconds>" second delay
	
Examples:
	|QueueName			|DelayInSeconds	|
	|					|				|
	
	 
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: GetMessagesUsingTable	
	When the user gets message(s) as described below: 
		|QueueName		|MessageID		|
		|blah				| 				|  
		
		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: GetSingleMessageUsingGivenInfo 
	Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|		  			
		| delayInSeconds| 5					|
		| messageId		|					|
		
		
	When the user gets the messages with the given information
		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario Outline: GetSingleMessageUsingExplicitInfo		
	 	
	When the user gets the message with the ID "<MessageId>" from the queue "<QueueName>" with a "<DelayInSeconds>" second delay
	
Examples:
	|QueueName		|MessageId		|DelayInSeconds	|
	|				|				|				|	 
	
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: GetSingleMessageUsingTable	
	When the user gets the message using the following criteria: 
		|QueueName		|MessageId	|DelayInSeconds	|
		|blah				|			| 5				|  
		
		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: DeleteMessageUsingGivenInfo 
	Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|  				
		| delayInSeconds| 5					|
		
	When the user deletes the messages with the given information 
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario Outline: DeleteMessageUsingExplicitInfo	
	When the user deletes the message with the ID "<MessageId>" from the queue
	
Examples:
	|MessageId		|
	|				|	 
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: DeleteMessageUsingTable	
	When the user deletes message(s) as described below: 
		|QueueName		|MessageId		|
		|blah			| 5				|		
		
		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: DeleteSingleMessageUsingGivenInfo 
	Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|		  			
		| delayInSeconds| 5					|
		| messageId		|					|
		
		When the user deletes the message with the given information
		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario Outline: DeleteSingleMessageUsingExplicitInfo		
	 	
	When the user deletes the message with the ID "<MessageId>" from the queue "<QueueName>" with a "<DelayInSeconds>" second delay
	
	Examples:
	|QueueName		|MessageId			|DelayInSeconds	|
	|				|					|				|
	

#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: DeleteSingleMessageUsingTable	 
	
	When the user deletes the message(s) using the following criteria: 
		|QueueName		|MessageId		|DelayInSeconds	|
		|blah			|				| 5				|