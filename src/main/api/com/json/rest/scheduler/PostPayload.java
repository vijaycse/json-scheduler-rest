package com.json.rest.scheduler;

import java.util.HashMap;
import java.util.Map;

import org.mule.api.MuleException;
import org.mule.api.MuleMessage;
import org.mule.api.client.LocalMuleClient;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class PostPayload implements Job{

	private static final Object LIST_PAYLOAD = "lat=43.6406009&amp;lon=-79.3972214";
	
	
	
	public void execute(JobExecutionContext context)
	throws JobExecutionException {
		LocalMuleClient client =(LocalMuleClient) context.getJobDetail().getJobDataMap().get("client");
		MuleMessage result = null;
		try {
			result = client.send("http://api.openweathermap.org/data/2.5/weather?", 
					LIST_PAYLOAD, setHTTPHeaders());
			System.out.println(" result from http post " +result.getPayloadAsString() );
		} catch (MuleException e) {
			System.out.println("Mule exception occured in queue list fetch  " + e.getMessage());
		}
		catch (Exception e) {
			System.out.println("exception occured in queue list fetch  " + e.getMessage());
		}
 
	}
 
	private final Map<String, Object> setHTTPHeaders(){
		Map<String, Object> props = new HashMap<String,Object>();
		props.put("Content-type", "application/json");
		props.put("Accept", "application/json");
		props.put("http.method", "POST");
		return props;
	}


}
