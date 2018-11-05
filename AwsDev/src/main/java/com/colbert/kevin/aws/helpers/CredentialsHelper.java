package com.colbert.kevin.aws.helpers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;

public class CredentialsHelper {
	//----------------------------------------------------------------
		//----------------------------------------------------------------
		public AWSCredentials getAwsCredentials() {
			AWSCredentials credentials = null;
			try {
				credentials = new ProfileCredentialsProvider("default").getCredentials();
			} catch (Exception e) {
				throw new AmazonClientException("Cannot load the credentials from the credential profiles file. "
						+ "Please make sure that your credentials file is at the correct "
						+ "location (C:\\Users\\kevin\\.aws\\credentials), and is in valid format.", e);
			}
			return credentials;
		}

}
