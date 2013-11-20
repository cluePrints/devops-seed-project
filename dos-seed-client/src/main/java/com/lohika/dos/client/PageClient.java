package com.lohika.dos.client;

import java.util.Arrays;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.JerseyClientBuilder;

import com.lohika.dos.backend.domain.Page;
import com.lohika.dos.backend.domain.PageList;
import com.lohika.dos.backend.service.PageService;

public class PageClient {
	private final String baseUrl;
	private final Client newClient;
	
	public PageClient(String baseUrl) {
		super();
		this.baseUrl = baseUrl;
		this.newClient = JerseyClientBuilder.newClient();
	}
	
	public List<Page> listRequiringAction()
	{
		return list(false);
	}
	
	public List<Page> list(Boolean downloaded)
	{
		WebTarget path = newClient
				.target(baseUrl)				
				.path(PageService.ROOT);
		if (downloaded != null)
		{
			path.queryParam(PageService.P_DOWNLOADED, downloaded);
		}
		Response response = path.request()
			.accept(MediaType.APPLICATION_JSON)
		.buildGet()
		.invoke();
		PageList responseList = response.readEntity(PageList.class);
		return responseList.getPages();
	}
	
	public void saveOrUpdate(Page... pages)
	{		
		PageList requestEntity = new PageList();
		requestEntity.setPages(Arrays.asList(pages));
		newClient
				.target(baseUrl)				
				.path(PageService.ROOT).request()
		.buildPut(Entity.entity(requestEntity, MediaType.APPLICATION_JSON))
		.invoke();		
	}
}
