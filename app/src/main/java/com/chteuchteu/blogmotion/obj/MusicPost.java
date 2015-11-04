package com.chteuchteu.blogmotion.obj;

import android.graphics.Bitmap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MusicPost extends BasePost {
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
		super(title, pubDate, null);
		this.id = id;
		this.permalink = generateTargetUrl(descriptionTag);
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
		super(title, pubDate, targetUrl);
		this.id = id;
		this.type = type;
	}

	public boolean hasTitle() { return !this.title.equals("Video") && !this.title.equals("Audio"); }

	public MusicPostType getType() { return type; }

	public Bitmap getPreviewImage() { return previewImage; }
	public void setPreviewImage(Bitmap previewImage) { this.previewImage = previewImage; }
	public boolean hasPreviewImage() { return this.previewImage != null; }

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
