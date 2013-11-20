package com.lohika.dos.client;

import java.net.InetAddress;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.RateLimiter;
import com.lohika.dos.backend.domain.Page;


public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);
	private static Random random = new Random();
	
	public static void main(String[] args) throws Exception {
		if (args.length == 0)
		{
			System.out.println("Please pass base service url as a first parameter, e.g. http://localhost:8080/service");
			return;
		}
		
		// prepare stuff
		PageClient pageClient = new PageClient(args[0]);		
		String hostName = InetAddress.getLocalHost().getHostName();
		int requestsPerSecond = 2;
		try {
			requestsPerSecond = Integer.parseInt(args[1]);
		} catch (Exception e) {
		}
		
		// generate workload
		RateLimiter limiter = RateLimiter.create(requestsPerSecond);
		while (true)
		{
			limiter.acquire();
			try {				
				logger.debug("Checking the server status.");
				List<Page> actionable = pageClient.listRequiringAction();				
				boolean shouldWeAddSomeWork = actionable.size() < random.nextInt(50);
				if (shouldWeAddSomeWork)
				{
					addWorkToDo(pageClient, hostName);					
				} else {
					doWork(pageClient, actionable);
				}				
			} catch (Exception e) {
				logger.error("Something wrong had happened", e);
			}			
		}		
	}

	private static void doWork(PageClient pageClient, List<Page> actionable) {		
		Collections.shuffle(actionable);
		Page page = actionable.get(0);		
		page.setDownloadedAt(new Date());
		page.setContent(RandomStringUtils.randomAlphanumeric(1024));
		logger.debug("Uploading page #{}", page.getId());
		pageClient.saveOrUpdate(page);
		logger.debug("Uploaded page #{}", page.getId());
	}

	private static void addWorkToDo(PageClient pageClient, String hostName) {
		Page page = new Page();
		page.setId(UUID.randomUUID().toString());		
		page.setFoundAt(new Date());
		page.setUrl(hostName + "/" + page.getId());
		logger.debug("Adding page #{}", page.getId());
		pageClient.saveOrUpdate(page);
		logger.debug("Added page #{}", page.getId());
	}
}
