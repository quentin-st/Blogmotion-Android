package com.chteuchteu.blogmotion.at;

import android.os.AsyncTask;

import com.chteuchteu.blogmotion.BM;
import com.chteuchteu.blogmotion.hlpr.DatabaseHelper;
import com.chteuchteu.blogmotion.hlpr.ArticlesHelper;
import com.chteuchteu.blogmotion.hlpr.Util;
import com.chteuchteu.blogmotion.obj.Post;

import java.util.List;

/**
 * Gets a list of Article to be put in the posts array list in parameter
 */
public class ArticlesLoader extends AsyncTask<Void, Integer, Void> {
	public enum Mode { CACHE, INTERNET }

	private List<Post> posts;
	private Util.ProgressListener progressListener;
	private Mode mode;

	public ArticlesLoader(List<Post> posts, Util.ProgressListener progressListener, Mode mode) {
		this.posts = posts;
		this.progressListener = progressListener;
		this.mode = mode;
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		this.progressListener.onPreExecute();
	}

	@Override
	protected Void doInBackground(Void... arg0) {
		publishProgress(5);

		DatabaseHelper dbHelper = BM.getInstance(null).getDbHelper();

		boolean hasPosts = dbHelper.hasPosts();
		if (mode == Mode.INTERNET) {
			publishProgress(30);

			ArticlesHelper.parse(BM.FEED_URL, this.posts);

			publishProgress(70);

			if (hasPosts)
				dbHelper.clearPosts();
			dbHelper.insertPosts(posts);
		} else {
			publishProgress(30);

			this.posts.clear();
			this.posts.addAll(dbHelper.getPosts());
		}

		this.progressListener.onProgress(100, 100);

		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		this.progressListener.onPostExecute();
	}

	private void publishProgress(int progress) { this.progressListener.onProgress(progress, 100); }
}
