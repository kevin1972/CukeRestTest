package com.colbert.kevin.aws.helpers;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;
import com.amazonaws.services.cloudwatch.model.ComparisonOperator;
import com.amazonaws.services.cloudwatch.model.DeleteAlarmsRequest;
import com.amazonaws.services.cloudwatch.model.DeleteAlarmsResult;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsRequest;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsResult;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.DisableAlarmActionsRequest;
import com.amazonaws.services.cloudwatch.model.DisableAlarmActionsResult;
import com.amazonaws.services.cloudwatch.model.EnableAlarmActionsRequest;
import com.amazonaws.services.cloudwatch.model.EnableAlarmActionsResult;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.ListMetricsResult;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.amazonaws.services.cloudwatch.model.MetricAlarm;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricAlarmResult;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.cloudwatch.model.Statistic;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEvents;
import com.amazonaws.services.cloudwatchevents.AmazonCloudWatchEventsClientBuilder;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequest;
import com.amazonaws.services.cloudwatchevents.model.PutEventsRequestEntry;
import com.amazonaws.services.cloudwatchevents.model.PutEventsResult;
import com.amazonaws.services.cloudwatchevents.model.PutRuleRequest;
import com.amazonaws.services.cloudwatchevents.model.PutRuleResult;
import com.amazonaws.services.cloudwatchevents.model.PutTargetsRequest;
import com.amazonaws.services.cloudwatchevents.model.PutTargetsResult;
import com.amazonaws.services.cloudwatchevents.model.RuleState;
import com.amazonaws.services.cloudwatchevents.model.Target;

public class CloudWatchHelper {
	private AWSCredentials credentials;
	private AmazonCloudWatch cloudWatchClient;
	private AmazonCloudWatchEvents cloudWatchEventsClient;
	private String region;

	public AmazonCloudWatch getCloudWatchClient() {
		return cloudWatchClient;
	}
	
	public AmazonCloudWatchEvents getCloudWatchEventsClient() {
		return cloudWatchEventsClient;
	}

