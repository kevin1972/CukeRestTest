package steps;

import java.util.HashMap;
import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.sqs.model.ListQueuesResult;
import com.amazonaws.services.sqs.model.Message;
import com.colbert.kevin.aws.helpers.CredentialsHelper;
import com.colbert.kevin.aws.helpers.SqsHelper;

import cucumber.api.java.en.When;
import framework.Constants;
import framework.TestRun;

public class SqsSteps {
	private TestRun testRun;
	// private String region;
	private SqsHelper sqsHelper;
	private String currentQueueName;
	private String currentQueueUrl;
	private Message currentMessage;	
	private String currentRegion;
	final int DEFAULT_DELAY_IN_SECONDS = 1;
	final String DEFAULT_MESSAGE_RETENTION_PERIOD = "10";
	private String currentMessageBody;
	
	@SuppressWarnings("unused")
	private ListQueuesResult currentQueueList;	
	@SuppressWarnings("unused")
	private List<Message> currentMessages;
	@SuppressWarnings("unused")
	private String currentMessageId;

	public SqsSteps(TestRun testRun) {
		this.testRun = testRun;
		this.sqsHelper = createSqsHelper();
	}

	// ###########################################################################
	// Queue Methods
	// ###########################################################################
	@When("the user creates a queue named {string} in the given region")
	public void the_user_creates_a_queue_named_in_the_given_region(String queueName) throws Exception {
		// String region = getRegion();
		sqsHelper.createQueue(queueName, String.valueOf(DEFAULT_DELAY_IN_SECONDS), DEFAULT_MESSAGE_RETENTION_PERIOD);
	}

	@When("the user gets a list of queues in the given region")
	public void the_user_gets_a_list_of_queues_in_the_given_region() {
		this.currentQueueList = sqsHelper.listQueues();
	}

	@When("the user gets the URL for the given queue")
	public void the_user_gets_the_URL_for_the_given_queue() throws Exception {
		String queueName = getQueueName();
		this.currentQueueUrl = sqsHelper.getQueueUrl(queueName);
	}

	@When("the user gets the URL for the queue named {string}")
	public void the_user_gets_the_URL_for_the_queue_named(String queueName) {
		this.currentQueueUrl = sqsHelper.getQueueUrl(queueName);
	}

	@When("the user deletes the given queue")
	public void the_user_deletes_the_given_queue() throws Exception {
		String queueName = getQueueName();
		String queueUrl = sqsHelper.getQueueUrl(queueName);
		sqsHelper.deleteQueue(queueUrl);
	}

	@When("the user deletes the queue named {string}")
	public void the_user_deletes_the_queue_named(String queueName) {
		String queueUrl = sqsHelper.getQueueUrl(queueName);
		sqsHelper.deleteQueue(queueUrl);
	}

	// ###########################################################################
	// Send Message Methods
	// ###########################################################################
	@When("the user sends the message with the given information")
	public void the_user_sends_the_message_with_the_given_information() throws Exception {
		String messageBody = getMessageBody();
		String queueUrl = getQueueUrl();
		sqsHelper.sendMessage(queueUrl, messageBody, DEFAULT_DELAY_IN_SECONDS);
	}

	@When("the user sends the message to the queue {string} with a {string} second delay")
	public void the_user_sends_the_message_to_the_queue_with_a_second_delay(String queueName, String delayInSeconds)
			throws Exception {
		String messageBody = getMessageBody();
		String queueUrl = sqsHelper.getQueueUrl(queueName);
		sqsHelper.sendMessage(queueUrl, messageBody, Integer.valueOf(delayInSeconds));
	}

	@When("the user sends the message {string} to the queue {string} with a {string} second delay")
	public void the_user_sends_the_message_to_the_queue_with_a_second_delay(String messageBody, String queueName,
			String delayInSeconds) {
		String queueUrl = sqsHelper.getQueueUrl(queueName);
		sqsHelper.sendMessage(queueUrl, messageBody, Integer.valueOf(delayInSeconds));
	}

