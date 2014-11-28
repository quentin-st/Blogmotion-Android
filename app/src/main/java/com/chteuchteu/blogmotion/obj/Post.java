package com.chteuchteu.blogmotion.obj;

import java.util.List;

public class Post {
	private long id;
	private String title;
	private String publishDate;
	private String permalink;
	private List<String> categories;
	private String description;
	private String content;

	public Post(long id, String title, String permalink, String publishDate,
	            List<String> categories, String description, String content) {
		this.id = id;
		this.title = title;
		this.permalink = permalink;
		this.publishDate = publishDate;
		this.categories = categories;
		this.description = description;
		this.content = content;
	}

	public Post(long id, String title, String permalink, String publishDate,
	            String categories, String description, String content) {
		this.id = id;
		this.title = title;
		this.permalink = permalink;
		this.publishDate = publishDate;
		this.setCategories(categories);
		this.description = description;
		this.content = content;
	}

	public void setId(long val) { this.id = val; }
	public long getId() { return this.id; }
	public String getTitle() { return this.title; }
	public String getPermalink() { return this.permalink; }
	public String getPublishDate() { return this.publishDate; }
	public List<String> getCategories() { return this.categories; }
	public String getDescription() { return this.description; }
	public String getContent() { return this.content; }

	public String toString() { return this.title; }

	public String getCategoriesAsString() {
		String str = "";
		for (String category : categories)
			str += category.replaceAll(",", "");

		if (str.contains(","))
			str = str.substring(str.length()-1);

		return str;
	}
	public void setCategories(String str) {
		if (str.equals(""))
			return;

		for (String s : str.split(","))
			categories.add(s);
	}
}
