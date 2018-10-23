package steps;
import org.junit.Assert;
import com.jayway.jsonpath.JsonPath;
import cucumber.api.java.en.Then;
import framework.Constants;
import framework.TestRun;
import net.minidev.json.JSONArray;

public class RestCountriesTestsSteps {
	private TestRun testRun;	
	
	public RestCountriesTestsSteps(TestRun testRun) {
		this.testRun = testRun;
	}
	
	@Then("^all countries should be returned$")
	public void all_countries_should_be_returned() throws Throwable {
		String responseContent = (String) testRun.getTestData().get(Constants.HTTP_RESPONSE_KEY);
		JSONArray jArray = JsonPath.read(responseContent, "$");
		int arraySize = jArray.size();
		Assert.assertEquals(250, arraySize);
	}

	@Then("^\"([^\"]*)\" countries should be returned$")
	public void countries_should_be_returned(String numExpectedCountries) throws Throwable {
		String responseContent = (String) testRun.getTestData().get(Constants.HTTP_RESPONSE_KEY);
		JSONArray jArray = JsonPath.read(responseContent, "$");
		int arraySize = jArray.size();
		int expected = Integer.valueOf(numExpectedCountries);
		Assert.assertEquals(expected, arraySize);
	}

}
