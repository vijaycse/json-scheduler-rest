package com.json.rest.scheduler;

import java.util.Date;

import org.mule.api.MuleContext;
import org.mule.api.annotations.expressions.Lookup;
import org.mule.api.context.MuleContextAware;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;



public class MyScheduler implements MuleContextAware{
	@Lookup
	private MuleContext muleContext;
	private String repeatCount="0";
	private String duration="0";

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(String repeatCount) {
		this.repeatCount = repeatCount;
	}

	public String  processPayload(String payload){
		System.out.println(" scheduler " + payload);
		JobDetail job = new JobDetail();
		job.setName("dummyJobName");
		job.setJobClass(PostPayload.class);
		
		JobDataMap jobDataMap = new JobDataMap();
		jobDataMap.put("client", muleContext.getClient());
		
		job.setJobDataMap(jobDataMap);

		//configure the scheduler time
		SimpleTrigger trigger = new SimpleTrigger();
		trigger.setName("flow");
		trigger.setStartTime(new Date(System.currentTimeMillis() + 1000));
		trigger.setRepeatCount(Integer.parseInt(repeatCount));
		trigger.setRepeatInterval(Integer.parseInt(this.duration));

		//schedule it
		Scheduler scheduler;
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return payload;

	}

	public void setMuleContext(MuleContext context) {
	this.muleContext=context;
		
	}

}
