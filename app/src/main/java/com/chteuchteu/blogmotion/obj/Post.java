package com.chteuchteu.blogmotion.obj;

import java.util.List;

public class Post {
	private long id;
	private String title;
	private String permalink;
	private List<String> categories;
	private String description;
	private String content;

	public Post(long id, String title, String permalink, List<String> categories, String description, String content) {
		this.id = id;
		this.title = title;
		this.permalink = permalink;
		this.categories = categories;
		this.description = description;
		this.content = content;
	}

	public long getId() { return this.id; }
	public String getTitle() { return this.title; }
	public String getPermalink() { return this.permalink; }
	public List<String> getCategories() { return this.categories; }
	public String getDescription() { return this.description; }
	public String getContent() { return this.content; }

	public String toString() { return this.title; }
}
