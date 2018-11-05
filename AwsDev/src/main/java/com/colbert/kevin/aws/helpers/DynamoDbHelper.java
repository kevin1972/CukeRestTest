package com.colbert.kevin.aws.helpers;

import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.DeleteItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.GetItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.model.AttributeDefinition;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.CreateTableResult;
import com.amazonaws.services.dynamodbv2.model.KeySchemaElement;
import com.amazonaws.services.dynamodbv2.model.KeyType;
import com.amazonaws.services.dynamodbv2.model.ListTablesRequest;
import com.amazonaws.services.dynamodbv2.model.ListTablesResult;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughputDescription;
import com.amazonaws.services.dynamodbv2.model.ScalarAttributeType;
import com.amazonaws.services.dynamodbv2.model.TableDescription;

public class DynamoDbHelper {
	private AWSCredentials credentials;
	private AmazonDynamoDB dynamoDbClient;
	private String region;

	public AmazonDynamoDB getDynamoDbClient() {
		return dynamoDbClient;
	}

	// ----------------------------------------------------------------
	// Constructor
	// ----------------------------------------------------------------
	public DynamoDbHelper(AWSCredentials credentials, String region) {
		this.credentials = credentials;
		this.region = region;
		try {
			this.dynamoDbClient = createDynamoDbClient(this.region);
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
	}

	// ----------------------------------------------------------------
	// Create Dynamo DB Client
	// ----------------------------------------------------------------
	private AmazonDynamoDB createDynamoDbClient(String region) {
		AmazonDynamoDB dynamoDb = null;
		try {
			dynamoDb = AmazonDynamoDBClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(this.credentials)).build();
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
		return dynamoDb;
	}

	// ----------------------------------------------------------------
	// Create Table
	// ----------------------------------------------------------------
	public void createTable(String tableName) {
		CreateTableRequest request = new CreateTableRequest()
				.withAttributeDefinitions(new AttributeDefinition("Name", ScalarAttributeType.S))
				.withKeySchema(new KeySchemaElement("Name", KeyType.HASH))
				.withProvisionedThroughput(new ProvisionedThroughput(new Long(10), new Long(10)))
				.withTableName(tableName);

		try {
			CreateTableResult result = this.dynamoDbClient.createTable(request);
			System.out.println(result.getTableDescription().getTableName());
		} catch (AmazonServiceException ase) {
			AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
		}
	}

	// ----------------------------------------------------------------
	// Get Table Names
	// ----------------------------------------------------------------
	public List<String> getTableNames() {
		ListTablesRequest request;

		boolean more_tables = true;
		String last_name = null;

		List<String> table_names = new ArrayList<String>();
		while (more_tables) {
			try {
				if (last_name == null) {
					request = new ListTablesRequest().withLimit(10);
				} else {
					request = new ListTablesRequest().withLimit(10).withExclusiveStartTableName(last_name);
				}

				ListTablesResult table_list = this.dynamoDbClient.listTables(request);
				table_names = table_list.getTableNames();

				if (table_names.size() > 0) {
					for (String cur_name : table_names) {
						System.out.format("* %s\n", cur_name);
					}
				} else {
					System.out.println("No tables found!");
				}

				last_name = table_list.getLastEvaluatedTableName();
				if (last_name == null) {
					more_tables = false;
				}

			} catch (AmazonServiceException ase) {
				AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
			}
		}
		return table_names;
	}

	// ----------------------------------------------------------------
	// Describe Table
	// ----------------------------------------------------------------
	public TableDescription describeTable(String tableName) {
		TableDescription table_info = null;
		try {
			table_info = this.dynamoDbClient.describeTable(tableName).getTable();
			if (table_info != null) {
				System.out.format("Table name  : %s\n", table_info.getTableName());
				System.out.format("Table ARN   : %s\n", table_info.getTableArn());
				System.out.format("Status      : %s\n", table_info.getTableStatus());
				System.out.format("Item count  : %d\n", table_info.getItemCount().longValue());
				System.out.format("Size (bytes): %d\n", table_info.getTableSizeBytes().longValue());

				ProvisionedThroughputDescription throughput_info = table_info.getProvisionedThroughput();
				System.out.println("Throughput");
				System.out.format("  Read Capacity : %d\n", throughput_info.getReadCapacityUnits().longValue());
				System.out.format("  Write Capacity: %d\n", throughput_info.getWriteCapacityUnits().longValue());

				List<AttributeDefinition> attributes = table_info.getAttributeDefinitions();
				System.out.println("Attributes");
				for (AttributeDefinition a : attributes) {
					System.out.format("  %s (%s)\n", a.getAttributeName(), a.getAttributeType());
				}
			}
		} catch (AmazonServiceException e) {
			System.err.println(e.getErrorMessage());
			System.exit(1);
		}

		return table_info;
	}

	// ----------------------------------------------------------------
	// Update Provisioned Throughput
	// ----------------------------------------------------------------
	public void updateProvisionedThroughput(String tableName, Long readCapacityUnits, Long writeCapacityUnits) {
		DynamoDB dynamoDB = new DynamoDB(this.dynamoDbClient);

		Table table = dynamoDB.getTable(tableName);

		ProvisionedThroughput provisionedThroughput = new ProvisionedThroughput()
				.withReadCapacityUnits(readCapacityUnits).withWriteCapacityUnits(writeCapacityUnits);

		table.updateTable(provisionedThroughput);

		try {
			table.waitForActive();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// ----------------------------------------------------------------
	// Delete Table
	// ----------------------------------------------------------------
	public void deleteTable(String tableName) {
		DynamoDB dynamoDB = new DynamoDB(this.dynamoDbClient);
		Table table = dynamoDB.getTable(tableName);
		table.delete();
		try {
			table.waitForDelete();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// ----------------------------------------------------------------
	// Put Item
	// ----------------------------------------------------------------
	public PutItemOutcome putItem(String tableName, Item item) {
		DynamoDB dynamoDB = new DynamoDB(this.dynamoDbClient);
		Table table = dynamoDB.getTable(tableName);
		PutItemOutcome result = table.putItem(item);
		return result;
	}

	// ----------------------------------------------------------------
	// Get Item
	// ----------------------------------------------------------------
	public Item getItem(String tableName, GetItemSpec getItemSpec) {
		DynamoDB dynamoDB = new DynamoDB(this.dynamoDbClient);
		Table table = dynamoDB.getTable(tableName);
		Item item = table.getItem(getItemSpec);
		return item;
	}

	// ----------------------------------------------------------------
	// Update Item
	// ----------------------------------------------------------------
	public UpdateItemOutcome updateItem(String tableName, UpdateItemSpec updateItemSpec) {
		DynamoDB dynamoDB = new DynamoDB(this.dynamoDbClient);
		Table table = dynamoDB.getTable(tableName);
		UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
		return outcome;
	}

	// ----------------------------------------------------------------
	// Delete Item
	// ----------------------------------------------------------------
	public DeleteItemOutcome deleteItem(String tableName, DeleteItemSpec deleteItemSpec) {
		DynamoDB dynamoDB = new DynamoDB(this.dynamoDbClient);
		Table table = dynamoDB.getTable(tableName);
		DeleteItemOutcome outcome = table.deleteItem(deleteItemSpec);
		return outcome;
	}

}