	// ----------------------------------------------------------------
	// Constructor
	// ----------------------------------------------------------------
	public CloudWatchHelper(AWSCredentials credentials, String region) {
		this.credentials = credentials;
		this.region = region;
		try {
			this.cloudWatchClient = createCloudWatchClient(this.region);
			this.cloudWatchEventsClient = createCloudWatchEventsClient(this.region);
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
	}

	// ----------------------------------------------------------------
	// Create CloudWatch Client
	// ----------------------------------------------------------------
	private AmazonCloudWatch createCloudWatchClient(String region) {
		AmazonCloudWatch cloudWatchClient = null;
		try {
			cloudWatchClient = AmazonCloudWatchClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(this.credentials)).build();
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
		return cloudWatchClient;
	}

	// ----------------------------------------------------------------
	// Create CloudWatch Events Client
	// ----------------------------------------------------------------
	private AmazonCloudWatchEvents createCloudWatchEventsClient(String region) {
		AmazonCloudWatchEvents cloudWatchEventsClient = null;
		try {
			cloudWatchEventsClient = AmazonCloudWatchEventsClientBuilder.standard()
					.withCredentials(new AWSStaticCredentialsProvider(this.credentials)).build();
		} catch (AmazonClientException ace) {
			AwsHelperUtilities.printAmazonClientExceptionErrorMessage(ace);
		}
		return cloudWatchEventsClient;
	}

	// ----------------------------------------------------------------
	// List Metrics
	// ----------------------------------------------------------------
	public ListMetricsResult listMetrics(String name, String namespace) {
		ListMetricsRequest request = new ListMetricsRequest().withMetricName(name).withNamespace(namespace);

		boolean done = false;

		ListMetricsResult response = null;
		while (!done) {
			response = this.cloudWatchClient.listMetrics(request);

			for (Metric metric : response.getMetrics()) {
				System.out.printf("Retrieved metric %s", metric.getMetricName());
			}

			request.setNextToken(response.getNextToken());

			if (response.getNextToken() == null) {
				done = true;
			}
		}
		return response;
	}

	// ----------------------------------------------------------------
	// Create Metric
	// ----------------------------------------------------------------
	public PutMetricDataResult createMetric(String namespace, MetricDatum datum) {
		// Dimension dimension = new
		// Dimension().withName("UNIQUE_PAGES").withValue("URLS");

		// MetricDatum datum = new
		// MetricDatum().withMetricName("PAGES_VISITED").withUnit(StandardUnit.None)
		// .withValue(data_point).withDimensions(dimension);

		PutMetricDataRequest request = new PutMetricDataRequest().withNamespace(namespace).withMetricData(datum);

		PutMetricDataResult response = this.cloudWatchClient.putMetricData(request);

		return response;
	}

	// ----------------------------------------------------------------
	// Create an Alarm
	// ----------------------------------------------------------------
	// TODO:Refactor to read in an alarm description
	public PutMetricAlarmResult createAlarm(String instanceId, String alarmName, String alarmDescription) {
		Dimension dimension = new Dimension().withName("InstanceId").withValue(instanceId);

		PutMetricAlarmRequest request = new PutMetricAlarmRequest().withAlarmName(alarmName)
				.withComparisonOperator(ComparisonOperator.GreaterThanThreshold).withEvaluationPeriods(1)
				.withMetricName("CPUUtilization").withNamespace("AWS/EC2").withPeriod(60)
				.withStatistic(Statistic.Average).withThreshold(70.0).withActionsEnabled(false)
				.withAlarmDescription(alarmDescription).withUnit(StandardUnit.Seconds).withDimensions(dimension);
		PutMetricAlarmResult response = this.cloudWatchClient.putMetricAlarm(request);
		return response;
	}

	// ----------------------------------------------------------------
	// List Alarms
	// ----------------------------------------------------------------
	public DescribeAlarmsResult listAlarms() {
		boolean done = false;
		DescribeAlarmsResult response = null;
		DescribeAlarmsRequest request = new DescribeAlarmsRequest();

		while (!done) {
			response = this.cloudWatchClient.describeAlarms(request);

			for (MetricAlarm alarm : response.getMetricAlarms()) {
				System.out.printf("Retrieved alarm %s", alarm.getAlarmName());
			}

			request.setNextToken(response.getNextToken());
			if (response.getNextToken() == null) {
				done = true;
			}
		}
		return response;
	}

	// ----------------------------------------------------------------
	// Delete Alarm
	// ----------------------------------------------------------------
	public DeleteAlarmsResult deleteAlarm(String alarmName) {
		DeleteAlarmsRequest request = new DeleteAlarmsRequest().withAlarmNames(alarmName);
		DeleteAlarmsResult response = this.cloudWatchClient.deleteAlarms(request);
		return response;
	}

	// ----------------------------------------------------------------
	// Enable Alarm Action
	// ----------------------------------------------------------------
	public EnableAlarmActionsResult enableAlarmAction(String alarmName) {
		EnableAlarmActionsRequest request = new EnableAlarmActionsRequest().withAlarmNames(alarmName);
		EnableAlarmActionsResult response = this.cloudWatchClient.enableAlarmActions(request);
		return response;
	}

	// ----------------------------------------------------------------
	// Disable Alarm Action
	// ----------------------------------------------------------------
	public DisableAlarmActionsResult disbleAlarmAction(String alarmName) {
		DisableAlarmActionsRequest request = new DisableAlarmActionsRequest().withAlarmNames(alarmName);
		DisableAlarmActionsResult response = this.cloudWatchClient.disableAlarmActions(request);
		return response;
	}

	// ----------------------------------------------------------------
	// Add Event
	// ----------------------------------------------------------------
	// TODO: Refactor to read an object with the request info
	public PutEventsResult addEvent(String eventDetails, String resourceArn) {		
		PutEventsRequestEntry request_entry = new PutEventsRequestEntry().withDetail(eventDetails)
				.withDetailType("sampleSubmitted").withResources(resourceArn)
				.withSource("aws-sdk-java-cloudwatch-example");

		PutEventsRequest request = new PutEventsRequest().withEntries(request_entry);

		PutEventsResult response = this.cloudWatchEventsClient.putEvents(request);
		return response;
	}

	// ----------------------------------------------------------------
	// Add Rule
	// ----------------------------------------------------------------
	public PutRuleResult addRule(String ruleName, String roleArn, String scheduleExpression, RuleState ruleState) {		
		PutRuleRequest request = new PutRuleRequest().withName(ruleName).withRoleArn(roleArn)
				.withScheduleExpression(scheduleExpression).withState(ruleState);

		PutRuleResult response = this.cloudWatchEventsClient.putRule(request);
		return response;
	}

	// ----------------------------------------------------------------
	// Add Target
	// ----------------------------------------------------------------
	public PutTargetsResult addTarget(String functionArn, String targetId, String ruleName) {		
		Target target = new Target().withArn(functionArn).withId(targetId);
		PutTargetsRequest request = new PutTargetsRequest().withTargets(target).withRule(ruleName);
		PutTargetsResult response = this.cloudWatchEventsClient.putTargets(request);
		return response;
	}

}
