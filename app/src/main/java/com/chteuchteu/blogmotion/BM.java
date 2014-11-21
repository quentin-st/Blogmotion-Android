package com.chteuchteu.blogmotion;

import android.content.Context;
import android.util.Log;

import com.chteuchteu.blogmotion.at.ArticlesLoader;
import com.chteuchteu.blogmotion.hlpr.Util;
import com.chteuchteu.blogmotion.obj.Post;

import java.util.ArrayList;
import java.util.List;

public class BM {
	public static final String FEED_URL = "http://blogmotion.fr/feed";

	private static BM instance;

	private List<Post> posts;

	private BM(Context context) {
		loadInstance(context);
	}

	private void loadInstance(Context context) {
		this.posts = new ArrayList<Post>();
	}

	public static synchronized BM getInstance(Context context) {
		if (instance == null)
			instance = new BM(context);
		return instance;
	}

	public List<Post> getPosts() { return this.posts; }
	public Post getPost(long id) {
		for (Post post : posts) {
			if (post.getId() == id)
				return post;
		}
		return null;
	}

	public void loadArticles(Util.ProgressListener progressListener) {
		new ArticlesLoader(posts, progressListener).execute();
	}

	public static void log(String s) { if (BuildConfig.DEBUG) Log.i("Blogmotion", s); }
}
