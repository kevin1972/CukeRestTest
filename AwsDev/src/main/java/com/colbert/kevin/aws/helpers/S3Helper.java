package com.colbert.kevin.aws.helpers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

import java.io.File;
import java.util.List;

public class S3Helper {
	enum S3Action {
		GET, PUT, LIST, DELETE, DOWNLOAD
	}

	final String prefix = "136559356037";
	private AWSCredentials credentials;
	private AmazonS3 s3Client;
	private String region;

	public AmazonS3 getS3Client() {
		return this.s3Client;
	}

	// ----------------------------------------------------------------
	// Constructor
	// ----------------------------------------------------------------
	public S3Helper(AWSCredentials credentials, String region) {
		this.credentials = credentials;
		this.region = region;
		try {
			this.s3Client = createS3Client(this.region);
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
	}

	// ----------------------------------------------------------------
	// Create S3 Client
	// ----------------------------------------------------------------
	private AmazonS3 createS3Client(String region) {
		AmazonS3 s3 = null;
		try {
			s3 = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(this.credentials))
					.withRegion(region).build();
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
		return s3;
	}

	// ----------------------------------------------------------------
	// Create S3 Bucket
	// ----------------------------------------------------------------
	public Bucket createBucket(String bucketName) {
		Bucket returnBucket = null;

		try {
			if (this.s3Client.doesBucketExistV2(bucketName)) {
				throw new AmazonServiceException("Bucket" + bucketName + " already exists.\n");
			} else {
				returnBucket = this.s3Client.createBucket(bucketName);
			}
		} catch (AmazonServiceException ase) {
			AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
		}
		return returnBucket;
	}
	
	
	public Bucket createBucket(String region, String bucketName) {		
		Bucket returnBucket = null;
		AmazonS3 client = null;
		if(region != this.s3Client.getRegionName()) {
			client =  AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(this.credentials))
					.withRegion(region).build();
		}else {
			client = this.s3Client;
		}
		try {
			if (client.doesBucketExistV2(bucketName)) {
				throw new AmazonServiceException("Bucket" + bucketName + " already exists.\n");
			} else {
				returnBucket = client.createBucket(bucketName);
			}
		} catch (AmazonServiceException ase) {
			AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
		}
		return returnBucket;
	}

	// ----------------------------------------------------------------
	// List S3 Buckets
	// ----------------------------------------------------------------
	public List<Bucket> listBuckets() {
		System.out.println("Listing buckets");
		List<Bucket> buckets = this.s3Client.listBuckets();
		for (Bucket bucket : buckets) {
			System.out.println(" - " + bucket.getName());
		}
		System.out.println();
		return buckets;
	}
	
	public List<Bucket> listBuckets(String region) {
		List<Bucket> buckets;;
		System.out.println("Listing buckets");
		if(region != s3Client.getRegionName()) {
			AmazonS3 tmpS3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(this.credentials))
					.withRegion(region).build();
			buckets = tmpS3Client.listBuckets();
		}else {
			buckets = this.s3Client.listBuckets();			
		}
		
		for (Bucket bucket : buckets) {
			System.out.println(" - " + bucket.getName());
		}
		System.out.println();
		return buckets;
	}

	// ----------------------------------------------------------------
	// List Objects in Bucket
	// ----------------------------------------------------------------
	public List<S3ObjectSummary> listObjects(String bucketName) {
		ListObjectsV2Result result = this.s3Client.listObjectsV2(bucketName);
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		System.out.printf("Objects in Bucket '%s'\n", bucketName);
		for (S3ObjectSummary os : objects) {
			System.out.println("* " + os.getKey());
		}
		return objects;
	}
	
	
	public List<S3ObjectSummary> listObjects(String region, String bucketName) {		
		ListObjectsV2Result result = null;
		AmazonS3 client = null;
		if(region != s3Client.getRegionName()) {
			client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(this.credentials))
					.withRegion(region).build();						
		}else {
			client = this.s3Client;
		}	
		result = client.listObjectsV2(bucketName);
		List<S3ObjectSummary> objects = result.getObjectSummaries();
		System.out.printf("Objects in Bucket '%s'\n", bucketName);
		for (S3ObjectSummary os : objects) {
			System.out.println("* " + os.getKey());
		}
		return objects;
	}

	// ----------------------------------------------------------------
	// Get Object
	// ----------------------------------------------------------------
	public void getObject(String bucketName, String key) {
		System.out.printf("Downloading the object '%s' from the bucket '%s'.\n", key, bucketName);
		try {
			S3Object object = this.s3Client.getObject(new GetObjectRequest(bucketName, key));
			System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
		} catch (AmazonServiceException ase) {
			AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
		}
	}
	
	
	public void getObject(String region, String bucketName, String key) {
		AmazonS3 client = null;
		if(region != this.s3Client.getRegionName()) {
			client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(this.credentials))
					.withRegion(region).build();
		}else {
			client = this.s3Client;
		}
		
		System.out.printf("Downloading the object '%s' from the bucket '%s'.\n", key, bucketName);
		try {
			S3Object object = client.getObject(new GetObjectRequest(bucketName, key));
			System.out.println("Content-Type: " + object.getObjectMetadata().getContentType());
		} catch (AmazonServiceException ase) {
			AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
		}
	}

	// ----------------------------------------------------------------
	// Put Object
	// ----------------------------------------------------------------
	public void putObject(String bucketName, String key, File file) {
		try {
			System.out.printf("Putting the file '%s' into the bucket '%s' .\n", key, bucketName);
			this.s3Client.putObject(new PutObjectRequest(bucketName, key, file));
		} catch (AmazonServiceException ase) {
			AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
		}
	}
	
	public void putObject(String region,String bucketName, String key, File file) {
		AmazonS3 client = null;
		if(region != this.s3Client.getRegionName()) {
			client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(this.credentials))
					.withRegion(region).build();
		}else {
			client = this.s3Client;
		}
		try {
			System.out.printf("Putting the file '%s' into the bucket '%s' .\n", key, bucketName);
			client.putObject(new PutObjectRequest(bucketName, key, file));
		} catch (AmazonServiceException ase) {
			AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
		}
	}

	// ----------------------------------------------------------------
	// Delete Object
	// ----------------------------------------------------------------
	public void deleteObject(String bucketName, String key) {
		try {
			System.out.printf("Deleting '%s from the bucket '%s' .\n", key, bucketName);
			this.s3Client.deleteObject(bucketName, key);
		} catch (AmazonServiceException ase) {
			AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
		}
	}
	
	
	public void deleteObject(String region, String bucketName, String key) {
		try {
			System.out.printf("Deleting '%s from the bucket '%s' .\n", key, bucketName);
			
			if(region != s3Client.getRegionName()) {
				AmazonS3 tmpS3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(this.credentials))
						.withRegion(region).build();
				tmpS3Client.deleteObject(bucketName, key);
			}else {
				this.s3Client.deleteObject(bucketName, key);
			}
			
		} catch (AmazonServiceException ase) {
			AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
		}
	}

}
