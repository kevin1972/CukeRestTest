package steps;

import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.logs.model.GetLogEventsRequest;
import com.amazonaws.services.logs.model.GetLogEventsResult;
import com.amazonaws.services.logs.model.LogGroup;
import com.amazonaws.services.logs.model.LogStream;
import com.amazonaws.services.logs.model.OutputLogEvent;
import com.colbert.kevin.aws.helpers.AwsLogsHelper;
import com.colbert.kevin.aws.helpers.CredentialsHelper;
import cucumber.api.java.en.When;
import framework.Constants;
import framework.TestRun;

public class AwsLogsSteps {
	private TestRun testRun;	
	private AwsLogsHelper awsLogsHelper;
	@SuppressWarnings("unused")
	private List<LogGroup> currentGroups;
	private List<OutputLogEvent> currentLogs;
	private List<LogStream> currentLogStreams;
	private LogStream currentLogStream;
	private String currentLogStreamName;
	private List<OutputLogEvent> currentEventLogs;
	private OutputLogEvent currentEventLog;
	
	@SuppressWarnings("unused")
	private OutputLogEvent currentLog;
		
	
	
	public AwsLogsSteps(TestRun testRun) {
		this.testRun = testRun;
		this.awsLogsHelper = createAwsLogsHelper();
	}
	
	
	@When("the user lists the log groups")
	public void the_user_lists_the_log_groups() {
	    awsLogsHelper.listLogGroups();
	}
	
	@When("the user retrieves the log groups")
	public void the_user_retrieves_the_log_groups() {
	    this.currentGroups = awsLogsHelper.getLogGroups();
	}

	@When("the user retrieves the latest log from the log group named {string}")
	public void the_user_retrieves_the_latest_log_from_the_log_group_named(String groupName) {		
		List<LogStream> streams = awsLogsHelper.getLogStreams(groupName); 
		LogStream latestStream = streams.get(streams.size()-1);
		this.currentLogStream = latestStream;
		String logStreamName = latestStream.getLogStreamName();
		this.currentLogStreamName = logStreamName;
						
		GetLogEventsRequest request = new GetLogEventsRequest().withLogGroupName(groupName).withLogStreamName(logStreamName);
		GetLogEventsResult logEvents = awsLogsHelper.getLogEvents(request);
		List<OutputLogEvent> outputLogEvents = logEvents.getEvents();
		this.currentEventLogs = outputLogEvents;
		
		OutputLogEvent latestLog = outputLogEvents.get(outputLogEvents.size()-1);
		System.out.printf("Latest Event Log for the Group '%s':\n", groupName);
		System.out.println(latestLog.getMessage());
	}
	
	@When("the user retrieves the latest log from the function named {string}")
	public void the_user_retrieves_the_latest_log_from_the_function_named(String functionName) {	
		String prefix = "/aws/lambda/";
		String groupName = prefix + functionName;
		List<LogStream> streams = awsLogsHelper.getLogStreams(groupName); 
		LogStream latestStream = streams.get(streams.size()-1);
		this.currentLogStream = latestStream;
		String logStreamName = latestStream.getLogStreamName();
		this.currentLogStreamName = logStreamName;
						
		GetLogEventsRequest request = new GetLogEventsRequest().withLogGroupName(groupName).withLogStreamName(logStreamName);
		GetLogEventsResult logEvents = awsLogsHelper.getLogEvents(request);
		List<OutputLogEvent> outputLogEvents = logEvents.getEvents();
		this.currentEventLogs = outputLogEvents;
		
		OutputLogEvent latestLog = outputLogEvents.get(outputLogEvents.size()-1);
		System.out.printf("Latest Event Log for the function '%s':\n", functionName);
		System.out.println(latestLog.getMessage());
	}

	@When("the user retrieves the latest log from the log group named {string} and the stream named {string}")
	public void the_user_retrieves_the_latest_log_from_the_log_group_named_and_the_stream_named(String groupName, String streamName) {

		GetLogEventsRequest request = new GetLogEventsRequest().withLogGroupName(groupName).withLogStreamName(streamName);
		GetLogEventsResult logEvents = awsLogsHelper.getLogEvents(request);
		List<OutputLogEvent> outputLogEvents = logEvents.getEvents();
		this.currentEventLogs = outputLogEvents;

		OutputLogEvent latestLog = outputLogEvents.get(outputLogEvents.size()-1);
		System.out.printf("Latest Event Log for the Group '%s' and the stream '%s':\n", groupName, streamName);
		System.out.println(latestLog.getMessage());						
	}
	
	
	@When("the user retrieves the Log Streams for the Log Group named {string}")
	public void the_user_retrieves_the_Log_Streams_for_the_Log_Group_named(String logGroupName) {
		this.currentLogStreams = awsLogsHelper.getLogStreams(logGroupName);
	    
	}
	
