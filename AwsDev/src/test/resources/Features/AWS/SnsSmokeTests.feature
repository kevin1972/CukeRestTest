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
  Scenario: SubscribeToATopicUsingGivenInfo       
  Given the following information: 
		| Name   		| Value     		|
		| topicArn	 	| queueName 		|
		| protocol		|					|
		| endpoint		|					| 
		
				
  When the user subscribes to a topic using the given information
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario Outline: SubscribeToATopicUsingExplicitInfo       
    	
  When the user subscribes to the topic arn "<TopicArn>" with the protocol "<Protocol>" and the endpoint "<Endpoint>"
  
  Examples:
  |TopicArn						|Protocol			|Endpooint					|
  |								|					|							|
  
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: SubscribeToTopicUsingTable  
  
  When the user subscribes to the topic(s) as described below:
  |TopicArn				|Protocol			|Endpoint		|
  |						|					|				|
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: PublishToATopicUsingGivenInfo       
  Given the following information: 
		| Name   		| Value     		|
		| topicArn	 	| queueName 		|		
		| topicMessage	|					| 
		
				
  When the user publishes to a topic using the given information
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario Outline: PublishToATopicUsingExplicitInfo       
  	
				  
  When the user publishes the message "<Message>"  to the topic arn "<TopicArn>"
  
  Examples:
  |Message										|TopicArn						|
  |												|								|
  
    
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: PublishToTopicUsingTable
         
  When the user publishes the message(s) to the topic(s) as described below:
  |TopicArn				|Topic Message											|
  |						|														|
  
  
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: DeleteTopicUsingGivenInfo       
  Given the following information: 
		| Name   		| Value     		|
		| topicArn	 	| queueName 		|				
		
				
  When the user deletes a topic using the given information
  
  #---------------------------------------------------------------------------
  #---------------------------------------------------------------------------
  Scenario Outline: DeleteTopicExplicitly
  
  When the user deletes the topic with the arn "<TopicArn>"
         
  Examples:
  |TopicArn								|
  
  	
  
  #---------------------------------------------------------------------------
  #---------------------------------------------------------------------------
  Scenario: DeleteTopicUsingTable
  
  When the user deletes the topic(s) as described below:
  |TopicArn				|
  |						|
  
  
  
  