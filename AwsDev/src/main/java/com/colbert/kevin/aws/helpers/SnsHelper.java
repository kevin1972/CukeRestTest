package com.colbert.kevin.aws.helpers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.ResponseMetadata;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.AmazonSNSClientBuilder;
import com.amazonaws.services.sns.model.CreateTopicRequest;
import com.amazonaws.services.sns.model.CreateTopicResult;
import com.amazonaws.services.sns.model.DeleteTopicRequest;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.amazonaws.services.sns.model.SubscribeRequest;

public class SnsHelper {
	private AWSCredentials credentials;
	private AmazonSNSClient snsClient;
	private String region;

	public AmazonSNSClient getSnsClient() {
		return this.snsClient;
	}

	// ----------------------------------------------------------------
	// Constructor
	// ----------------------------------------------------------------
	public SnsHelper(AWSCredentials credentials, String region) {
		this.credentials = credentials;
		this.region = region;
		try {
			this.snsClient = createSnsClient(this.region);
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
	}

	// ----------------------------------------------------------------
	// Create SNS Client
	// ----------------------------------------------------------------
	private AmazonSNSClient createSnsClient(String region) {
		AmazonSNSClient snsClient = null;
		try {
			// AmazonSNSClient snsClient = new AmazonSNSClient(new
			// ClasspathPropertiesFileCredentialsProvider());
			snsClient = (AmazonSNSClient) AmazonSNSClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(this.credentials)).withRegion(region).build();			
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
		return snsClient;
	}

	// ----------------------------------------------------------------
	// Create Topic
	// ----------------------------------------------------------------
	public CreateTopicResult createTopic(String topic) {
		// create a new SNS topic
		CreateTopicRequest createTopicRequest = new CreateTopicRequest(topic);
		CreateTopicResult createTopicResult = snsClient.createTopic(createTopicRequest);
		return createTopicResult;
	}

	// ----------------------------------------------------------------
	// Subscribe to a Topic
	// ----------------------------------------------------------------
	public ResponseMetadata subscribeToTopic(String topicArn, String protocol, String endpoint) {
		// subscribe to an SNS topic
		SubscribeRequest subRequest = new SubscribeRequest(topicArn, protocol, endpoint);
		snsClient.subscribe(subRequest);
		// get request id for SubscribeRequest from SNS metadata
		ResponseMetadata responseMetaData = snsClient.getCachedResponseMetadata(subRequest);
		System.out.println("SubscribeRequest - " + responseMetaData);
		return responseMetaData;
	}

	// ----------------------------------------------------------------
	// Publish to a Topic
	// ----------------------------------------------------------------
	public String publishToTopic(String topicArn, String msg) {
		PublishRequest publishRequest = new PublishRequest(topicArn, msg);
		PublishResult publishResult = snsClient.publish(publishRequest);
		String messageId = publishResult.getMessageId();
		System.out.println("MessageId - " + messageId);
		return messageId;
	}

	// ----------------------------------------------------------------
	// Delete a Topic
	// ----------------------------------------------------------------
	public ResponseMetadata deleteTopic(String topicArn) {
		//delete an SNS topic
		DeleteTopicRequest deleteTopicRequest = new DeleteTopicRequest(topicArn);
		snsClient.deleteTopic(deleteTopicRequest);
		//get request id for DeleteTopicRequest from SNS metadata
		ResponseMetadata responseMetaData =  snsClient.getCachedResponseMetadata(deleteTopicRequest);
		System.out.println("DeleteTopicRequest - " + responseMetaData);		
		return responseMetaData;
	}
	
	
	
}
