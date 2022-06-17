package com.batch.grab.cronapp;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import com.batch.grab.cronapp.utils.CronConstants;

import java.io.InputStream;
import java.util.Properties;

public class CronExecuteScheduler {
	
	public static void executeJob(String option) throws Exception {

		try
		{
			System.out.println("CronExecuteScheduler - START");

				String interval = null;

				Properties prop = new Properties();		
				String resourceName = "filetransferconfig.properties";
				ClassLoader loader = Thread.currentThread().getContextClassLoader();
				try(InputStream in = loader.getResourceAsStream(resourceName)) {
					prop.load(in);
					in.close();
				}
				

				if(option.equals(CronConstants.OPTION_SPARKSUBMIT_REALTIME)){
					interval = prop.getProperty(CronConstants.PROPKEY_SPARK_REALTIME_CRON_INTERVAL);
				}else if(option.equals(CronConstants.OPTION_SPARKSUBMIT_BATCH)){
					interval = prop.getProperty(CronConstants.PROPKEY_SPARK_BATCH_CRON_INTERVAL);
				} else{
			        interval = prop.getProperty(CronConstants.PROPKEY_INTERVAL);
				}
				System.out.println("Time interval =="+interval);
				
				@SuppressWarnings("unchecked")
				JobDetail job = JobBuilder
						.newJob(CronExecutionJobFactory.getProcessTypeExecutionJob(option).
								getCronExecuteJobClass())
								.withIdentity(option)
								.build();

					Trigger trigger = TriggerBuilder
							.newTrigger()
							.withIdentity(option)
							.withSchedule(
									SimpleScheduleBuilder
											.simpleSchedule()
											.withIntervalInMinutes(Integer.parseInt(interval))
											.repeatForever()).build();

					Scheduler scheduler = new StdSchedulerFactory().getScheduler();
					scheduler.start();
					scheduler.scheduleJob(job, trigger);

					System.out.println("CronExecuteScheduler - END");

			
		}catch(Exception e){

			System.out.println("Exception in Cron Execute Scheduler");
			e.printStackTrace();
		}
	}
}
