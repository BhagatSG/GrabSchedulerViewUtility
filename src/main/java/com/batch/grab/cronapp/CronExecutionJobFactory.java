package com.batch.grab.cronapp;

import com.batch.grab.cronapp.batch.SparkBatchCronExecuteJob;
import com.batch.grab.cronapp.sparksubmit.SparkCronExecuteJob;
import com.batch.grab.cronapp.utils.CronConstants;



public class CronExecutionJobFactory {

	public static CronExecuteJobBase getProcessTypeExecutionJob(String option)
			throws Exception {

		if (option.equals(CronConstants.OPTION_SPARKSUBMIT_REALTIME)) {
			return new SparkCronExecuteJob();
		} else if (option.equals(CronConstants.OPTION_SPARKSUBMIT_BATCH)) {
			return new SparkBatchCronExecuteJob();
		}
		return null;

	}
}
