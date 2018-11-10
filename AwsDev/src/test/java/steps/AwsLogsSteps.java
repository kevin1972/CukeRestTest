package steps;

import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.logs.model.GetLogEventsRequest;
import com.amazonaws.services.logs.model.GetLogEventsResult;
import com.amazonaws.services.logs.model.LogGroup;
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
	@SuppressWarnings("unused")
	private OutputLogEvent currentLog;
		
	
	
	public AwsLogsSteps(TestRun testRun) {
		this.testRun = testRun;
		this.awsLogsHelper = createAwsLogsHelper();
	}
	
	@When("the user retrieves the log groups")
	public void the_user_retrieves_the_log_groups() {
	    this.currentGroups = awsLogsHelper.getLogGroups();
	}

	@When("the user retrieves the latest log from the log group named {string}")
	public void the_user_retrieves_the_latest_log_from_the_log_group_named(String groupName) {		
		this.currentLogs = awsLogsHelper.getLogs(groupName);
		this.currentLog = this.currentLogs.get(0);

	}

	@When("the user retrieves the latest log from the log group named {string} and the stream named {string}")
	public void the_user_retrieves_the_latest_log_from_the_log_group_named_and_the_stream_named(String groupName, String streamName) {
		this.currentLogs = awsLogsHelper.getLogs(groupName, streamName);
		this.currentLog = this.currentLogs.get(0);
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
