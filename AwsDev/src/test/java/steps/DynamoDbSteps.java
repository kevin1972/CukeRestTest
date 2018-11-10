package steps;

import java.util.List;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.model.TableDescription;
import com.colbert.kevin.aws.helpers.CredentialsHelper;
import com.colbert.kevin.aws.helpers.DynamoDbHelper;

import cucumber.api.java.en.When;
import framework.Constants;
import framework.TestRun;

public class DynamoDbSteps {
	private TestRun testRun;
	private DynamoDbHelper dynamoDbHelper;
	private String currentTableName;
	private List<String> currentTableNames;
	private TableDescription currentTableDescription;
	private String currentReadUnits;
	private String currentWriteUnits;
	private String currentItemName;
	private String currentItemKey;
	private Item currentItem;

	public DynamoDbSteps(TestRun testRun) {
		this.testRun = testRun;
		this.dynamoDbHelper = createDynamoDbHelper();
	}

	
	@When("the user creates a table using the given information")
	public void the_user_creates_a_table_using_the_given_information() throws Exception {
		String tableName = getTableName();
	    dynamoDbHelper.createTable(tableName);
	}

	@When("the user creates a table named {string}")
	public void the_user_creates_a_table_named(String tableName) {
		dynamoDbHelper.createTable(tableName);
		this.currentTableName = tableName;
	}

