package com.lohika.dos.backend.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.lohika.dos.backend.domain.Page;
import com.lohika.dos.backend.domain.PageList;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class PageService {
	public static final String PATH_BY_ID = "/page/{id}";
	public static final String ROOT = "/page";
	public static final String P_DOWNLOADED = "withDownloadedStatus";
	
	@PersistenceUnit(unitName = "MySql")
	EntityManagerFactory ef = Persistence.createEntityManagerFactory("MySql");
	
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/")
	public String hello()
	{
		return "hellow";
	}
	
	@PUT	
	@Produces(MediaType.APPLICATION_JSON)
	@Path(ROOT)
	public String saveOrUpdate(PageList pages)
	{
		EntityManager em = ef.createEntityManager();
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		for (Page page : pages.getPages()) {
			em.merge(page);	
		}		
		em.flush();
		txn.commit();
		return "{\"status\": \"ok\"}";
	}
	
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	@Path(PATH_BY_ID)
	public Page getById(@PathParam("id") String id)
	{
		EntityManager em = ef.createEntityManager();
		Page page = em.find(Page.class, id);
		return page;
	}
	
	@GET	
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/page")
	public PageList list(@QueryParam(P_DOWNLOADED) Boolean downloaded)
	{
		EntityManager em = ef.createEntityManager();
		String queryStr = "from Page ";
		if (downloaded != null)
		{
			queryStr += "where downloadedAt is "+(downloaded ? "not" : "") +" null"; 
		}
		Query query = em.createQuery(queryStr);
		query.setMaxResults(10);
		List<Page> results = query.getResultList();
		PageList response = new PageList();
		response.setPages(results);
		return response;
	}
}
