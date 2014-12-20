package com.chteuchteu.blogmotion.hlpr;

public class YoutubeHelper {
	public static String getPreviewImageUrl(String videoUrl) {
		String videoId = getVideoId(videoUrl);
		return "https://img.youtube.com/vi/" + videoId + "/0.jpg";
	}

	public static String getVideoId(String videoUrl) {
		// Youtube URL should be https://www.youtube.com/embed/ajvMEgA1x8o?feature=oembed&enablejsapi=1&origin=http://safe.txmblr.com&wmode=opaque
		if (videoUrl.contains("?"))
			return videoUrl.substring("https://www.youtube.com/embed/".length(), videoUrl.indexOf('?'));
		else
			return videoUrl.substring("https://www.youtube.com/embed/".length());
	}
}
