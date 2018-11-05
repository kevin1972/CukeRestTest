package com.colbert.kevin.aws.helpers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;

public class AwsHelperUtilities {
	// ----------------------------------------------------------------
	// Exception Message Printers
	// ----------------------------------------------------------------
	public static void printAmazonServiceExceptionErrorMessage(AmazonServiceException ase) {
		System.out.println("Caught an AmazonServiceException, which means your request made it "
				+ "to Amazon S3, but was rejected with an error response for some reason.");
		System.out.println("Error Message:    " + ase.getMessage());
		System.out.println("HTTP Status Code: " + ase.getStatusCode());
		System.out.println("AWS Error Code:   " + ase.getErrorCode());
		System.out.println("Error Type:       " + ase.getErrorType());
		System.out.println("Request ID:       " + ase.getRequestId());
	}

	public static void printAmazonClientExceptionErrorMessage(AmazonClientException ace) {
		System.out.println("Caught an AmazonClientException, which means the client encountered "
				+ "a serious internal problem while trying to communicate with S3, "
				+ "such as not being able to access the network.");
		System.out.println("Error Message: " + ace.getMessage());
	}

}
