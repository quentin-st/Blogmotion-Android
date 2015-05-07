package com.chteuchteu.blogmotion.obj;

import android.graphics.Bitmap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MusicPost {
	private long id;
	private String title;
	private String targetUrl;
	private String pubDate;
	private MusicPostType type;

	private Bitmap previewImage;

	public enum MusicPostType {
		YOUTUBE, SOUNDCLOUD;
		public String toString() { return this.name(); }
		public static MusicPostType get(String serialized) {
			for (MusicPostType type : MusicPostType.values()) {
				if (type.name().equals(serialized))
					return type;
			}
			return null;
		}
	}

	/**
	 * Constructor used when creating a MusicPost from RSS FEED
	 * @param id
	 * @param title
	 * @param descriptionTag
	 * @param pubDate
	 */
	public MusicPost(long id, String title, String descriptionTag, String pubDate) {
		this.id = id;
		this.title = title;
		this.targetUrl = generateTargetUrl(descriptionTag);
		this.pubDate = pubDate;
		this.type = detectPostType(descriptionTag);
	}

	/**
	 * Constructor used when loading a MusicPost from Database
	 * @param id
	 * @param title
	 * @param targetUrl
	 * @param pubDate
	 * @param type
	 */
	public MusicPost(long id, String title, String targetUrl, String pubDate, MusicPostType type) {
		this.id = id;
		this.title = title;
		this.targetUrl = targetUrl;
		this.pubDate = pubDate;
		this.type = type;
	}

	public long getId() { return id; }
	public void setId(long id) { this.id = id; }

	public String getTitle() { return title; }
	public void setTitle(String title) { this.title = title; }
	public boolean hasTitle() { return !this.title.equals("Video") && !this.title.equals("Audio"); }

	public String getPubDate() { return pubDate; }
	public void setPubDate(String pubDate) { this.pubDate = pubDate; }

	public MusicPostType getType() { return type; }

	public Bitmap getPreviewImage() { return previewImage; }
	public void setPreviewImage(Bitmap previewImage) { this.previewImage = previewImage; }
	public boolean hasPreviewImage() { return this.previewImage != null; }

	public String getTargetUrl() { return targetUrl; }

	public String toString() {
		return this.title;
	}

	public String generateTargetUrl(String descriptionTag) {
		Document jsoup = Jsoup.parse(descriptionTag);
		Elements iframes = jsoup.getElementsByTag("iframe");
		if (iframes.size() > 0)
			return jsoup.getElementsByTag("iframe").get(0).attr("src");
		else
			return "";
	}
	public MusicPostType detectPostType(String descriptionTag) {
		return descriptionTag.contains("youtube") ? MusicPost.MusicPostType.YOUTUBE : MusicPost.MusicPostType.SOUNDCLOUD;
	}
}
