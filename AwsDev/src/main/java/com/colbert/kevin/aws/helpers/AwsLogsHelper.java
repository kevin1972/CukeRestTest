package com.colbert.kevin.aws.helpers;

import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.logs.AWSLogs;
import com.amazonaws.services.logs.AWSLogsClientBuilder;
import com.amazonaws.services.logs.model.DescribeLogGroupsRequest;
import com.amazonaws.services.logs.model.DescribeLogGroupsResult;
import com.amazonaws.services.logs.model.DescribeLogStreamsRequest;
import com.amazonaws.services.logs.model.DescribeLogStreamsResult;
import com.amazonaws.services.logs.model.GetLogEventsRequest;
import com.amazonaws.services.logs.model.GetLogEventsResult;
import com.amazonaws.services.logs.model.OutputLogEvent;

public class AwsLogsHelper {
	private AWSCredentials credentials;
	private AWSLogs awsLogsClient;
	private String region;

	public AWSLogs getAwsLogsClient() {
		return this.awsLogsClient;
	}

	// ----------------------------------------------------------------
	// Constructor
	// ----------------------------------------------------------------
	public AwsLogsHelper(AWSCredentials credentials, String region) {
		this.credentials = credentials;
		this.region = region;
		try {
			this.awsLogsClient = createAWSLogsClient(this.region);
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
	}

	// ----------------------------------------------------------------
	// Create AWS Logs Client
	// ----------------------------------------------------------------
	private AWSLogs createAWSLogsClient(String region) {
		AWSLogs client = null;
		try {
			client = AWSLogsClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(this.credentials))
					.withRegion(region).build();
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
		return client;
	}

	// ----------------------------------------------------------------
	// Get Log Groups
	// ----------------------------------------------------------------
	public DescribeLogGroupsResult getLogGroups(DescribeLogGroupsRequest describeLogGroupsRequest) {
		return this.awsLogsClient.describeLogGroups(describeLogGroupsRequest);
	}

	public DescribeLogGroupsResult getLogGroups() {
		return this.awsLogsClient.describeLogGroups();
	}

	// ----------------------------------------------------------------
	// Get Logs
	// ----------------------------------------------------------------
	public List<OutputLogEvent> getLogs(String groupName, String logStreamName) {
		List<OutputLogEvent> logs = null;
		GetLogEventsResult logEventsResult = this.awsLogsClient.getLogEvents(
				new GetLogEventsRequest().withLogGroupName("/aws/batch/job").withLogStreamName(logStreamName));
		logs = logEventsResult.getEvents();
		return logs;
	}

	// ----------------------------------------------------------------
	// Get Log Streams
	// ----------------------------------------------------------------
	public DescribeLogStreamsResult getLogStreams(DescribeLogStreamsRequest describeLogStreamsRequest) {
		return this.awsLogsClient.describeLogStreams(describeLogStreamsRequest);
	}

	// ----------------------------------------------------------------
	// Get Log Events
	// ----------------------------------------------------------------
	public GetLogEventsResult getLogEvents(GetLogEventsRequest getLogEventsRequest) {
		return this.awsLogsClient.getLogEvents(getLogEventsRequest);
	}

}
