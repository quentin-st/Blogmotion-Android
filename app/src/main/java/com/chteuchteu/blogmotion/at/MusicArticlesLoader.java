package com.chteuchteu.blogmotion.at;

import android.os.AsyncTask;

import com.chteuchteu.blogmotion.BM;
import com.chteuchteu.blogmotion.hlpr.DatabaseHelper;
import com.chteuchteu.blogmotion.hlpr.MusicMotionHelper;
import com.chteuchteu.blogmotion.hlpr.Util;
import com.chteuchteu.blogmotion.obj.MusicPost;

import java.util.List;

public class MusicArticlesLoader extends AsyncTask<Void, Integer, Void> {
	private List<MusicPost> posts;
	private Util.ProgressListener progressListener;
	private boolean forceLoad;

	public MusicArticlesLoader(List<MusicPost> posts, Util.ProgressListener progressListener, boolean forceLoad) {
		this.posts = posts;
		this.progressListener = progressListener;
		this.forceLoad = forceLoad;
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

		boolean hasPosts = dbHelper.hasMusicPosts();
		if (forceLoad || !hasPosts) {
			publishProgress(30);

			MusicMotionHelper.parseFeed(this.posts);

			publishProgress(70);

			if (hasPosts)
				dbHelper.clearMusicPosts();
			dbHelper.insertMusicPosts(posts);
		} else {
			publishProgress(30);

			this.posts.clear();
			this.posts.addAll(dbHelper.getMusicPosts());
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
