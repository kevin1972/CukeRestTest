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
#
#---------------------------------------------------------------------------		
@Tested
Scenario: Create a Bucket 
	When the user creates a bucket named "10301972-kevin-dev-test-bucket" in the given region 
	Then a new bucket called "10301972-kevin-dev-test-bucket" should be displayed in the list of buckets
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------		
@NotTested
Scenario Outline: CreateBucketUsingTable
	When the user creates a bucket as described below:
	|Region		|BucketName		|
	|<Region>	|<BucketName>	|
		
	Then a new bucket called "<BucketName>" should be displayed in the list of buckets
	
Examples:
	|Region		|BucketName						|
	|us-east-2	|10301972-2-kevins-dev-bucket	|
	
	 	 
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------
@Tested
Scenario: Get List of Files in a Bucket 

	When the user gets a list of the objects in the bucket named "kevins-dev-bucket" 
	
	Then the bucket named "kevins-dev-bucket" should contain the following objects: 
		|ObjectName		|
		|HelloWorld.zip	|
		|PA310786.JPG	|
		
				
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------
@NotTested
Scenario: UploadFileWithGivenPathAndKey 
	Given the following information: 
		| Name     | Value 										|
		| filePath | H:\Development\Testing\TestTextFile.txt   	|
		| fileKey  | TestTextFile.txt   						|
		
	When the user uploads the file with the given path and key 
	
	Then the file should appear in the list of files in the bucket. 
	
##---------------------------------------------------------------------------
#
##---------------------------------------------------------------------------
@Tested
Scenario Outline: UploadFileExplicitly
	When the user uploads the file with the path "<FilePath>" and the key "<FileKey>" into the bucket named "<BucketName>" in the region "<Region>" 	
	Then the bucket named "<BucketName>" in the region "<Region>" should contain the key "<FileKey>" 

Examples:
	|FileKey			|FilePath									|BucketName			|Region		|
	|TestTextFile.txt	|H:\Development\Testing\TestTextFile.txt	|kevins-dev-bucket	|us-east-2	| 	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------
@Tested
Scenario Outline: UploadFileUsingTable 

	Given a bucket named "<BucketName>" exists in the region
		
	When the user uploads file(s) as described below: 
		|Bucket			|Key					|FilePath									|
		|<BucketName>	|<FileKey>				|<FilePath>									|
		
	Then the bucket named "<BucketName>" should contain the following objects: 
		|ObjectName			|
		|<FileKey>			|
							
	#TODO: #And the bucket should contain all of the existing files in addition to the files that were uploaded.

Examples:
	|BucketName			|FileKey			|FilePath									|
	|kevins-dev-bucket	|TestTextFile3.txt	|H:\Development\Testing\TestTextFile3.txt	| 
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------
@Tested
Scenario: DeleteAFileUsingGivenOnly 
	Given the following information: 
		| Name     | Value 										|
		| filePath | H:\Development\Testing\TestTextFile.txt   	|
		| fileKey  | TestTextFile.txt   						|
		
	
	When the user uploads the file with the given path and key 	
	Then the file should appear in the list of files in the bucket. 
				
	When the user deletes the file with the given key 	
	Then the bucket should not contain the file that was deleted 
	
	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------
@Tested
Scenario Outline: DeleteAFileExplicitly 
	Given the following information: 
		| Name     | Value 										|
		| filePath | <FilePath>								   	|
		| fileKey  | <FileKey>			   						|
		
	
	When the user uploads the file with the path "<FilePath>" and the key "<FileKey>" into the bucket named "<BucketName>" in the region "<Region>" 	
	Then the bucket named "<BucketName>" in the region "<Region>" should contain the key "<FileKey>" 

	When the user deletes the file named "<FileKey>" from the bucket named "<BucketName>" in the region "<Region>" 	
	Then the bucket named "<BucketName>" in the region "<Region>" should not contain the key "<FileKey>" 

Examples:
	|FileKey			|FilePath									|BucketName			|Region		|
	|TestTextFile.txt	|H:\Development\Testing\TestTextFile4.txt	|kevins-dev-bucket	|us-east-2	|

	
#---------------------------------------------------------------------------
#
#---------------------------------------------------------------------------
@RunMe
Scenario Outline: DeleteAFileUsingATable 


	When the user uploads file(s) as described below: 
		|BucketName			|Key					|FilePath									|
		|<BucketName>		|<FileKey>				|<FilePath>									|
		
	Then the bucket named "<BucketName>" should contain the following objects: 
		|ObjectName			|
		|<FileKey>			|
		

	When the user deletes the file(s) as described below: 
		|Region				|BucketName		|FileKey		|
		|<Region>			|<BucketName>	|<FileKey>		|
		
		
	Then a file with the following description should not be present: 
		|Region				|BucketName		|FileKey		|
		|<Region>			|<BucketName>	|<FileKey>		|

Examples:
	|FileKey			|FilePath									|BucketName			|Region		|
	|TestTextFile.txt	|H:\Development\Testing\TestTextFile2.txt	|kevins-dev-bucket	|us-east-2	|			