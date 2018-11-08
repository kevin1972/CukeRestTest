Feature: S3SmokeTests 
	In order to maintain a collection of items
  As an Amazon S3 user
  I want to create, get, update, and delete resources in S3

Background: 
	Given the following information: 
		| Name   	| Value     		|
		| region 	| us-east-2 		|
		| bucketName| kevins-dev-bucket	|
		
		
#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
#  Scenario: Create a Bucket       
#  When the user creates a bucket named "10301972-kevin-dev-test-bucket" in the given region
#  Then a new bucket called "10301972-kevin-dev-test-bucket" should be displayed in the list of buckets



#---------------------------------------------------------------------------
#---------------------------------------------------------------------------
  Scenario: Get List of Files in a Bucket
    
   When the user gets a list of the objects in the bucket named "kevins-dev-bucket"
   
   Then the bucket named "kevins-dev-bucket" should contain the following objects:
   |ObjectName		|
   |HelloWorld.zip	|
   |PA310786.JPG	|
		
		
		
##---------------------------------------------------------------------------
##
##---------------------------------------------------------------------------
#Scenario: UploadFileWithGivenPathAndKey 
#	Given the following information: 
#		| Name     | Value 										|
#		| filePath | H:\Development\Testing\TestTextFile.txt   	|
#		| fileKey  | TestTextFile.txt   						|
#		
#		When the user uploads the file with the given path and name
		
		
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------
#Scenario: UploadFileUsingTable
# 
#	Given a bucket named "kevins-dev-bucket" exists in the region 
#		
#	When the user uploads file(s) as described below:
#	|BucketName			|Key					|FilePath									|
#	|kevins-dev-bucket	|TestTextFile2.txt		|H:\Development\Testing\TestTextFile2.txt	|
#		
#	Then the file should appear in the list of files in the bucket.
	
#	Then the bucket should contain all of the existing files in addition to the files that were uploaded. 
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------
#Scenario: Delete a File
#	Given the following information: 
#		| Name     | Value 										|		
#		| fileKey  | TestTextFile.txt   						|
#		
#	#When the user deletes the file with the given key
#	
#	#When the user deletes the file named "" from the bucket named ""
#	
#	When the user deletes the file(s) as described below:
#	|Region		|BucketName		|FileKey		|
#	|			|				|				|
#		
#	Then the bucket should not contain the file that was deleted
#	
#	Then the bucket named "" in the region "" should not contain the key ""
#	
#	Then a file with the following description should not be present:
#		|Region		|BucketName		|FileKey		|
#		|			|				|				|
			