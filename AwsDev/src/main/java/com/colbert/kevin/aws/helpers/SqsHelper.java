package com.colbert.kevin.aws.helpers;

import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.AmazonSQSException;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.SendMessageRequest;

public class SqsHelper {
	private AWSCredentials credentials;
	private AmazonSQS sqsClient;
	private String region;

	public AmazonSQS getSqsClient() {
		return sqsClient;
	}

	// ----------------------------------------------------------------
	// Constructor
	// ----------------------------------------------------------------
	public SqsHelper(AWSCredentials credentials, String region) {
		this.credentials = credentials;
		this.region = region;
		try {
			this.sqsClient = createSqsClient(this.region);
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
	}

	// ----------------------------------------------------------------
	// Create SQS Client
	// ----------------------------------------------------------------
	private AmazonSQS createSqsClient(String region) {
		AmazonSQS sqs = null;
		try {
			sqs = AmazonSQSClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(this.credentials))
					.withRegion(region).build();
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
		return sqs;
	}

	// ----------------------------------------------------------------
	// Create Queue
	// ----------------------------------------------------------------
	public void createQueue(String queueName, String delaySeconds, String messageRetentionPeriod) {
		CreateQueueRequest create_request = new CreateQueueRequest(queueName)
				.addAttributesEntry("DelaySeconds", delaySeconds)
				.addAttributesEntry("MessageRetentionPeriod", messageRetentionPeriod);
		try {
			this.sqsClient.createQueue(create_request);
		} catch (AmazonSQSException e) {
			if (!e.getErrorCode().equals("QueueAlreadyExists")) {
				throw e;
			}
		}
	}
	
	
	public void createQueue(String queueName, String region, String delaySeconds, String messageRetentionPeriod) {
		
		if(!this.region.equals(region)) {
			this.region = region;
			this.sqsClient = createSqsClient(region);
		}
		
		CreateQueueRequest create_request = new CreateQueueRequest(queueName)
				.addAttributesEntry("DelaySeconds", delaySeconds)
				.addAttributesEntry("MessageRetentionPeriod", messageRetentionPeriod);
		try {
			this.sqsClient.createQueue(create_request);
		} catch (AmazonSQSException e) {
			if (!e.getErrorCode().equals("QueueAlreadyExists")) {
				throw e;
			}
		}
	}

	// ----------------------------------------------------------------
	// List Queues
	// ----------------------------------------------------------------
	public ListQueuesResult listQueues() {
		ListQueuesResult lq_result = this.sqsClient.listQueues();
		System.out.println("Your SQS Queue URLs:");
		for (String url : lq_result.getQueueUrls()) {
			System.out.println(url);
		}
		return lq_result;
	}
	
	public ListQueuesResult listQueues(String region) {
		if(!this.region.equals(region)) {
			this.region = region;
			this.sqsClient = createSqsClient(region);
		}
		ListQueuesResult lq_result = this.sqsClient.listQueues();
		System.out.println("Your SQS Queue URLs:");
		for (String url : lq_result.getQueueUrls()) {
			System.out.println(url);
		}
		return lq_result;
	}

	// ----------------------------------------------------------------
	// Get Queue URL
	// ----------------------------------------------------------------
	public String getQueueUrl(String queueName) {
		return this.sqsClient.getQueueUrl(queueName).getQueueUrl();
	}
	
	public String getQueueUrl(String queueName, String region) {
		if(!this.region.equals(region)) {
			this.region = region;
			this.sqsClient = createSqsClient(region);
		}
		return this.sqsClient.getQueueUrl(queueName).getQueueUrl();
	}

	// ----------------------------------------------------------------
	// Delete Queue
	// ----------------------------------------------------------------
	public void deleteQueue(String queueUrl) {
		sqsClient.deleteQueue(queueUrl);
	}

	// ----------------------------------------------------------------
	// Send a Message
	// ----------------------------------------------------------------
	public void sendMessage(String queueUrl, String messageBody, int delaySeconds) {
		SendMessageRequest request = new SendMessageRequest().withQueueUrl(queueUrl).withMessageBody(messageBody)
				.withDelaySeconds(delaySeconds);
		this.sqsClient.sendMessage(request);
	}

	// ----------------------------------------------------------------
	// Get Message(s)
	// ----------------------------------------------------------------
	public List<Message> getMessages(String queueUrl) {
		List<Message> messages = this.sqsClient.receiveMessage(queueUrl).getMessages();
		return messages;
	}


	public Message getMessage(String queueUrl, String messageId, int delaySeconds) throws Exception {
		Message foundMessage = null;
		List<Message> messages = this.sqsClient.receiveMessage(queueUrl).getMessages();
		for (Message m : messages) {
			if (m.getMessageId().equals(messageId)) {
				foundMessage = m;
				break;
			}
		}

		if (foundMessage == null) {
			throw new Exception("Could not find a message with the id '" + messageId + "'");
		}

		return foundMessage;
	}

	// ----------------------------------------------------------------
	// Delete Message(s)
	// ----------------------------------------------------------------
	public void deleteMessages(String queueUrl, List<Message> messages) {
		for (Message m : messages) {
			this.sqsClient.deleteMessage(queueUrl, m.getReceiptHandle());
		}
	}

	public void deleteMessage(String queueUrl, Message m) {
		this.sqsClient.deleteMessage(queueUrl, m.getReceiptHandle());
	}

}
