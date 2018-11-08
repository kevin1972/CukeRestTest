package steps;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Assert;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import com.colbert.kevin.aws.helpers.CredentialsHelper;
import com.colbert.kevin.aws.helpers.S3Helper;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import framework.Constants;
import framework.TestRun;

public class S3Steps {
	private TestRun testRun;
	//private String region;
	private S3Helper s3Helper;
	private Bucket currentBucket;
	private File currentFile;
	private List<S3ObjectSummary> currentObjectList;

	public S3Steps(TestRun testRun) {
		this.testRun = testRun;
		this.s3Helper = createS3Helper();
	}

	@When("the user creates a bucket named {string} in the given region")
	public void the_user_creates_a_bucket_named_in_the_given_region(String bucketName) {
		this.currentBucket = this.s3Helper.createBucket(bucketName);
	}

	@Then("a new bucket called {string} should be displayed in the list of buckets")
	public void a_new_bucket_called_should_be_displayed_in_the_list_of_buckets(String bucketName) {
		// verify bucket was found
		if (doesBucketExist(bucketName)) {
			Assert.assertEquals(true, true);
		} else {
			Assert.assertEquals(true, false);
		}
	}

	@When("the user gets a list of the objects in the bucket named {string}")
	public void the_user_gets_a_list_of_the_objects_in_the_bucket_named(String bucketName) {
		if (doesBucketExist(bucketName)) {
			this.currentBucket = getBucket(bucketName);
			this.currentObjectList = this.s3Helper.listObjects(bucketName);
		}
	}

	@Given("a bucket named {string} exists in the region")
	public void a_bucket_named_exists_in_the_region(String bucketName) {
		if (!doesBucketExist(bucketName)) {
			this.currentBucket = this.s3Helper.createBucket(bucketName);
		} else {
			this.currentBucket = getBucket(bucketName);
		}
	}

	@When("the user uploads the file with the given path and name")
	public void the_user_uploads_the_file_with_the_given_path_and_name() {
		String key = testRun.getTestData().get(Constants.FILE_KEY).toString();
		String bucketName = "";
		if (this.currentBucket != null) {
			bucketName = this.currentBucket.getName();
		} else {
			bucketName = testRun.getTestData().get(Constants.S3_BUCKET_NAME_KEY).toString();
		}
		File file = new File(testRun.getTestData().get(Constants.FILE_PATH_KEY).toString());
		this.currentFile = file;
		this.s3Helper.putObject(bucketName, key, file);
	}

	@Then("the file should appear in the list of files in the bucket.")
	public void the_file_should_appear_in_the_list_of_files_in_the_bucket() {
		String bucketName = "";
		if (this.currentBucket != null) {
			bucketName = this.currentBucket.getName();
		} else {
			bucketName = testRun.getTestData().get(Constants.S3_BUCKET_NAME_KEY).toString();
		}

		List<S3ObjectSummary> contents = this.s3Helper.listObjects(bucketName);
		HashMap<String, S3ObjectSummary> contentsMap = new HashMap<String, S3ObjectSummary>();
		for (S3ObjectSummary os : contents) {
			contentsMap.put(os.getKey(), os);
		}

		String expectedKey = this.currentFile.getName();
		if (!contentsMap.containsKey(expectedKey)) {
			System.out.printf("ERROR: Could not find the expected object '%s' in the bucket '%s'\n", expectedKey,
					bucketName);
		} else {
			System.out.printf("Found the expected object '%s' in the bucket '%s'\n", expectedKey, bucketName);
		}
	}

	@Then("the bucket named {string} should contain the following objects:")
	public void the_bucket_named_should_contain_the_following_objects(String bucketName,
			io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);
		List<S3ObjectSummary> contents = this.s3Helper.listObjects(bucketName);
		HashMap<String, S3ObjectSummary> contentsMap = new HashMap<String, S3ObjectSummary>();
		for (S3ObjectSummary os : contents) {
			contentsMap.put(os.getKey(), os);
		}

