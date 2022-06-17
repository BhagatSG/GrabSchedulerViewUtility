package com.batch.grab.cronapp.batch;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.batch.grab.cronapp.CronExecuteJobBase;
import com.batch.grab.cronapp.utils.CronConstants;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public class SparkBatchCronExecuteJob extends CronExecuteJobBase {

	public SparkBatchCronExecuteJob() {
		super(CronConstants.OPTION_SPARKSUBMIT_BATCH);
		System.out
				.println("Spark Submit Batch cron Execution - inside constructor");
	}

	public void execute(JobExecutionContext args) throws JobExecutionException {
		System.out.println("Spark Submit cron Batch Execution - START");

		try {

			Properties prop = new Properties();
			String resourceName = "filetransferconfig.properties";
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			try (InputStream in = loader.getResourceAsStream(resourceName)) {
				prop.load(in);
				in.close();
			}

			AWSCredentials credentials = new BasicAWSCredentials(
					prop.getProperty(CronConstants.PROPKEY_AWS_ACCESSKEY),
					prop.getProperty(CronConstants.PROPKEY_AWS_SECRETACCESSKEY));

			AmazonS3 s3client = new AmazonS3Client(credentials);
			s3client.setRegion(Region.getRegion(Regions.US_WEST_2));

			ObjectListing objectListing = s3client
					.listObjects(new ListObjectsRequest().withBucketName(prop
							.getProperty(CronConstants.PROPKEY_S3_BATCH_BUCKETNAME)));

			System.out.println("<==========================Cron Invoked time=========================>"+new Date().toString());
			if (objectListing.getObjectSummaries().size() > 0) {
				System.out.println("Invoking spark submit Batch command");
				new SparkSubmitBatchApp().invokeSparkSubmitBatchCmd();
			} else {
				System.out.println("There are no data feeds from S3 bucket to process!!!");
			}

		} catch (Exception e) {
			System.out.println("Exception in SparkSubmitBatchApp class");
			e.printStackTrace();
		}
		System.out.println("Spark Submit Batch cron Execution job - END");

	}

	@SuppressWarnings("rawtypes")
	public Class getCronExecuteJobClass() {
		return this.getClass();
	}
}
