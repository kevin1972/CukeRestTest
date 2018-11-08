Feature: SnsSmokeTests 
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
  Scenario: Create a Topic       
  When the user creates a topic named "test-topic" in the given region
  
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: Subscribe To a Topic       
  Given the following information: 
		| Name   		| Value     		|
		| topicArn	 	| queueName 		|
		| protocol		|					|
		| endpoint		|					| 
		
				
  When the user subscribes to a topic using the given information
  	
  When the user subscribes to the topic arn "" with the protocol "" and the endpoint ""
  
  When the user subscribes to the topic(s) as described below:
  |TopicArn				|Protocol			|Endpoint		|
  |						|					|				|
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: Publish To a Topic       
  Given the following information: 
		| Name   		| Value     		|
		| topicArn	 	| queueName 		|		
		| topicMessage	|					| 
		
				
  When the user publishes to a topic using the given information
  	
  When the user publishes the message ""  to the topic arn ""
  
  When the user publishes the message(s) to the topic(s) as described below:
  |TopicArn				|Topic Message											|
  |						|														|
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: Delete a Topic       
  Given the following information: 
		| Name   		| Value     		|
		| topicArn	 	| queueName 		|				
		
				
  When the user deletes a topic using the given information
  	
  When the user deletes the topic with the arn ""
  
  When the user deletes the topic(s) as described below:
  |TopicArn				|
  |						|