package com.chteuchteu.blogmotion.obj;

public abstract class BasePost {
	protected long id;
	protected String title;
	protected String publishDate;
	protected String permalink;

	public BasePost(String title, String publishDate, String permalink) {
		this.title = title;
		this.publishDate = publishDate;
		this.permalink = permalink;
	}

	public long getId() { return id; }
	public void setId(long id) { this.id = id; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getPublishDate() { return publishDate; }
	public void setPublishDate(String publishDate) { this.publishDate = publishDate; }

	public String getPermalink() { return permalink; }
	public void setPermalink(String permalink) { this.permalink = permalink; }
}
