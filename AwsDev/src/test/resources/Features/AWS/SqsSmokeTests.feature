Feature: SqsSmokeTests 
	In order to utilize queues
  	As an Amazon SQS user
  	I want to create, get, update, and delete resources in Sqs

Background: 
	Given the following information: 
		| Name   	| Value     				|
		| region 	| us-east-2 				|
		| queueName	| kevins-test-queue-103072	|


#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@Tested
Scenario: List Queues 
	When the user gets a list of queues in the given region		

#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@Tested
Scenario Outline: List Queues 
	When the user gets a list of queues for the region "<Region>"
	
Examples:
|Region		|	
|us-east-1	|			
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@Tested 
Scenario: CreateQueue 
	When the user creates a queue named "kevins-test-queue-103072" in the given region 
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@Tested 
Scenario Outline: CreateQueueWithNameAndRegion 
	When the user creates a queue named "<QueueName>" in the region "<Region>"
	And the user gets a list of queues in the given region	
	
Examples:
	|QueueName							|Region		|		
	|kevins-test-queue-103072-us-east-1	|us-east-1	|
	

	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@Tested
Scenario: GetQueueUrlUsingGivenInfo 
	Given the following information: 
		| Name   		| Value     					|
		| queueName 	| kevins-test-queue-103072 		|
		
	When the user gets the URL for the given queue
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@NotTested
Scenario Outline: GetQueueUsingExplicitInfo 
	
	When the user gets the URL for the queue named "<QueueName>" in the region "<Region>"
	
Examples:
	|QueueName					|Region		|
	|kevins-test-queue-103072	|use-east-2	|
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@Tested
Scenario: DeleteQueueUsingGivenInfo 

	When the user creates a queue named "kevins-test-queue-103072" in the given region
	And the user gets a list of queues in the given region				
	And the user deletes the queue
	And the user waits "60" seconds
	And the user gets a list of queues in the given region
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@NotTested
Scenario Outline: DeleteQueueUsingExplicitInfo 
	
	When the user creates a queue named "<QueueName>" in the given region
	And the user gets a list of queues in the given region				
	And the user deletes the queue
	And the user waits "60" seconds
	And the user gets a list of queues in the given region
	
Examples:
	|QueueName					|	 
	|kevins-test-queue-103072	|
	
	
	 		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@Tested
Scenario: SendMessageUsingGivenInformation 
	Given the following information: 
		| Name   		| Value     												|
		| queueName 	| kevins-test-queue-103072 									|  		
		| body			| {"body":{"firstName":"Kevin","lastName":"Colbert"}}		|
		| delayInSeconds| 1															|
								
	When the user sends the message with the given information
	
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@Tested
Scenario Outline: SendMessageUsingExplicitInformation 
	
	#When the user sends the message to the queue "" with a "" second delay 
	
	When the user sends the message '"<Message>"' to the queue "<QueueName>" with a "<DelayInSeconds>" second delay
	
Examples:
	|Message												|QueueName					|DelayInSeconds	|
	|{"body":{"city":"Aurora","state":"Colorado"}}			|kevins-test-queue-103072	|1				|
	 
	 
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@Tested
Scenario: SendMessageUsingTable	
	When the user sends a message(s) as described below: 
		|QueueName					|DelayInSeconds	|MessageBody													|
		|kevins-test-queue-103072	| 1				|{"body":{"country":"America","planet":"Earth"}}				|
		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@RunMe
@NotTested
Scenario: ListMessagesUsingGivenInfo 
	Given the following information: 
		| Name   		| Value     					|
		| queueName 	| kevins-test-queue-103072 		|  				
		| delayInSeconds| 1								|
		
	When the user lists the messages with the given information
			
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#@RunMe
@NotTested
Scenario: GetMessagesUsingGivenInfo 
	Given the following information: 
		| Name   		| Value     					|
		| queueName 	| kevins-test-queue-103072 		|  				
		| delayInSeconds| 1								|
		
	When the user gets the messages with the given information
	
	
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario Outline: GetMessagesUsingExplicitInfo	 
	
	When the user gets the messages from the queue "<QueueName>" with a "<DelayInSeconds>" second delay
	
Examples:
	|QueueName					|DelayInSeconds	|
	|kevins-test-queue-103072	|1				|
	
	 
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: GetMessagesUsingTable	
	When the user gets message(s) as described below: 
		|QueueName					|MessageID		|
		|kevins-test-queue-103072	| 				|  
		
		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
@NotTested
Scenario: GetSingleMessageUsingGivenInfo 
	Given the following information: 
		| Name   		| Value     				|
		| queueName 	| kevins-test-queue-103072	|		  			
		| delayInSeconds| 1							|
		| messageId		|							|
		
		
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
		
		
