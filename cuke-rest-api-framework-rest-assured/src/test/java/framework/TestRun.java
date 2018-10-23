package framework;

import java.util.ArrayList;
import java.util.HashMap;

public class TestRun {
	// ----------------------------------
	// MEMBERS
	// ----------------------------------
	private RequestInfo lastRequest;
	private HashMap<String, Object> testData;
	private ArrayList<RequestInfo> requestHistory; // used to keep a list of requests/responses created during this test

	// ----------------------------------
	// CONSTRUCTOR
	// ----------------------------------
	public TestRun() {
		this.testData = new HashMap<String, Object>();
		this.requestHistory = new ArrayList<RequestInfo>();
	}

	// ----------------------------------
	// GET-SET TEST DATA
	// ----------------------------------
	public HashMap<String, Object> getTestData() {
		if (this.testData == null) {
			this.testData = new HashMap<String, Object>();
		}
		return this.testData;
	}

	public void setTestData(HashMap<String, Object> testData) {
		this.testData = testData;
	}

	// ----------------------------------
	// SET LAST RESULTS
	// ----------------------------------
	public void updateRequestHistory(RequestInfo requestInfo) {
		this.getTestData().put(Constants.HTTP_RESPONSE_KEY, requestInfo.getResponseBody());
		this.getTestData().put(Constants.HTTP_RESPONSE_STATUS_CODE_KEY, requestInfo.getStatusCode());
		this.getTestData().put(Constants.HTTP_RESPONSE_STATUS_LINE_KEY, requestInfo.getStatusLine());
		this.setLastRequest(requestInfo);
		this.getRequesHistory().add(requestInfo);
	}

	// ----------------------------------
	// ACCESSORS
	// ----------------------------------
	public RequestInfo getLastRequest() {
		return lastRequest;
	}

	public void setLastRequest(RequestInfo lastRequestResponse) {
		this.lastRequest = lastRequestResponse;
	}

	public ArrayList<RequestInfo> getRequesHistory() {
		return requestHistory;
	}

	public void setRequestHistory(ArrayList<RequestInfo> reqResHistory) {
		this.requestHistory = reqResHistory;
	}

}
