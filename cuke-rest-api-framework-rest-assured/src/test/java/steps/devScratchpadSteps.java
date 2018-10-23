package steps;

import java.util.List;
import io.cucumber.datatable.DataTable;
import cucumber.api.java.en.When;
import framework.DataTableHelper;

public class devScratchpadSteps {
	@When("I print the contents of the following table:")
	public void i_print_the_contents_of_the_following_table(DataTable dataTable) {	    
	    DataTableHelper.displayTableValues(dataTable, true);
	}
	
	
	@When("the values in the following table are verified:")
	public void the_values_in_the_following_table_are_verified(DataTable dataTable) {
	    List<List<String>> table = dataTable.asLists();	    
	    for(int i=1; i < table.size(); i++) {
	    	List<String> row = table.get(i);
	    	String label = row.get(0);
	    	String expectedValue = row.get(1);
	    	String actualValue = row.get(2);
	    	
	    	if(expectedValue.equals(actualValue)) {
	    		System.out.printf("[%s] EXPECTED: '%s' matches ACTUAL:'%s'\n", label, expectedValue, actualValue);
	    	}else {
	    		System.out.printf("[%s] EXPECTED: '%s' does NOT match  ACTUAL:'%s'\n", label, expectedValue, actualValue);
	    	}
	    	
	    }
	}
}
