package com.chteuchteu.blogmotion.hlpr;

public class YoutubeHelper {
	public static String getPreviewImageUrl(String videoUrl) {
		return "https://img.youtube.com/vi/" + getVideoId(videoUrl) + "/0.jpg";
	}

	public static String getVideoId(String videoUrl) {
		// Youtube URL should be https://www.youtube.com/embed/VMp2-VO1wvc?feature=oembed
		String leftConcat = videoUrl.substring(videoUrl.lastIndexOf('/'));
		if (leftConcat.contains("?"))
			return leftConcat.substring(0, leftConcat.lastIndexOf('/'));
		else
			return leftConcat;
	}
}
