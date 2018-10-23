package framework;

public class HttpMethodRunner {
	// -----------------------------------------------------------
	// -----------------------------------------------------------
	public static RequestInfo doGet(TestRun testRun) {
		RequestInfo requestInfo = new RequestInfo(testRun);
		requestInfo = RestTestClient.httpGet(requestInfo);
		testRun.updateRequestHistory(requestInfo);
		return requestInfo;
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	public static RequestInfo doPost(TestRun testRun) {

		// get the request object
		RequestInfo requestInfo = new RequestInfo(testRun);

		// check for message data
		String messageData = "";
		if (testRun.getTestData().containsKey(Constants.MESSAGE_DATA_KEY)) {
			messageData = (String) testRun.getTestData().get(Constants.MESSAGE_DATA_KEY);
		}

		// do the post
		requestInfo = RestTestClient.httpPost(requestInfo, messageData);

		// save values from response
		testRun.updateRequestHistory(requestInfo);

		// return the request object
		return requestInfo;
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	public static RequestInfo doPut(TestRun testRun) {

		// get the request object
		RequestInfo requestInfo = new RequestInfo(testRun);

		// check for message data
		String messageData = "";
		if (testRun.getTestData().containsKey(Constants.MESSAGE_DATA_KEY)) {
			messageData = (String) testRun.getTestData().get(Constants.MESSAGE_DATA_KEY);
		}

		// do the put
		requestInfo = RestTestClient.httpPut(requestInfo, messageData);

		// save values from response
		testRun.updateRequestHistory(requestInfo);

		// return the request object
		return requestInfo;
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	public static RequestInfo doDelete(TestRun testRun) {

		// get the request object
		RequestInfo requestInfo = new RequestInfo(testRun);

		// do the delete
		requestInfo = RestTestClient.httpDelete(requestInfo);

		// save values from response
		testRun.updateRequestHistory(requestInfo);

		// return the request object
		return requestInfo;
	}

}
