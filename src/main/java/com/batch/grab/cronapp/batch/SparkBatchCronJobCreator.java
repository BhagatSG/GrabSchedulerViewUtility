package com.batch.grab.cronapp.batch;

import com.batch.grab.cronapp.CronExecuteScheduler;
import com.batch.grab.cronapp.utils.CronConstants;

public class SparkBatchCronJobCreator {

	public static void main(String[] args) {

		System.out.println("SparkSubmit Batch CronJobCreator - START");
		try {
			CronExecuteScheduler.executeJob(CronConstants.OPTION_SPARKSUBMIT_BATCH);
		} catch (Exception e) {
			System.out.println("Exception in SparkSubmit Batch CronJobCreator");
			e.printStackTrace();
		}
		System.out.println("SparkSubmit Batch CronJobCreator - END");
	}
}
