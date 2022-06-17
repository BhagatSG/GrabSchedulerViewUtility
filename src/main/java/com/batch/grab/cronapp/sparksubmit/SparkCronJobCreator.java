package com.batch.grab.cronapp.sparksubmit;

import com.batch.grab.cronapp.CronExecuteScheduler;
import com.batch.grab.cronapp.utils.CronConstants;

public class SparkCronJobCreator {

	public static void main(String[] args) {

		System.out.println("SparkSubmit CronJobCreator - START");
		try {
			CronExecuteScheduler.executeJob(CronConstants.OPTION_SPARKSUBMIT_REALTIME);
		} catch (Exception e) {
			System.out.println("Exception in SparkSubmit CronJobCreator");
			e.printStackTrace();
		}
		System.out.println("SparkSubmit CronJobCreator - END");
	}
}