		for (int i = 1; i < table.size(); i++) { // i starts from 1 because i=0 represents the header
			String expectedObjectName = table.get(i).get(0);
			if (!contentsMap.containsKey(expectedObjectName)) {
				System.out.printf("ERROR: Could not find the expected object '%s' in the bucket '%s'\n",
						expectedObjectName, bucketName);
			} else {
				System.out.printf("Found the expected object '%s' in the bucket '%s'\n", expectedObjectName, bucketName);
			}
		}
	}

	@When("the user uploads file\\(s) as described below:")
	public void the_user_uploads_file_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);

		for (int i = 1; i < table.size(); i++) {
			String bucketName = table.get(i).get(0);
			String key = table.get(i).get(1);
			String filePath = table.get(i).get(2);
			File file = new File(filePath);
			this.s3Helper.putObject(bucketName, key, file);
			this.currentFile = file;
		}
	}

	@Then("the bucket should contain all of the existing files in addition to the files that were uploaded.")
	public void the_bucket_should_contain_all_of_the_existing_files_in_addition_to_the_files_that_were_uploaded() {
		List<S3ObjectSummary> existingFiles = new ArrayList<S3ObjectSummary>();
		existingFiles = this.currentObjectList;

		List<S3ObjectSummary> currentBucketObjectList = this.s3Helper.listObjects(this.currentBucket.getName());
		HashMap<String, S3ObjectSummary> currentBucketContentsMap = new HashMap<String, S3ObjectSummary>();
		for (S3ObjectSummary os : currentBucketObjectList) {
			currentBucketContentsMap.put(os.getKey(), os);
		}

		if (currentBucketContentsMap.containsKey(currentFile.getName())) {
			Assert.assertEquals(true, true);
		} else {
			Assert.assertEquals(true, false);
		}

		for (S3ObjectSummary existingFile : existingFiles) {
			if (currentBucketContentsMap.containsKey(existingFile.getKey())) {
				Assert.assertEquals(true, true);
			} else {
				Assert.assertEquals(true, false);
			}
		}
	}

	@When("the user deletes the file with the given key")
	public void the_user_deletes_the_file_with_the_given_key() throws Exception {
		String fileKey = "";
		String bucketName = "";

		// check fileKey
		if (testRun.getTestData().containsKey(Constants.FILE_KEY)) {
			bucketName = testRun.getTestData().get(Constants.FILE_KEY).toString();
			;
		} else if (this.currentFile != null) {
			bucketName = this.currentFile.getName();
		} else {
			String errMsg = "No '" + Constants.FILE_KEY + "' has been set and the currentFile variable is null.";
			throw new Exception(errMsg);
		}

		// check bucketName
		if (testRun.getTestData().containsKey(Constants.S3_BUCKET_NAME_KEY)) {
			bucketName = testRun.getTestData().get(Constants.S3_BUCKET_NAME_KEY).toString();
		} else if (this.currentBucket != null) {
			bucketName = this.currentBucket.getName();
		} else {
			String errMsg = "No '" + Constants.S3_BUCKET_NAME_KEY
					+ "' has been set and the currentBucket variable is null.";
			throw new Exception(errMsg);
		}

		// delete object
		s3Helper.deleteObject(bucketName, fileKey);

	}

	@When("the user deletes the file named {string} from the bucket named {string}")
	public void the_user_deletes_the_file_named_from_the_bucket_named(String fileName, String bucketName) {
		s3Helper.deleteObject(fileName, bucketName);
	}

	@When("the user deletes the file\\(s) as described below:")
	public void the_user_deletes_the_file_s_as_described_below(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);

		for (int i = 1; i < table.size(); i++) {
			String region = table.get(i).get(0);
			String bucketName = table.get(i).get(1);
			String fileKey = table.get(i).get(2);
			s3Helper.deleteObject(region, bucketName, fileKey);
		}
	}

	@Then("the bucket should not contain the file that was deleted")
	public void the_bucket_should_not_contain_the_file_that_was_deleted() {
		// Write code here that turns the phrase above into concrete actions
		throw new cucumber.api.PendingException();
	}

	@Then("the bucket named {string} in the region {string} should not contain the key {string}")
	public void the_bucket_named_in_the_region_should_not_contain_the_key(String bucketName, String regionName,
			String key) {
		if (doesBucketExist(bucketName)) {
			this.currentBucket = getBucket(bucketName);
			this.currentObjectList = this.s3Helper.listObjects(bucketName);

			HashMap<String, S3ObjectSummary> objectMap = new HashMap<String, S3ObjectSummary>();
			for (S3ObjectSummary os : this.currentObjectList) {
				objectMap.put(os.getKey(), os);
			}

			if (!objectMap.containsKey(key)) {
				Assert.assertEquals(true, true);
			} else {
				Assert.assertEquals(true, false);
			}
		}
	}

	@Then("a file with the following description should not be present:")
	public void a_file_with_the_following_description_should_not_be_present(io.cucumber.datatable.DataTable dataTable) {
		List<List<String>> table = dataTable.asLists(String.class);

		for (int i = 1; i < table.size(); i++) {
			String region = table.get(i).get(0);
			String bucketName = table.get(i).get(1);
			String fileKey = table.get(i).get(2);
			List<S3ObjectSummary> bucketContents = s3Helper.listObjects(region, bucketName);
			
			HashMap<String, S3ObjectSummary> objectMap = new HashMap<String, S3ObjectSummary>();
			for (S3ObjectSummary os : this.currentObjectList) {
				objectMap.put(os.getKey(), os);
			}
			
			if (!objectMap.containsKey(fileKey)) {
				Assert.assertEquals(true, true);
			} else {
				Assert.assertEquals(true, false);
			}
		}
	}

	// =======================================================================================================================================================
	// =======================================================================================================================================================
	private S3Helper createS3Helper() {
		AWSCredentials creds = CredentialsHelper.getCredentialsFromDefaultProvider();
		HashMap<String, Object> testData = testRun.getTestData();
		String region = testRun.getTestData().get(Constants.AWS_REGION_KEY).toString();
		S3Helper s3 = new S3Helper(creds, region);
		return s3;
	}

	private Boolean doesBucketExist(String bucketName) {
		// create the helper
		S3Helper helper = createS3Helper();

		// get a list of buckets
		List<Bucket> buckets = helper.listBuckets();

		// convert list of buckets to a map
		HashMap<String, Bucket> bucketMap = new HashMap<String, Bucket>();
		for (Bucket bucket : buckets) {
			bucketMap.put(bucket.getName(), bucket);
		}

		// verify bucket was found
		if (bucketMap.containsKey(bucketName)) {
			return true;
		} else {
			return false;
		}

	}
	
	private Boolean doesBucketExist(String region, String bucketName) {
		// create the helper
		S3Helper helper = createS3Helper();

		// get a list of buckets
		List<Bucket> buckets = helper.listBuckets(region);

		// convert list of buckets to a map
		HashMap<String, Bucket> bucketMap = new HashMap<String, Bucket>();
		for (Bucket bucket : buckets) {
			bucketMap.put(bucket.getName(), bucket);
		}

		// verify bucket was found
		if (bucketMap.containsKey(bucketName)) {
			return true;
		} else {
			return false;
		}

	}

	private Bucket getBucket(String bucketName) {
		Bucket foundBucket = null;
		// create the helper
		S3Helper helper = createS3Helper();

		// get a list of buckets
		List<Bucket> buckets = helper.listBuckets();

		// convert list of buckets to a map
		HashMap<String, Bucket> bucketMap = new HashMap<String, Bucket>();
		for (Bucket bucket : buckets) {
			bucketMap.put(bucket.getName(), bucket);
		}

		// verify bucket was found
		if (bucketMap.containsKey(bucketName)) {
			foundBucket = bucketMap.get(bucketName);
		}
		return foundBucket;
	}
	
	private List<S3ObjectSummary> listBucketObjects(String bucketName) throws Exception {
		List<S3ObjectSummary> contents = null;
		if (doesBucketExist(bucketName)) {
			this.currentBucket = getBucket(bucketName);
			contents = this.s3Helper.listObjects(bucketName);
			this.currentObjectList = contents;			
		}else {
			throw new Exception("No bucket named '" + bucketName + "' exists.");
		}
		return contents;
	}
	
	
	private List<S3ObjectSummary> listBucketObjects(String region, String bucketName) throws Exception {
		List<S3ObjectSummary> contents = null;
		if (doesBucketExist(region, bucketName)) {
			this.currentBucket = getBucket(bucketName);
			contents = this.s3Helper.listObjects(bucketName);
			this.currentObjectList = contents;			
		}else {
			throw new Exception("No bucket named '" + bucketName + "' exists.");
		}
		return contents;
	}

}
