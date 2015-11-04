package com.chteuchteu.blogmotion.at;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.chteuchteu.blogmotion.obj.Post;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class PostPreviewLoader extends AsyncTask<Void, Integer, Void> {
	private Post post;
	private PostPreviewLoaderListener ppll;
	private Bitmap downloadedBitmap;

	public PostPreviewLoader(Post post, PostPreviewLoaderListener ppll) {
		this.post = post;
		this.ppll = ppll;
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		String imageUrl = post.getPreviewImageUrl();

		if (imageUrl == null)
			return null;

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
		this.ppll.onPostExecute(this.downloadedBitmap);
	}

	public interface PostPreviewLoaderListener {
		void onPostExecute(Bitmap downloadedBitmap);
	}
}