	@When("the user retrieves the latest Log Stream for the Log Group named {string}")
	public void the_user_retrieves_the_latest_Log_Stream_for_the_Log_Group_named(String logGroupName) {
		List<LogStream> streams = awsLogsHelper.getLogStreams(logGroupName); 
		LogStream latestStream = streams.get(streams.size()-1);
		this.currentLogStream = latestStream;
		String logStreamName = latestStream.getLogStreamName();
		this.currentLogStreamName = logStreamName;
		System.out.println("Found Logstream: " + logStreamName);	
	}
	
	
	@When("the user retrieves the latest event logs for the Log Group named {string}")
	public void the_user_retrieves_the_latest_event_logs_for_the_Log_Group_named(String logGroupName) {
		List<LogStream> streams = awsLogsHelper.getLogStreams(logGroupName); 
		LogStream latestStream = streams.get(streams.size()-1);
		this.currentLogStream = latestStream;
		String logStreamName = latestStream.getLogStreamName();
		this.currentLogStreamName = logStreamName;
		System.out.println("Found Logstream: " + logStreamName);
				
		GetLogEventsRequest request = new GetLogEventsRequest().withLogGroupName(logGroupName).withLogStreamName(logStreamName);
		GetLogEventsResult logEvents = awsLogsHelper.getLogEvents(request);
		List<OutputLogEvent> outputLogEvents = logEvents.getEvents();
		this.currentEventLogs = outputLogEvents;
		
		System.out.println("Output Log Events:");
		for(OutputLogEvent event:outputLogEvents) {
			String message = event.getMessage();
			System.out.println("Message: " + message);
		}		
	}
	
	
	@When("the user retrieves the latest event log for the Log Group named {string}")
	public void the_user_retrieves_the_latest_event_log_for_the_Log_Group_named(String logGroupName) {
		List<LogStream> streams = awsLogsHelper.getLogStreams(logGroupName); 
		LogStream latestStream = streams.get(streams.size()-1);
		this.currentLogStream = latestStream;
		String logStreamName = latestStream.getLogStreamName();
		this.currentLogStreamName = logStreamName;
		System.out.println("Found Logstream: " + logStreamName);
				
		GetLogEventsRequest request = new GetLogEventsRequest().withLogGroupName(logGroupName).withLogStreamName(logStreamName);
		GetLogEventsResult logEvents = awsLogsHelper.getLogEvents(request);
		List<OutputLogEvent> outputLogEvents = logEvents.getEvents();
		this.currentEventLogs = outputLogEvents;
		
//		System.out.println("Output Log Events:");
//		for(OutputLogEvent event:outputLogEvents) {
//			String message = event.getMessage();
//			System.out.println("Message: " + message);
//		}		
		
		OutputLogEvent latestLog = outputLogEvents.get(outputLogEvents.size()-1);
		this.currentEventLog = latestLog;
		System.out.printf("Latest Event Log for the Group '%s' and the stream '%s':\n", logGroupName, logStreamName);
		System.out.println(latestLog.getMessage());
		
		
	}

	@When("the user retrieves the log stream\\(s) as described below:")
	public void the_user_retrieves_the_log_stream_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);
		System.out.println("Log Streams:");
		for (int i = 1; i < table.size(); i++) {
			String groupName = table.get(i).get(0);
			String logStreamName = table.get(i).get(1);
			this.awsLogsHelper.getLogs(groupName, logStreamName);					
		}
	}

	@When("the user retrieves the log event\\(s) as described below:")
	public void the_user_retrieves_the_log_event_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);
		System.out.println("Log Events:");
		for (int i = 1; i < table.size(); i++) {
			String groupName = table.get(i).get(0);
			GetLogEventsRequest request = new GetLogEventsRequest().withLogGroupName(groupName);			        
			GetLogEventsResult events = awsLogsHelper.getLogEvents(request);
			this.currentLogs = events.getEvents();

			events.getEvents().forEach(outputLogEvent -> {
			    System.out.println(outputLogEvent.getMessage());
			});			
		}
	}
	
	
	//#######################################################################################################################################
	//#######################################################################################################################################	
	private AwsLogsHelper createAwsLogsHelper() {
		AWSCredentials creds = CredentialsHelper.getCredentialsFromDefaultProvider();
		String region = testRun.getTestData().get(Constants.AWS_REGION_KEY).toString();
		AwsLogsHelper helper = new AwsLogsHelper(creds, region);
		return helper;
	}


}
