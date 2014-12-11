package com.chteuchteu.blogmotion.obj;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class MusicPost {
	private long id;
	private String title;
	private String description;
	private String link;
	private String pubDate;
	private MusicPostType type;

	public enum MusicPostType { YOUTUBE, SOUNDCLOUD }

	public MusicPost(long id, String title, String description, String link, String pubDate) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.link = link;
		this.pubDate = pubDate;
		this.type = detectPostType();
	}

	public long getId() { return id; }
	public void setId(long id) { this.id = id; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }

	public String getDescription() { return description; }
	public void setDescription(String description) { this.description = description; }

	public String getLink() { return link; }
	public void setLink(String link) { this.link = link; }

	public String getPubDate() { return pubDate; }
	public void setPubDate(String pubDate) { this.pubDate = pubDate; }

	public MusicPostType getType() { return type; }
	public void setType(MusicPostType type) { this.type = type; }

	public MusicPostType detectPostType() {
		return this.description.contains("youtube") ? MusicPost.MusicPostType.YOUTUBE : MusicPost.MusicPostType.SOUNDCLOUD;
	}

	public String toString() {
		return this.title;
	}

	public String getTargetUrl() {
		Document jsoup = Jsoup.parse(this.description);
		return jsoup.getElementsByTag("iframe").get(0).attr("src");
	}
}
