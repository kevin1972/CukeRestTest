package com.colbert.kevin.aws.helpers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.Bucket;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;

import java.io.File;

public class S3Helper {
	enum S3Action {
		GET, PUT, LIST, DELETE, DOWNLOAD
	}

	private AWSCredentials credentials;
	private AmazonS3 s3Client;
	private String region;

	public AmazonS3 getS3Client() {
		return s3Client;
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
	public Bucket createS3Bucket(String bucketName) {
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

	// ----------------------------------------------------------------
	// List S3 Buckets
	// ----------------------------------------------------------------
	public void listS3Buckets() {
		System.out.println("Listing buckets");
		for (Bucket bucket : this.s3Client.listBuckets()) {
			System.out.println(" - " + bucket.getName());
		}
		System.out.println();
	}

	// ----------------------------------------------------------------
	// Put Object
	// ----------------------------------------------------------------
	public void putObject(String bucketName, String key, File file) {
		try {
			System.out.printf("Putting the file '%s% into the bucket '%s' .\n", key, bucketName);
			this.s3Client.putObject(new PutObjectRequest(bucketName, key, file));
		} catch (AmazonServiceException ase) {
			AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
		}
	}

	// ----------------------------------------------------------------
	// Delete Object
	// ----------------------------------------------------------------
	public void deleteObject(String bucketName, String key) {
		try {
			System.out.printf("Deleting '%s% from the bucket '%s' .\n", key, bucketName);
			this.s3Client.deleteObject(bucketName, key);
		} catch (AmazonServiceException ase) {
			AwsHelperUtilities.printAmazonServiceExceptionErrorMessage(ase);
		}
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

	
}