	@When("the user sends a message\\(s) as described below:")
	public void the_user_sends_a_message_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);

		for (int i = 1; i < table.size(); i++) {
			String queueName = table.get(i).get(0);
			String messageBody = table.get(i).get(1);
			String delayInSeconds = table.get(i).get(2);
			String queueUrl = sqsHelper.getQueueUrl(queueName);
			sqsHelper.sendMessage(queueUrl, messageBody, Integer.valueOf(delayInSeconds));
		}
	}

	// ###########################################################################
	// Get Message Methods
	// ###########################################################################
	@When("the user gets the messages with the given information")
	public void the_user_gets_the_messages_with_the_given_information() throws Exception {
		Message message = getMessage();
		this.currentMessage = message;
	}

	@When("the user gets the messages from the queue {string} with a {string} second delay")
	public void the_user_gets_the_messages_from_the_queue_with_a_second_delay(String queueName, String delayInSeconds) throws Exception {
		String queueUrl = sqsHelper.getQueueUrl(queueName);		
		this.currentMessages = sqsHelper.getMessages(queueUrl);
	}

	@When("the user gets message\\(s) as described below:")
	public void the_user_gets_message_s_as_described_below(io.cucumber.datatable.DataTable dataTable) throws NumberFormatException, Exception {
		List<List<String>> table = dataTable.asLists(String.class);
		for (int i = 1; i < table.size(); i++) {
			String queueName = table.get(i).get(0);
			String messageId = table.get(i).get(1);			
			this.currentMessageId = messageId;
			this.currentMessage = findMessage(queueName, messageId);
		}
	}
	
	private Message findMessage(String queueName, String messageId) {
		Message found = null;
		String queueUrl = sqsHelper.getQueueUrl(queueName);
		List<Message> messages = sqsHelper.getMessages(queueUrl);		
		HashMap<String, Message> messageMap = new HashMap<String, Message>();
		for(Message message : messages) {
			messageMap.put(message.getMessageId(), message);
		}
		
		found = messageMap.get(messageId);
		return found;
		
	}

	@When("the user gets the message with the ID {string} from the queue {string} with a {string} second delay")
	public void the_user_gets_the_message_with_the_ID_from_the_queue_with_a_second_delay(String messageId, String queueName,
			String delayInSeconds) throws NumberFormatException, Exception {
		String queueUrl = sqsHelper.getQueueUrl(queueName);
		this.currentMessage = sqsHelper.getMessage(queueUrl, messageId, Integer.valueOf(delayInSeconds));
		this.currentMessageId = messageId;
	}

	@When("the user gets the message using the following criteria:")
	public void the_user_gets_the_message_using_the_following_criteria(io.cucumber.datatable.DataTable dataTable) throws NumberFormatException, Exception {
		List<List<String>> table = dataTable.asLists(String.class);
		for (int i = 1; i < table.size(); i++) {
			String queueName = table.get(i).get(0);
			String messageId = table.get(i).get(1);
			String delayInSeconds = table.get(i).get(2);
			String queueUrl = sqsHelper.getQueueUrl(queueName);
			this.currentMessage = sqsHelper.getMessage(queueUrl, messageId, Integer.valueOf(delayInSeconds));
			this.currentMessageId = messageId;
		}
	}

	
	// ###########################################################################
	// Delete Message Methods
	// ###########################################################################
	@When("the user deletes the messages with the given information")
	public void the_user_deletes_the_messages_with_the_given_information() throws Exception {
		
		Message message = null;
		if(this.currentMessage != null) {
			message = this.currentMessage;
		}else {
			throw new Exception("The variable 'currentMessage' is undefined null");
		}
		
			
		if(!this.currentQueueUrl.isEmpty()) {
			String queueUrl = this.currentQueueUrl;
			sqsHelper.deleteMessage(queueUrl, message);
		}else {
			throw new Exception("The variable 'currentQueueUrl' is undefined or empty.");
		}
		
		
	}

	@When("the user deletes the messages from the queue {string} with a {string} second delay")
	public void the_user_deletes_the_messages_from_the_queue_with_a_second_delay(String string, String string2) {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@When("the user deletes message\\(s) as described below:")
	public void the_user_deletes_message_s_as_described_below(io.cucumber.datatable.DataTable dataTable) throws Exception {
		List<List<String>> table = dataTable.asLists(String.class);
		for (int i = 1; i < table.size(); i++) {
			String queueName = table.get(i).get(0);
			String messageId = table.get(i).get(1);			
			String queueUrl = sqsHelper.getQueueUrl(queueName);
			Message m = sqsHelper.getMessage(queueUrl, messageId, 1);
			sqsHelper.deleteMessage(queueUrl, m);
		}
	}

	@When("the user deletes the message with the given information")
	public void the_user_deletes_the_message_with_the_given_information() {
		String queueUrl = this.currentQueueUrl;
		Message m = this.currentMessage;
		sqsHelper.deleteMessage(queueUrl, m);
	}

	@When("the user deletes the message with the ID {string} from the queue {string} with a {string} second delay")
	public void the_user_deletes_the_message_with_the_ID_from_the_queue_with_a_second_delay(String messageId,
			String queueName, String delayInSeconds) throws NumberFormatException, Exception {
		String queueUrl = sqsHelper.getQueueUrl(queueName);		
		Message m = sqsHelper.getMessage(queueUrl, messageId, Integer.valueOf(delayInSeconds));
		sqsHelper.deleteMessage(queueUrl, m);
	}

	@When("the user deletes the message\\(s) using the following criteria:")
	public void the_user_deletes_the_message_s_using_the_following_criteria(io.cucumber.datatable.DataTable dataTable) throws Exception {
		List<List<String>> table = dataTable.asLists(String.class);
		for (int i = 1; i < table.size(); i++) {
			String queueName = table.get(i).get(0);
			String messageId = table.get(i).get(1);			
			String queueUrl = sqsHelper.getQueueUrl(queueName);
			Message m = sqsHelper.getMessage(queueUrl, messageId, 1);
			sqsHelper.deleteMessage(queueUrl, m);
		}
	}

	// =======================================================================================================================================================
	// Helper Methods
	// =======================================================================================================================================================
	private SqsHelper createSqsHelper() {
		AWSCredentials creds = CredentialsHelper.getCredentialsFromDefaultProvider();
		// HashMap<String, Object> testData = testRun.getTestData();
		String region = testRun.getTestData().get(Constants.AWS_REGION_KEY).toString();
		SqsHelper sqs = new SqsHelper(creds, region);
		return sqs;
	}

	@SuppressWarnings("unused")
	private String getRegion() throws Exception {
		String region = "";
		if (testRun.getTestData().containsKey(Constants.AWS_REGION_KEY)) {
			region = testRun.getTestData().get(Constants.AWS_REGION_KEY).toString();
		} else if (this.currentMessage != null) {
			region = this.currentRegion;
		} else {
			String errMsg = "No '" + Constants.AWS_REGION_KEY
					+ "' has been set and the currentRegion variable is null.";
			throw new Exception(errMsg);
		}
		return region;
	}

	private String getQueueName() throws Exception {
		String queueName = "";
		if (testRun.getTestData().containsKey(Constants.SQS_QUEUE_NAME_KEY)) {
			queueName = testRun.getTestData().get(Constants.SQS_QUEUE_NAME_KEY).toString();
			this.currentQueueName = queueName;
		} else if (this.currentQueueName != null) {
			queueName = this.currentQueueName;
		} else {
			String errMsg = "No '" + Constants.SQS_QUEUE_NAME_KEY
					+ "' has been set and the currentQueueName variable is null.";
			throw new Exception(errMsg);
		}
		return queueName;
	}

	private String getQueueUrl() throws Exception {
		String queueUrl = "";
		if (testRun.getTestData().containsKey(Constants.SQS_QUEUE_URL_KEY)) {
			queueUrl = testRun.getTestData().get(Constants.SQS_QUEUE_URL_KEY).toString();
			this.currentQueueUrl = queueUrl;
		} else if (this.currentQueueUrl != null) {
			queueUrl = this.currentQueueUrl;
		} else {
			String errMsg = "No '" + Constants.SQS_QUEUE_URL_KEY
					+ "' has been set and the currentQueueUrl variable is null.";
			throw new Exception(errMsg);
		}
		return queueUrl;
	}

	private String getMessageBody() throws Exception {
		String messageBody = "";
		if (testRun.getTestData().containsKey(Constants.SQS_MESSAGE_BODY_KEY)) {
			messageBody = testRun.getTestData().get(Constants.SQS_MESSAGE_BODY_KEY).toString();
			this.currentMessageBody = messageBody;
		} else if (this.currentMessageBody != null) {
			messageBody = this.currentMessageBody;
		} else {
			String errMsg = "No '" + Constants.SQS_MESSAGE_BODY_KEY
					+ "' has been set and the currentMessageBody variable is null.";
			throw new Exception(errMsg);
		}
		return messageBody;
	}

	private Message getMessage() throws Exception {
		Message message = null;
		if (testRun.getTestData().containsKey(Constants.SQS_MESSAGE_KEY)) {
			message = (Message) testRun.getTestData().get(Constants.SQS_MESSAGE_KEY);
			this.currentMessage = message;
		} else if (this.currentMessage != null) {
			message = this.currentMessage;
		} else {
			String errMsg = "No '" + Constants.SQS_MESSAGE_KEY
					+ "' has been set and the currentMessage variable is null.";
			throw new Exception(errMsg);
		}
		return message;
	}

}