	@When("the user creates a table\\(s) as described below:")
	public void the_user_creates_a_table_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);
		for (int i = 1; i < table.size(); i++) {
			String tableName = table.get(i).get(0);
			dynamoDbHelper.createTable(tableName);
			this.currentTableName = tableName;
		}
	}

	@When("the user gets the table names")
	public void the_user_gets_the_table_names() {
	    this.currentTableNames = this.dynamoDbHelper.getTableNames();
	}

	@When("the user describes a table using the given information")
	public void the_user_describes_a_table_using_the_given_information() throws Exception {
		String tableName = getTableName();
	    this.currentTableDescription = dynamoDbHelper.describeTable(tableName);
	}

	@When("the user describees a table named {string}")
	public void the_user_describees_a_table_named(String tableName) {
		this.currentTableDescription = dynamoDbHelper.describeTable(tableName);
	}

	@When("the user describes a table\\(s) as described below:")
	public void the_user_describes_a_table_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);
		for (int i = 1; i < table.size(); i++) {
			String tableName = table.get(i).get(0);			
			this.currentTableDescription = dynamoDbHelper.describeTable(tableName);;
		}
	}

	@When("the user updates provisioned throughput using the given information")
	public void the_user_updates_provisioned_throughput_using_the_given_information() throws Exception {
		String tableName = getTableName();
		String readUnits = getReadUnits();
		String writeUnits = getWriteUnits();
	    dynamoDbHelper.updateProvisionedThroughput(tableName, Long.valueOf(readUnits), Long.valueOf(writeUnits));
	}

	@When("the user updates provisioned throughput for the table {string} by {string} read units and {string} write units")
	public void the_user_updates_provisioned_throughput_for_the_table_by_read_units_and_write_units(String tableName, String readUnits, String writeUnits) {
		this.currentTableName = tableName;
		this.currentReadUnits = readUnits;
		this.currentWriteUnits = writeUnits;
		dynamoDbHelper.updateProvisionedThroughput(tableName, Long.valueOf(readUnits), Long.valueOf(writeUnits));
	}

	@When("the user updates the provisioned throughput as described below:")
	public void the_user_updates_the_provisioned_throughput_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);
		for (int i = 1; i < table.size(); i++) {
			String tableName = table.get(i).get(0);		
			String readUnits = table.get(i).get(1);
			String writeUnits = table.get(i).get(2);
			dynamoDbHelper.updateProvisionedThroughput(tableName, Long.valueOf(readUnits), Long.valueOf(writeUnits));
		}
	}

	@When("the user deletes a table using the given information")
	public void the_user_deletes_a_table_using_the_given_information() throws Exception {
		String tableName = getTableName();
	    dynamoDbHelper.deleteTable(tableName);
	}

	@When("the user deletes a table named {string}")
	public void the_user_deletes_a_table_named(String tableName) {
		dynamoDbHelper.deleteTable(tableName);
	}

	@When("the user deletes a table\\(s) as described below:")
	public void the_user_deletes_a_table_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);
		for (int i = 1; i < table.size(); i++) {
			String tableName = table.get(i).get(0);			
			dynamoDbHelper.deleteTable(tableName);
		}
	}

	@When("the user puts an item using the given information")
	public void the_user_puts_an_item_using_the_given_information() throws Exception {
		String tableName = getTableName();
		String itemName = getItemName();
		String itemKey = getItemKey();
		this.currentItemName = itemName;
		//dynamoDbHelper.putItem(tableName, itemName);
	}

	@When("the user puts an item named {string} into the table {string}")
	public void the_user_puts_an_item_named_into_the_table(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("the user puts an item as described below:")
	public void the_user_puts_an_item_as_described_below(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    throw new cucumber.api.PendingException();
	}
	
	
	@When("the user gets an item using the given information")
	public void the_user_gets_an_item_using_the_given_information() throws Exception {
		String tableName = getTableName();		
		String itemKey = getItemKey();		
		this.currentItem = dynamoDbHelper.getItem(tableName, itemKey);
	}

	@When("the user gets an item with the key {string} from the table {string}")
	public void the_user_gets_an_item_named_from_the_table(String itemKey, String tableName) {
		this.currentItem = dynamoDbHelper.getItem(tableName, itemKey);
	}

	@When("the user gets an item\\(s) as described below:")
	public void the_user_gets_an_item_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);
		for (int i = 1; i < table.size(); i++) {
			String tableName = table.get(i).get(0);
			String itemKey = table.get(i).get(1);
			this.currentItem = dynamoDbHelper.getItem(tableName, itemKey);
		}
	}

	@When("the user updates an item using the given information")
	public void the_user_updates_an_item_using_the_given_information() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("the user updates an item named {string} in the table {string}")
	public void the_user_updates_an_item_named_in_the_table(String string, String string2) {
	    // Write code here that turns the phrase above into concrete actions
	    throw new cucumber.api.PendingException();
	}

	@When("the user updates an item\\(s) as described below:")
	public void the_user_updates_an_item_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
	    // Write code here that turns the phrase above into concrete actions
	    // For automatic transformation, change DataTable to one of
	    // E, List<E>, List<List<E>>, List<Map<K,V>>, Map<K,V> or
	    // Map<K, List<V>>. E,K,V must be a String, Integer, Float,
	    // Double, Byte, Short, Long, BigInteger or BigDecimal.
	    //
	    // For other transformations you can register a DataTableType.
	    throw new cucumber.api.PendingException();
	}

	@When("the user deletes an item using the given information")
	public void the_user_deletes_an_item_using_the_given_information() throws Exception {
		String tableName = getTableName();		
		String itemKey = getItemKey();		
		dynamoDbHelper.deleteItem(tableName, itemKey);
	}

	@When("the user deletes an item with the key {string} from the table {string}")
	public void the_user_deletes_an_item_named_in_the_table(String itemKey, String tableName) {
		dynamoDbHelper.deleteItem(tableName, itemKey);
	}

	@When("the user deletes an item\\(s) as described below:")
	public void the_user_deletes_an_item_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);
		for (int i = 1; i < table.size(); i++) {
			String tableName = table.get(i).get(0);
			String itemKey = table.get(i).get(1);
			dynamoDbHelper.deleteItem(tableName, itemKey);
		}
	}
	// =======================================================================================================================================================
	// =======================================================================================================================================================
	private DynamoDbHelper createDynamoDbHelper() {
		AWSCredentials creds = CredentialsHelper.getCredentialsFromDefaultProvider();
		// HashMap<String, Object> testData = testRun.getTestData();
		String region = testRun.getTestData().get(Constants.AWS_REGION_KEY).toString();
		DynamoDbHelper helper = new DynamoDbHelper(creds, region);
		return helper;
	}
	
	
	private String getTableName() throws Exception {
		String tableName = "";
		if (testRun.getTestData().containsKey(Constants.TABLE_NAME_KEY)) {
			tableName = testRun.getTestData().get(Constants.TABLE_NAME_KEY).toString();
			this.currentTableName = tableName;
		} else if (this.currentTableName != null) {
			tableName = this.currentTableName;
		} else {
			String errMsg = "No '" + Constants.TABLE_NAME_KEY + "' has been set and the currentTableName variable is null.";
			throw new Exception(errMsg);
		}
		return tableName;
	}
	
	
	private String getReadUnits() throws Exception {
		String readUnits = "";
		if (testRun.getTestData().containsKey(Constants.READ_UNITS_KEY)) {
			readUnits = testRun.getTestData().get(Constants.READ_UNITS_KEY).toString();
			this.currentReadUnits = readUnits;
		} else if (this.currentReadUnits != null) {
			readUnits = this.currentReadUnits;
		} else {
			String errMsg = "No '" + Constants.READ_UNITS_KEY + "' has been set and the currentReadUnits variable is null.";
			throw new Exception(errMsg);
		}
		return readUnits;
	}
	
	private String getWriteUnits() throws Exception {
		String writeUnits = "";
		if (testRun.getTestData().containsKey(Constants.WRITE_UNITS_KEY)) {
			writeUnits = testRun.getTestData().get(Constants.WRITE_UNITS_KEY).toString();
			this.currentWriteUnits = writeUnits;
		} else if (this.currentWriteUnits != null) {
			writeUnits = this.currentWriteUnits;
		} else {
			String errMsg = "No '" + Constants.WRITE_UNITS_KEY + "' has been set and the currentWriteUnits variable is null.";
			throw new Exception(errMsg);
		}
		return writeUnits;
	}
	
	
	private String getItemName() throws Exception {
		String itemName = "";
		if (testRun.getTestData().containsKey(Constants.ITEM_NAME_KEY)) {
			itemName = testRun.getTestData().get(Constants.ITEM_NAME_KEY).toString();
			this.currentItemName = itemName;
		} else if (this.currentItemName != null) {
			itemName = this.currentItemName;
		} else {
			String errMsg = "No '" + Constants.ITEM_NAME_KEY + "' has been set and the currentItemName variable is null.";
			throw new Exception(errMsg);
		}
		return itemName;
	}
	
	
	private String getItemKey() throws Exception {
		String itemKey = "";
		if (testRun.getTestData().containsKey(Constants.ITEM_KEY_KEY)) {
			itemKey = testRun.getTestData().get(Constants.ITEM_KEY_KEY).toString();
			this.currentItemKey = itemKey;
		} else if (this.currentItemKey != null) {
			itemKey = this.currentItemKey;
		} else {
			String errMsg = "No '" + Constants.ITEM_KEY_KEY + "' has been set and the currentItemKey variable is null.";
			throw new Exception(errMsg);
		}
		return itemKey;
	}

}
