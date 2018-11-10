package cucumberSupport;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
 
@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty"},
		features = {"src/test/resources/LambdaSmokeTests.feature"},
		glue = {"steps"},
		tags = {"@RunMe"}
		)
 
public class LambdaSmokeTestsRunner {
 
}
