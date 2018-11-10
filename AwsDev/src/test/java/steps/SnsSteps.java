package steps;

import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.colbert.kevin.aws.helpers.CredentialsHelper;
import com.colbert.kevin.aws.helpers.SnsHelper;

import cucumber.api.java.en.When;
import framework.Constants;
import framework.TestRun;

public class SnsSteps {
	private TestRun testRun;
	// private String region;
	private SnsHelper snsHelper;
	private String currentTopicArn;
	private String currentProtocol;
	private String currentEndpoint;
	private String currentMessage;

	public SnsSteps(TestRun testRun) {
		this.testRun = testRun;
		this.snsHelper = createSnsHelper();
	}
	
	@When("the user creates a topic named {string} in the given region")
	public void the_user_creates_a_topic_named_in_the_given_region(String topic) {
	    snsHelper.createTopic(topic);
	}

	@When("the user subscribes to a topic using the given information")
	public void the_user_subscribes_to_a_topic_using_the_given_information() throws Exception {
		String topicArn = getTopicArn();
		String protocol = getProtocol();
		String endpoint = getEndpoint();
		
		this.currentTopicArn = topicArn;
		this.currentProtocol = protocol;
		this.currentEndpoint = endpoint;
		
	    snsHelper.subscribeToTopic(topicArn, protocol, endpoint);
	}

	@When("the user subscribes to the topic arn {string} with the protocol {string} and the endpoint {string}")
	public void the_user_subscribes_to_the_topic_arn_with_the_protocol_and_the_endpoint(String topicArn, String protocol, String endpoint) {
	    // Write code here that turns the phrase above into concrete actions
		snsHelper.subscribeToTopic(topicArn, protocol, endpoint);
	}

	@When("the user subscribes to the topic\\(s) as described below:")
	public void the_user_subscribes_to_the_topic_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);

		for (int i = 1; i < table.size(); i++) {
			String topicArn = table.get(i).get(0);
			String protocol = table.get(i).get(1);
			String endpoint = table.get(i).get(2);
			
			this.currentTopicArn = topicArn;
			this.currentProtocol = protocol;
			this.currentEndpoint = endpoint;
			
			snsHelper.subscribeToTopic(topicArn, protocol, endpoint);
		}
	}

	@When("the user publishes to a topic using the given information")
	public void the_user_publishes_to_a_topic_using_the_given_information() throws Exception {
		String topicArn = getTopicArn();
		String msg = getMessage();
		
		this.currentTopicArn = topicArn;
		this.currentMessage = msg;
				
		snsHelper.publishToTopic(topicArn, msg);
	}

	@When("the user publishes the message {string}  to the topic arn {string}")
	public void the_user_publishes_the_message_to_the_topic_arn(String msg, String topicArn) {
		this.currentTopicArn = topicArn;
		this.currentMessage = msg;
		
		snsHelper.publishToTopic(topicArn, msg);
	}

	@When("the user publishes the message\\(s) to the topic\\(s) as described below:")
	public void the_user_publishes_the_message_s_to_the_topic_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);

		for (int i = 1; i < table.size(); i++) {
			String msg = table.get(i).get(0);
			String topicArn = table.get(i).get(1);
			
			this.currentTopicArn = topicArn;
			this.currentMessage = msg;
			
			snsHelper.publishToTopic(topicArn, msg);
		}
	}

	@When("the user deletes a topic using the given information")
	public void the_user_deletes_a_topic_using_the_given_information() throws Exception {		
		String topicArn = getTopicArn();		
		snsHelper.deleteTopic(topicArn);
	}

	@When("the user deletes the topic\\(s) as described below:")
	public void the_user_deletes_the_topic_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);

		for (int i = 1; i < table.size(); i++) {			
			String topicArn = table.get(i).get(0);					
			snsHelper.deleteTopic(topicArn);
		}
	}
	
	

	// =======================================================================================================================================================
	// =======================================================================================================================================================
	private SnsHelper createSnsHelper() {
		AWSCredentials creds = CredentialsHelper.getCredentialsFromDefaultProvider();
		String region = testRun.getTestData().get(Constants.AWS_REGION_KEY).toString();
		SnsHelper helper = new SnsHelper(creds, region);
		return helper;
	}
	
	
	private String getTopicArn() throws Exception {
		String topicArn = "";
		if (testRun.getTestData().containsKey(Constants.TOPIC_ARN_KEY)) {
			topicArn = testRun.getTestData().get(Constants.TOPIC_ARN_KEY).toString();
			this.currentTopicArn = topicArn;
		} else if (this.currentTopicArn != null) {
			topicArn = this.currentTopicArn;
		} else {
			String errMsg = "No '" + Constants.TOPIC_ARN_KEY + "' has been set and the currentTopicArn variable is null.";
			throw new Exception(errMsg);
		}
		return topicArn;
	}
	
	private String getProtocol() throws Exception {
		String protocol = "";
		if (testRun.getTestData().containsKey(Constants.SNS_PROTOCOL_KEY)) {
			protocol = testRun.getTestData().get(Constants.SNS_PROTOCOL_KEY).toString();
			this.currentProtocol = protocol;			
		} else if (this.currentProtocol != null) {
			protocol = this.currentProtocol;
		} else {
			String errMsg = "No '" + Constants.SNS_PROTOCOL_KEY + "' has been set and the currentProtocol variable is null.";
			throw new Exception(errMsg);
		}
		return protocol;
	}
	
	
	private String getEndpoint() throws Exception {
		String endpoint = "";
		if (testRun.getTestData().containsKey(Constants.SNS_ENDPOINT_KEY)) {
			endpoint = testRun.getTestData().get(Constants.SNS_ENDPOINT_KEY).toString();
			this.currentEndpoint = endpoint;			
		} else if (this.currentEndpoint != null) {
			endpoint = this.currentEndpoint;
		} else {
			String errMsg = "No '" + Constants.SNS_ENDPOINT_KEY + "' has been set and the currentEndpoint variable is null.";
			throw new Exception(errMsg);
		}
		return endpoint;
	}
	
	
	private String getMessage() throws Exception {
		String message = "";
		if (testRun.getTestData().containsKey(Constants.MESSAGE_KEY)) {
			message = testRun.getTestData().get(Constants.MESSAGE_KEY).toString();
			this.currentMessage = message;			
		} else if (this.currentMessage != null) {
			message = this.currentMessage;
		} else {
			String errMsg = "No '" + Constants.MESSAGE_KEY + "' has been set and the currentMessage variable is null.";
			throw new Exception(errMsg);
		}
		return message;
	}

}
