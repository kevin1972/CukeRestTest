package TestRunners;
import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
 
@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty"},
		features = {"src/test/resources/Features/AWS/LambdaSmokeTests.feature"},
		glue = {"steps"},
		tags = {"@RunMe"}
		)
 
public class LambdaSmokeTestsRunner {
 
}
