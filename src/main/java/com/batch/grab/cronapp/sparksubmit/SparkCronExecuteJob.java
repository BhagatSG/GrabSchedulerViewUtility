package com.batch.grab.cronapp.sparksubmit;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.batch.grab.cronapp.CronExecuteJobBase;
import com.batch.grab.cronapp.utils.CronConstants;

public class SparkCronExecuteJob extends CronExecuteJobBase {

	public SparkCronExecuteJob() {
		super(CronConstants.OPTION_SPARKSUBMIT_REALTIME);
		System.out.println("Spark Submit Real Time cron Execution - inside constructor");
	}

	public void execute(JobExecutionContext args) throws JobExecutionException {
		System.out.println("Spark Submit Real Time cron Execution - START");

		try {
			new SparkSubmitApp().invokeSparkSubmitCmd();
		} catch (Exception e) {
			System.out.println("Exception in SparkSubmitApp class");
			e.printStackTrace();
		}
		System.out.println("Spark Submit Real Time cron Execution job - END");

	}

	@SuppressWarnings("rawtypes")
	public Class getCronExecuteJobClass() {
		return this.getClass();
	}
}
