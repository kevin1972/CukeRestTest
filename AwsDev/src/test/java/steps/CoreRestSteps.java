package steps;

import java.util.HashMap;
import java.util.List;
import org.junit.Assert;
import com.jayway.jsonpath.JsonPath;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import framework.Constants;
import framework.HttpMethodRunner;
import framework.TestRun;
import io.cucumber.datatable.DataTable;
import net.minidev.json.JSONArray;
//import gherkin.ast.DataTable;

public class CoreRestSteps {
	private TestRun testRun;

	public CoreRestSteps(TestRun testRun) {
		this.testRun = testRun;
	}

	// ###########################################################################################
	//
	// CORE STEP DEFINITIONS
	//
	// ###########################################################################################

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	@Given("^the following setup information:$")
	public void the_following_setup_information(DataTable dt) throws Throwable {		
		List<List<String>> table = dt.asLists();		
		for(int i = 1; i<table.size(); i++) {
			String key = table.get(i).get(0);
			String value = table.get(i).get(1);
			testRun.getTestData().put(key, value);
		}
	}
	
	
	@Given("^the following information:$")
	public void the_following_information(DataTable dt) throws Throwable {		
		
		List<List<String>> table = dt.asLists();		
		for(int i = 1; i<table.size(); i++) {
			String key = table.get(i).get(0);
			String value = table.get(i).get(1);
			testRun.getTestData().put(key, value);
		}
		
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	@When("^I make the call to the resource$")
	public void i_make_the_call_to_the_resource() throws Throwable {

		// get the method
		String method = "";
		if (testRun.getTestData().containsKey(Constants.METHOD_KEY)) {
			method = (String) testRun.getTestData().get(Constants.METHOD_KEY);
		}

		// handle the request
		switch (method.toLowerCase()) {
		case "get":
			HttpMethodRunner.doGet(testRun);
			break;
		case "post":
			HttpMethodRunner.doPost(testRun);
			break;
		case "put":
			HttpMethodRunner.doPut(testRun);
			break;
		case "delete":
			HttpMethodRunner.doDelete(testRun);
			break;
		default:
			throw new Exception("'" + method + "' is not a supported method.");
		}
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	@Then("^the response code should be \"([^\"]*)\"$")
	public void the_response_code_should_be(String expectedCode) throws Throwable {
		String returnCode = String.valueOf(testRun.getTestData().get(Constants.HTTP_RESPONSE_STATUS_CODE_KEY));
		Assert.assertEquals(expectedCode, returnCode);
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	@Then("^the values should match the following table:$")
	public void the_values_should_match_the_following_table(DataTable dt) throws Throwable {
		String responseContent = (String) testRun.getTestData().get(Constants.HTTP_RESPONSE_KEY);
		System.out.println(responseContent);
		JSONArray contentArray = null;
		if (responseContent.startsWith("[")) {
			contentArray = JsonPath.read(responseContent, "$");
		}

		String actualValue = "";
		
		
		
//		List<List<String>> table = dt.asLists();		
//		for(int i = 1; i<table.size(); i++) {
//			String key = table.get(i).get(0);
//			String value = table.get(i).get(1);
//			testRun.getTestData().put(key, value);
//		}
		
		
		
		List<List<String>> table = dt.asLists();
		for (int i = 1; i < table.size(); i++) {
			List<String> row = table.get(i);
			String field = row.get(0);
			String jPath = row.get(1);
			String expectedValue = row.get(2);

			if (contentArray != null) {
				actualValue = JsonPath.read(contentArray.toJSONString(), jPath).toString();
			} else {
				actualValue = JsonPath.read(responseContent, jPath).toString();
			}

			if (expectedValue.equals(actualValue)) {
				System.out.printf("[%s] Expected: '%s' matched Actual: '%s'\n", field, expectedValue, actualValue);
				Assert.assertEquals(expectedValue, actualValue);
			} else {
				System.out.printf("[%s] Expected: '%s' DID NOT MATCH Actual: '%s'\n", field, expectedValue,
						actualValue);
				Assert.assertEquals(expectedValue, actualValue);
			}
		}
		
		
//		List<DataTableRow> gherkinRows = table.getGherkinRows();
//		for (int i = 1; i < gherkinRows.size(); i++) {
//			DataTableRow row = gherkinRows.get(i);
//			String field = row.getCells().get(0);
//			String jPath = row.getCells().get(1);
//			String expectedValue = row.getCells().get(2).toString();
//
//			if (contentArray != null) {
//				actualValue = JsonPath.read(contentArray.toJSONString(), jPath).toString();
//			} else {
//				actualValue = JsonPath.read(responseContent, jPath).toString();
//			}
//
//			if (expectedValue.equals(actualValue)) {
//				System.out.printf("[%s] Expected: '%s' matched Actual: '%s'\n", field, expectedValue, actualValue);
//				Assert.assertEquals(expectedValue, actualValue);
//			} else {
//				System.out.printf("[%s] Expected: '%s' DID NOT MATCH Actual: '%s'\n", field, expectedValue,
//						actualValue);
//				Assert.assertEquals(expectedValue, actualValue);
//			}
//		}
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	@Given("^the following parameter information:$")
	public void the_following_parameter_information(DataTable dt) throws Throwable {
//		HashMap<String, String> parameters = new HashMap<String, String>();
//		List<DataTableRow> gherkinRows = table.getGherkinRows();
//
//		for (DataTableRow row : gherkinRows) {
//			String key = row.getCells().get(0);
//			String value = row.getCells().get(1);
//			parameters.put(key, value);
//		}
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		
		List<List<String>> table = dt.asLists();
		for (int i = 1; i<table.size(); i++) {
			List<String> row = table.get(i);
			String key = row.get(0);
			String value = row.get(1);
			parameters.put(key, value);
		}
		
		testRun.getTestData().put(Constants.PARAMETERS_KEY, parameters);
	}

	// -----------------------------------------------------------
	// -----------------------------------------------------------
	@Given("^the message data is \"([^\"]*)\"$")
	public void the_message_data_is(String messageData) throws Throwable {
		testRun.getTestData().put(Constants.MESSAGE_DATA_KEY, messageData);
	}

}
