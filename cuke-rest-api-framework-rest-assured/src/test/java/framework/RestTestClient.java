package framework;

import java.util.HashMap;

import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONValue;

public class RestTestClient {

	// --------------------------------------------------------------
	// HTTP GET
	// --------------------------------------------------------------
	public static RequestInfo httpGet(RequestInfo requestInfo) {
		RestAssured.baseURI = requestInfo.getUri();
		RequestSpecification httpRequest = RestAssured.given();

		HashMap<String, String> parametersMap = requestInfo.getParametersMap();
		if (!parametersMap.isEmpty()) {
			httpRequest.given().params(parametersMap);
		}

		// save the values
		String resource = requestInfo.getResource();
		Response response = httpRequest.request(Method.GET, resource);
		String responseBody = response.getBody().asString();
		String statusCode = String.valueOf(response.getStatusCode());
		String statusMessage = response.getStatusLine();
		requestInfo.setResponse(response);
		requestInfo.setResponseBody(responseBody);
		requestInfo.setStatusCode(statusCode);
		requestInfo.setStatusLine(statusMessage);

		// return the request info object
		return requestInfo;
	}

	// --------------------------------------------------------------
	// HTTP POST
	// --------------------------------------------------------------
	public static RequestInfo httpPost(RequestInfo requestInfo, String message) {
		RestAssured.baseURI = requestInfo.getUri();
		RequestSpecification httpRequest = RestAssured.given();

		// add headers
		httpRequest.header("Content-Type", "application/json");

		// add parameters
		HashMap<String, String> parametersMap = requestInfo.getParametersMap();
		if (!parametersMap.isEmpty()) {
			httpRequest.given().params(parametersMap);
		}

		// add the message
		httpRequest.body(JSONValue.parse(message));

		// get the resource
		String resource = requestInfo.getResource();

		// send the request and capture the response
		Response response = httpRequest.post(resource);

		// save the values
		String responseBody = response.getBody().asString();
		String statusCode = String.valueOf(response.getStatusCode());
		String statusMessage = response.getStatusLine();
		requestInfo.setResponse(response);
		requestInfo.setResponseBody(responseBody);
		requestInfo.setStatusCode(statusCode);
		requestInfo.setStatusLine(statusMessage);

		// return the request info object
		return requestInfo;
	}

	// --------------------------------------------------------------
	// HTTP PUT
	// --------------------------------------------------------------
	public static RequestInfo httpPut(RequestInfo requestInfo, String message) {
		RestAssured.baseURI = requestInfo.getUri();
		RequestSpecification httpRequest = RestAssured.given();

		// add headers
		httpRequest.header("Content-Type", "application/json");

		// add parameters
		HashMap<String, String> parametersMap = requestInfo.getParametersMap();
		if (!parametersMap.isEmpty()) {
			httpRequest.given().params(parametersMap);
		}

		// add the message
		httpRequest.body(JSONValue.parse(message));

		// get the resource
		String resource = requestInfo.getResource();

		// send the request and capture the response
		Response response = httpRequest.put(resource);

		// save the values
		String responseBody = response.getBody().asString();
		String statusCode = String.valueOf(response.getStatusCode());
		String statusMessage = response.getStatusLine();
		requestInfo.setResponse(response);
		requestInfo.setResponseBody(responseBody);
		requestInfo.setStatusCode(statusCode);
		requestInfo.setStatusLine(statusMessage);

		// return the request info object
		return requestInfo;
	}

	// --------------------------------------------------------------
	// HTTP DELETE
	// --------------------------------------------------------------
	public static RequestInfo httpDelete(RequestInfo requestInfo) {
		RestAssured.baseURI = requestInfo.getUri();
		RequestSpecification httpRequest = RestAssured.given();

		// add headers
		httpRequest.header("Content-Type", "application/json");

		// get the resource
		String resource = requestInfo.getResource();

		// send the request and capture the response
		Response response = httpRequest.delete(resource);

		// save the values
		String responseBody = response.getBody().asString();
		String statusCode = String.valueOf(response.getStatusCode());
		String statusMessage = response.getStatusLine();
		requestInfo.setResponse(response);
		requestInfo.setResponseBody(responseBody);
		requestInfo.setStatusCode(statusCode);
		requestInfo.setStatusLine(statusMessage);

		// return the request info object
		return requestInfo;
	}

	
}
