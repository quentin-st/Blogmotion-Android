package com.chteuchteu.blogmotion.obj;

import android.graphics.Bitmap;

import com.chteuchteu.blogmotion.hlpr.YoutubeHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Collections;
import java.util.List;

public class Post {
	private long id;
	private String title;
	private String publishDate;
	private String permalink;
	private List<String> categories;
	private String description;
	private String content;

	private Bitmap previewImage;

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
	public boolean hasPreviewImage() { return this.previewImage != null; }
	public Bitmap getPreviewImage() { return previewImage; }
	public void setPreviewImage(Bitmap previewImage) { this.previewImage = previewImage; }

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

		Collections.addAll(categories, str.split(","));
	}

	/**
	 * Returns the first image found in the artice content
	 */
	public String getImageUrl() {
		if (this.content.equals(""))
			return "";

		Document doc = Jsoup.parse(this.content);
		Elements images = doc.getElementsByTag("img");

		if (images.size() > 0)
			return images.get(0).attr("src");

		// Check if there is a youtube video in it
		if (this.content.contains("youtube.com")) {
			Elements iframeTargets = doc.getElementsByTag("iframe");

			for (Element elem : iframeTargets) {
				if (elem.attr("src").contains("youtube.com"))
					return YoutubeHelper.getPreviewImageUrl(elem.attr("src"));
			}
		}

		return "";
	}
}
