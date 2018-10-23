package framework;

import java.util.HashMap;

import io.restassured.response.Response;

public class RequestInfo {

	// ----------------------------------
	// MEMBERS
	// ----------------------------------
	private String uri = "";
	private String resource = "";
	private String method = "";
	private HashMap<String, String> parametersMap;
	private String username = "";
	private String password = "";
	private Response response;
	private String statusCode = "";
	private String statusLine = "";
	private String content = "";
	private String responseBody = "";
	private String data = "";

	// ----------------------------------
	// CONSTRUCTORS
	// ----------------------------------
	public RequestInfo() {
		// create the parameters map
		this.parametersMap = new HashMap<String, String>();
	}

	@SuppressWarnings("unchecked")
	public RequestInfo(TestRun testRun) {

		// create the parameters map
		this.parametersMap = new HashMap<String, String>();

		// get the base uri
		if (testRun.getTestData().containsKey(Constants.BASE_URI_KEY)) {
			this.setUri(testRun.getTestData().get(Constants.BASE_URI_KEY).toString());
		}

		// get the resource
		if (testRun.getTestData().containsKey(Constants.RESOURCE_KEY)) {
			this.setResource(testRun.getTestData().get(Constants.RESOURCE_KEY).toString());
		}

		// get the method
		String method = "";
		if (testRun.getTestData().containsKey(Constants.METHOD_KEY)) {
			method = (String) testRun.getTestData().get(Constants.METHOD_KEY);
			this.setMethod(method);
		}

		// get the parameters
		HashMap<String, String> parameters = null;
		if (testRun.getTestData().containsKey(Constants.PARAMETERS_KEY)) {
			parameters = (HashMap<String, String>) testRun.getTestData().get(Constants.PARAMETERS_KEY);
			this.setParametersMap(parameters);
		}
		
		//get the username
		if (testRun.getTestData().containsKey(Constants.USERNAME_KEY)) {
			username = (String) testRun.getTestData().get(Constants.USERNAME_KEY);
			this.setUserName(username);
		}

		//get the password
		if (testRun.getTestData().containsKey(Constants.PASSWORD_KEY)) {
			password = (String) testRun.getTestData().get(Constants.PASSWORD_KEY);
			this.setPassword(password);
		}
				
		//get the message/payload
		if (testRun.getTestData().containsKey(Constants.MESSAGE_DATA_KEY)) {
			String messageData = (String) testRun.getTestData().get(Constants.MESSAGE_DATA_KEY);
			this.setData(messageData);
		}
	}

	//-----------------------------------------------------------
	// createReqResObject
	//-----------------------------------------------------------
//	@SuppressWarnings("unchecked")
//	public static RequestInfo createRequestInfoObject(TestRun testRun) {
//		// create the dto object
//		RequestInfo reqResInfo = new RequestInfo();
//
//		// get the base uri
//		if (testRun.getTestData().containsKey(Constants.BASE_URI_KEY)) {
//			reqResInfo.setUri(testRun.getTestData().get(Constants.BASE_URI_KEY).toString());
//		}
//
//		// get the resource
//		if (testRun.getTestData().containsKey(Constants.RESOURCE_KEY)) {
//			reqResInfo.setResource(testRun.getTestData().get(Constants.RESOURCE_KEY).toString());
//		}
//
//		// get the method
//		String method = "";
//		if (testRun.getTestData().containsKey(Constants.METHOD_KEY)) {
//			method = (String) testRun.getTestData().get(Constants.METHOD_KEY);
//			reqResInfo.setMethod(method);
//		}
//
//		// get the parameters
//		HashMap<String, String> parameters = null;
//		if (testRun.getTestData().containsKey(Constants.PARAMETERS_KEY)) {
//			parameters = (HashMap<String, String>) testRun.getTestData().get(Constants.PARAMETERS_KEY);
//			reqResInfo.setParametersMap(parameters);
//		}
//		return reqResInfo;
//	}
	

	// ----------------------------------
	// ACCESSORS
	// ----------------------------------
	public String getUri() {
		return uri;
	}

	public void setUri(String baseUri) {
		this.uri = baseUri;
	}

	public String getResource() {
		return resource;
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public HashMap<String, String> getParametersMap() {
		return this.parametersMap;
	}

	public void setParametersMap(HashMap<String, String> parametersMap) {
		this.parametersMap = parametersMap;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusLine() {
		return statusLine;
	}

	public void setStatusLine(String statusLine) {
		this.statusLine = statusLine;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String body) {
		this.responseBody = body;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
