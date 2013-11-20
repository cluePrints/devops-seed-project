package com.lohika.dos.backend.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pages")
public class Page {
	@Id
	private String id;
	private String url;
	private String content;
	private Date foundAt;
	private Date downloadedAt;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getFoundAt() {
		return foundAt;
	}
	public void setFoundAt(Date foundAt) {
		this.foundAt = foundAt;
	}
	public Date getDownloadedAt() {
		return downloadedAt;
	}
	public void setDownloadedAt(Date downloadedAt) {
		this.downloadedAt = downloadedAt;
	}
	@Override
	public String toString() {
		return "Page [url=" + url + ", contentSize=" + (content == null ? null : content.length())
				+ ", foundAt=" + foundAt + ", downloadedAt=" + downloadedAt
				+ "]";
	}	
}
