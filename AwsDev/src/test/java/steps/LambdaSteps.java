package steps;

import java.util.HashMap;
import java.util.List;

import org.junit.Assert;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.lambda.model.FunctionConfiguration;
import com.amazonaws.services.lambda.model.InvokeResult;
import com.colbert.kevin.aws.helpers.CredentialsHelper;
import com.colbert.kevin.aws.helpers.LambdaHelper;

import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import framework.Constants;
import framework.TestRun;

public class LambdaSteps {
	private TestRun testRun;	
	private LambdaHelper lambdaHelper;
	private String currentFunctionName;
	private String currentPayload;
	private InvokeResult lastResult;
	
	@SuppressWarnings("unused")
	private List<FunctionConfiguration> functions;
	
	
	public LambdaSteps(TestRun testRun) {
		this.testRun = testRun;
		this.lambdaHelper = createLambdaHelper();
	}
	
			
	@When("the user gets a list of functions")
	public void the_user_gets_a_list_of_functions() {
	    this.functions = lambdaHelper.listFunctions();
	}
	
	
	@Then("the following lambdas should be returned:")
	public void the_following_lambdas_should_be_returned(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);
		
		HashMap<String, FunctionConfiguration> functionMap = new HashMap<String, FunctionConfiguration>();
		
		for(FunctionConfiguration fc: this.functions) {
			functionMap.put(fc.getFunctionName(), fc);
		}

		for (int i = 1; i < table.size(); i++) {
			String functionName = table.get(i).get(0);
			
			if(functionMap.containsKey(functionName)) {
				System.out.printf("Found the expected function named '%s'\n", functionName);
				Assert.assertEquals(true, true);
			}else {
				System.out.printf("ERROR: Did NOT find the expected function named '%s'\n", functionName);
				Assert.assertEquals(true, false);
			}
		}
	}

	@When("the user invokes the Lambda function named {string}")
	public void the_user_invokes_the_Lambda_function_named(String functionName) throws Exception {
		String payload = getPayload();
		
		if(payload != null) {
			this.lastResult = this.lambdaHelper.runLambda(functionName, payload);
		}else {
			this.lastResult = this.lambdaHelper.runLambda(functionName);
		}
	    
	    String resultCode = this.lastResult.getStatusCode().toString();
	    System.out.println("Execution Result Code: " + resultCode);
	}
	
	@Then("the status code {string} should be returned")
	public void the_status_code_should_be_returned(String expectedStatusCode) {
	    Integer actualStatusCode = this.lastResult.getStatusCode();
	    
	    if(Integer.valueOf(expectedStatusCode).equals(actualStatusCode)) {
	    	System.out.printf("The expected status code '%s' matches the actual status code '%d'\n", expectedStatusCode, actualStatusCode);
			Assert.assertEquals(true, true);
	    }else {
	    	System.out.printf("ERROR: The expected status code '%s' did NOT match the actual status code '%d'\n", expectedStatusCode, actualStatusCode);
			Assert.assertEquals(true, false);
	    }	    	    
	}
	
	

	@When("the user invokes the Lambda function named {string} with the payload {string}")
	public void the_user_invokes_the_Lambda_function_named_with_the_payload(String functionName, String payload) {
		
	    //this.lambdaHelper.runLambda(functionName, payload);
		
		if(payload != null && !payload.isEmpty()) {			
			this.lastResult = this.lambdaHelper.runLambda(functionName, payload);
		}else {
			this.lastResult = this.lambdaHelper.runLambda(functionName);
		}
	}

	@When("the user invokes the Lambda function using the given data")
	public void the_user_invokes_the_Lambda_function_using_the_given_data() throws Exception {
	    String functionName = getFunctionName();
	    String payload = getPayload();
	    if(payload != null && !payload.isEmpty()) {
			this.lastResult = this.lambdaHelper.runLambda(functionName, payload);
		}else {
			this.lastResult = this.lambdaHelper.runLambda(functionName);
		}
	}

	@When("the user invokes the Lambda function as described below:")
	public void the_user_invokes_the_Lambda_function_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);

		for (int i = 1; i < table.size(); i++) {
			String functionName = table.get(i).get(0);
			String payload = table.get(i).get(1);
			if(payload != null && !payload.isEmpty()) {
				this.lastResult = this.lambdaHelper.runLambda(functionName, payload);
			}else {
				this.lastResult = this.lambdaHelper.runLambda(functionName);
			}
		}
		
		String output = lambdaHelper.getLambdaOutput(this.lastResult);
		System.out.print("Output from Last Lambda: " + output);
	}
	
	
	
	

	// =======================================================================================================================================================
	// =======================================================================================================================================================
	private LambdaHelper createLambdaHelper() {
		AWSCredentials creds = CredentialsHelper.getCredentialsFromDefaultProvider();
		String region = testRun.getTestData().get(Constants.AWS_REGION_KEY).toString();
		LambdaHelper helper = new LambdaHelper(creds, region);
		return helper;
	}
	
	
	
	private String getFunctionName() throws Exception {
		String functionName = "";
		if (testRun.getTestData().containsKey(Constants.LAMBDA_FUNCTION_NAME_KEY)) {
			functionName = testRun.getTestData().get(Constants.LAMBDA_FUNCTION_NAME_KEY).toString();
			;
		} else if (!this.currentFunctionName.isEmpty()) {
			functionName = this.currentFunctionName;
		} else {
			String errMsg = "No '" + Constants.LAMBDA_FUNCTION_NAME_KEY + "' has been set and the currentFunctionName variable is null.";
			throw new Exception(errMsg);
		}
		return functionName;
	}
	
	
	private String getPayload() throws Exception {
		String payload = null;
		if (testRun.getTestData().containsKey(Constants.LAMBDA_PAYLOAD_KEY)) {
			payload = testRun.getTestData().get(Constants.LAMBDA_PAYLOAD_KEY).toString();
			
		} 
//		else if (!this.currentPayload.isEmpty()) {
//			payload = null;
//		} else {
//			String errMsg = "No '" + Constants.LAMBDA_PAYLOAD_KEY + "' has been set and the currentPayload variable is null.";
//			throw new Exception(errMsg);
//		}
		return payload;
	}

}
