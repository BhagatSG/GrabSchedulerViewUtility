package com.batch.grab.cronapp;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public abstract class CronExecuteJobBase implements Job {

	//common variables
	protected String option;
		
	public CronExecuteJobBase(String option) {
		System.out.println("Base class - CronExecuteJobBase - inside constructor - ");
		this.option = option;
	}

	@SuppressWarnings("rawtypes")
	public abstract Class getCronExecuteJobClass();

	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		System.out.println("CronExecuteJobBase - execute");
	}

}


