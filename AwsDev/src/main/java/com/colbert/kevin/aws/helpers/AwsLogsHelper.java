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
import com.amazonaws.services.logs.model.LogGroup;
import com.amazonaws.services.logs.model.LogStream;
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
	public List<LogGroup> getLogGroups(DescribeLogGroupsRequest describeLogGroupsRequest) {
		List<LogGroup> groups = null;		
		groups = this.awsLogsClient.describeLogGroups(describeLogGroupsRequest).getLogGroups();
		System.out.println("Log Groups:");
		for(LogGroup group:groups) {
			System.out.println(group.getLogGroupName());
		}
		return groups;
	}

	public List<LogGroup> getLogGroups() {				
		List<LogGroup> groups = null;		
		System.out.println("Log Groups:");
		groups = this.awsLogsClient.describeLogGroups().getLogGroups();
		for(LogGroup group:groups) {
			System.out.println(group.getLogGroupName());
		}
		return groups;
	}
	
	
	public List<LogStream> getLogStreams(String logGroupName) {				
		List<LogStream> streams = null;		
		System.out.println("Log Streams:");
		DescribeLogStreamsRequest describeLogStreamsRequest = new DescribeLogStreamsRequest(logGroupName);
		DescribeLogStreamsResult result = this.awsLogsClient.describeLogStreams(describeLogStreamsRequest);
		streams = result.getLogStreams();		
		for(LogStream stream:streams) {
			System.out.println(stream.getLogStreamName());
		}
		return streams;
	}

	// ----------------------------------------------------------------
	// Get Logs
	// ----------------------------------------------------------------
	public List<OutputLogEvent> getLogs(String groupName) {
		List<OutputLogEvent> logs = null;
		GetLogEventsResult logEventsResult = this.awsLogsClient.getLogEvents(
				new GetLogEventsRequest().withLogGroupName(groupName));
		logs = logEventsResult.getEvents();
		return logs;
	}
	
		
	public List<OutputLogEvent> getLogs(String groupName, String logStreamName) {
		List<OutputLogEvent> logs = null;
		GetLogEventsResult logEventsResult = this.awsLogsClient.getLogEvents(
				new GetLogEventsRequest().withLogGroupName(groupName).withLogStreamName(logStreamName));
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
