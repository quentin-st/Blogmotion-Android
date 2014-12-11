package com.chteuchteu.blogmotion.at;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.chteuchteu.blogmotion.hlpr.YoutubeHelper;
import com.chteuchteu.blogmotion.obj.MusicPost;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class YoutubePreviewLoader extends AsyncTask<Void, Integer, Void> {
	private MusicPost musicPost;
	private Bitmap downloadedBitmap;
	private YoutubePreviewLoaderListener ypll;

	public YoutubePreviewLoader(MusicPost musicPost, YoutubePreviewLoaderListener ypll) {
		this.musicPost = musicPost;
		this.ypll = ypll;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

	}

	@Override
	protected Void doInBackground(Void... arg0) {
		if (this.musicPost.getType() != MusicPost.MusicPostType.YOUTUBE)
			return null;

		String imageUrl = YoutubeHelper.getPreviewImageUrl(musicPost.getTargetUrl());

		try {
			URL url = new URL(imageUrl);

			InputStream in = url.openStream();
			this.downloadedBitmap = BitmapFactory.decodeStream(in);
		}
		catch (MalformedURLException ex) {
			ex.printStackTrace();
			return null;
		}
		catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		this.ypll.onPostExecute(this.downloadedBitmap);
	}


	public static interface YoutubePreviewLoaderListener {
		public void onPostExecute(Bitmap downloadedBitmap);
	}
}
