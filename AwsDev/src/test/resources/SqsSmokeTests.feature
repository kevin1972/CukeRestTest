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
  Scenario: Create a Queue       
  When the user creates a queue named "test-queue" in the given region
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: List Queues       
  When the user gets a list of queues in the given region  
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: Get Queue URL       
  Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|
		
  When the user gets the URL for the given queue
  	
  When the user gets the URL for the queue named ""
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: Delete a Queue       
  Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|
		
		
  When the user deletes the given queue
  	
  When the user deletes the queue named ""
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: Send a Message       
  Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|  		
		| messageBody	| {messageBody}		|
		| delayInSeconds| 5					|
		
  When the user sends the message with the given information
  
  When the user sends the message to the queue "" with a "" second delay
  
  When the user sends the message "" to the queue "" with a "" second delay
  
  When the user sends a message(s) as described below:
  |QueueName		|DelayInSeconds	|MessageBody													|
  |blah				| 5				|																|
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: Get Messages       
  Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|  				
		| delayInSeconds| 5					|
		
  When the user gets the messages with the given information
  
  When the user gets the messages from the queue "" with a "" second delay
    
  When the user gets message(s) as described below:
  |QueueName		|MessageID		|
  |blah				| 				|  
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: Get A Single Message       
  Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|		  			
		| delayInSeconds| 5					|
		| messageId		|					|
		
		
  When the user gets the messages with the given information
  
  When the user gets the message with the ID "" from the queue "" with a "" second delay
    
  When the user gets the message using the following criteria:
  |QueueName		|MessageId	|DelayInSeconds	|
  |blah				|			| 5				|  
   		
		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: Delete Messages       
  Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|  				
		| delayInSeconds| 5					|
		
  When the user deletes the messages with the given information
  
  When the user deletes the message with the ID "" from the queue
    
  When the user deletes message(s) as described below:
  |QueueName		|MessageId		|
  |blah				| 5				|		
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: Delete A Single Message       
  Given the following information: 
		| Name   		| Value     		|
		| queueName 	| queueName 		|		  			
		| delayInSeconds| 5					|
		| messageId		|					|
		
		
  When the user deletes the message with the given information
  
  When the user deletes the message with the ID "" from the queue "" with a "" second delay
    
  When the user deletes the message(s) using the following criteria:
  |QueueName		|MessageId	|DelayInSeconds	|
  |blah				|			| 5				|