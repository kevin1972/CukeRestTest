package com.colbert.kevin.aws.helpers;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Logger;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.dynamodbv2.model.StreamRecord;
import com.amazonaws.services.lambda.AWSLambda;
import com.amazonaws.services.lambda.AWSLambdaClientBuilder;
import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.amazonaws.services.lambda.model.InvokeRequest;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.amazonaws.services.lambda.model.ListFunctionsRequest;
import com.amazonaws.services.lambda.model.ListFunctionsResult;
import com.amazonaws.services.lambda.runtime.events.DynamodbEvent;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;
import com.amazonaws.util.IOUtils;

public class LambdaHelper {
	private AWSCredentials credentials;
	private AWSLambda lambdaClient;
	private InvokeRequest lastRequest;
	private InvokeResult lastResult;
	private String region;

	// ----------------------------------------------------------------
	// Constructor
	// ----------------------------------------------------------------
	public LambdaHelper(AWSCredentials credentials, String region) {
		this.credentials = credentials;
		this.region = region;
		try {
			this.lambdaClient = createLambdaClient(this.region);
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
	}

	// ----------------------------------------------------------------
	// Create Lambda Client
	// ----------------------------------------------------------------
	private AWSLambda createLambdaClient(String region) {
		AWSLambda lambda = null;
		try {
			lambda = AWSLambdaClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(this.credentials)).withRegion(region).build();
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
		return lambda;
	}

	// ----------------------------------------------------------------
	// Run Lambda
	// ----------------------------------------------------------------
	public InvokeResult runLambda(String functionName) {

		InvokeRequest request = null;
		InvokeResult result = null;

		request = new InvokeRequest().withFunctionName(functionName).withPayload("{}");

		result = this.lambdaClient.invoke(request);

		this.setLastRequest(request);
		this.setLastResult(result);
		String logResult = result.getLogResult();
		return result;
	}

	public InvokeResult runLambda(String functionName, String payload) {
//		InvokeRequest request = new InvokeRequest().withFunctionName("MyFunction").withInvocationType("Event").withLogType("Tail").withClientContext("MyApp")
//		        .withPayload(ByteBuffer.wrap("fileb://file-path/input.json".getBytes())).withQualifier("1");

		InvokeRequest request = null;
		InvokeResult result = null;
		if(!(payload.startsWith("\"") && payload.endsWith("\""))) {
			payload = "\"" + payload + "\"";			
		}
		request = new InvokeRequest().withFunctionName(functionName).withPayload(payload);
		result = this.lambdaClient.invoke(request);
		this.setLastRequest(request);
		this.setLastResult(result);
		return result;
	}
	
	
	public InvokeResult runSqsEventLambda(String functionName, SQSEvent event) {
		InvokeRequest request = null;
		InvokeResult result = null;
		String eventBody = event.getRecords().get(0).getBody();
		request = new InvokeRequest().withFunctionName(functionName).withPayload(eventBody);
		result = this.lambdaClient.invoke(request);
		this.setLastRequest(request);
		this.setLastResult(result);
		return result;
	}
	
	public InvokeResult runSnsEventLambda(String functionName, SNSEvent event) {
		InvokeRequest request = null;
		InvokeResult result = null;
		String snsMsg = event.getRecords().get(0).getSNS().getMessage();
		request = new InvokeRequest().withFunctionName(functionName).withPayload(snsMsg);
		result = this.lambdaClient.invoke(request);
		this.setLastRequest(request);
		this.setLastResult(result);
		return result;
	}
	
	
	public InvokeResult runS3EventLambda(String functionName, S3Event event) {
		InvokeRequest request = null;
		InvokeResult result = null;
		String key = event.getRecords().get(0).getS3().getObject().getKey();
		request = new InvokeRequest().withFunctionName(functionName).withPayload(key);
		result = this.lambdaClient.invoke(request);
		this.setLastRequest(request);
		this.setLastResult(result);
		return result;
	}
	
	
	public InvokeResult runDynamoDbEventLambda(String functionName, DynamodbEvent event) {
		InvokeRequest request = null;
		InvokeResult result = null;
		String dynamoDB = event.getRecords().get(0).getDynamodb().toString();
		request = new InvokeRequest().withFunctionName(functionName).withPayload(dynamoDB);
		result = this.lambdaClient.invoke(request);
		this.setLastRequest(request);
		this.setLastResult(result);
		return result;
	}
	
	

	// ----------------------------------------------------------------
	// Get Output From Invoke Result
	// ----------------------------------------------------------------
	public String getLambdaOutput(InvokeResult invokeResult) {
		String result = "";
		final Logger logger = Logger.getLogger(this.getClass().getName());
		ByteBuffer byteBuf = invokeResult.getPayload();
		if (byteBuf != null) {
			result = StandardCharsets.UTF_8.decode(byteBuf).toString();
		}
		return result;
	}

	// ----------------------------------------------------------------
	// List Functions
	// ----------------------------------------------------------------
	public List<FunctionConfiguration> listFunctions() {
		ListFunctionsResult result = null;
		ListFunctionsRequest request = new ListFunctionsRequest();
		result = this.lambdaClient.listFunctions(request);
		List<FunctionConfiguration> functions = result.getFunctions();
		System.out.print("Functions List:");
		for (FunctionConfiguration func : functions) {
			System.out.println(func.getFunctionName());
		}
		return functions;
	}

	public List<FunctionConfiguration> listFunctions(String marker, int maxItems) {
		ListFunctionsResult result = null;
		ListFunctionsRequest request = new ListFunctionsRequest().withMarker(marker).withMaxItems(maxItems);
		result = this.lambdaClient.listFunctions(request);
		List<FunctionConfiguration> functions = result.getFunctions();
		return functions;
	}

	// ----------------------------------------------------------------
	// ACCESSORS
	// ----------------------------------------------------------------
	public AWSLambda getLambdaClient() {
		return this.lambdaClient;
	}

	public InvokeRequest getLastRequest() {
		return lastRequest;
	}

	public void setLastRequest(InvokeRequest lastRequest) {
		this.lastRequest = lastRequest;
	}

	public InvokeResult getLastResult() {
		return lastResult;
	}

	public void setLastResult(InvokeResult lastResult) {
		this.lastResult = lastResult;
	}

}
