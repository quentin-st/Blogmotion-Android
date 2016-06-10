package com.chteuchteu.blogmotion.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chteuchteu.blogmotion.BM;
import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.adptr.PostsListAdapter;
import com.chteuchteu.blogmotion.at.ArticlesLoader;
import com.chteuchteu.blogmotion.hlpr.DrawerHelper;
import com.chteuchteu.blogmotion.hlpr.Util;
import com.chteuchteu.blogmotion.obj.Post;
import com.crashlytics.android.Crashlytics;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class PostListActivity extends BMActivity {
	private boolean refreshing;
	private LinearLayout postsContainer;
	private PostsListAdapter adapter;
	private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

	    Fabric.with(this, new Crashlytics());

	    this.menuRes = R.menu.postlist;
	    this.currentActivity = PostListActivity.class;
	    this.postsContainer = (LinearLayout) findViewById(R.id.list_container);

	    super.afterOnCreate();

	    this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
	    this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
		    @Override
		    public void onRefresh() {
			    fetchArticles();
		    }
	    });

	    this.adapter = new PostsListAdapter((LinearLayout) findViewById(R.id.list_container), this.context);
	    this.adapter.setOnItemSelected(new PostsListAdapter.OnItemSelected() {
		    @Override
		    public void onItemSelected(Post post) {
			    Intent detailIntent = new Intent(context, PostDetailActivity.class);
			    detailIntent.putExtra(PostDetailFragment.ARG_ITEM_ID, post.getId());
			    startActivity(detailIntent);
			    Util.setTransition(context, Util.TransitionStyle.DEEPER);
		    }
	    });
	    this.adapter.inflate(bm.getPosts());

	    // Load articles from local db
		inflateArticles(BM.shouldFetchArticles(this));

	    if (!Util.hasPref(this, "firstLaunch")) {
		    Toast.makeText(this, R.string.firstLaunch, Toast.LENGTH_LONG).show();
		    Util.setPref(this, "firstLaunch", "false");
	    }
    }

	/**
	 * Inflates articles list from DB, done at app launch
	 * @param fetch True if we should inflate + update the list from internet
     */
	private void inflateArticles(final boolean fetch) {
		if (refreshing)
			return;

		refreshing = true;

		// Load articles from cache
		bm.loadArticles(new Util.ProgressListener() {
			@Override
			public void onPreExecute() { }

			@Override
			public void onProgress(int progress, int total) { }

			@Override
			public void onPostExecute() {
				postsContainer.removeAllViews();
				adapter.inflate(bm.getPosts());

				refreshing = false;

				if (fetch)
					fetchArticles();
			}
		}, ArticlesLoader.Mode.CACHE);
	}

	public void fetchArticles() {
		if (refreshing)
			return;

		refreshing = true;

		// Load articles on first launch (or if forceLoad)
		bm.loadArticles(new Util.ProgressListener() {
			private long lastArticleId;

			@Override
			public void onPreExecute() {
				swipeRefreshLayout.post(new Runnable() {
					@Override
					public void run() {
						swipeRefreshLayout.setRefreshing(true);
					}
				});

				List<Post> articles = bm.getPosts();
				if (articles.size() > 0)
					this.lastArticleId = articles.get(articles.size() - 1).getId();
				else
					this.lastArticleId = -1;

				Util.setViewAlpha(postsContainer, 0.7f);
			}

			@Override
			public void onProgress(int progress, int total) { }

			@Override
			public void onPostExecute() {
				// Find lastArticleId and compare it (see if there has been any changes)
				List<Post> articles = bm.getPosts();

				long lastArticleId = -1;
				if (articles.size() > 0)
					lastArticleId = articles.get(articles.size() - 1).getId();

				boolean newArticles = this.lastArticleId != lastArticleId;
				if (newArticles) {
					postsContainer.removeAllViews();
					adapter.inflate(bm.getPosts());
				}

				Util.setViewAlpha(postsContainer, 1f);

				swipeRefreshLayout.post(new Runnable() {
					@Override
					public void run() {
						swipeRefreshLayout.setRefreshing(false);
					}
				});
				refreshing = false;

				// Save lastArticlesFetch
				DateFormat dateFormat = BM.getDateFormat();
				Date now = Calendar.getInstance().getTime();
				Util.setPref(context, "lastArticlesFetch", dateFormat.format(now));
			}
		}, ArticlesLoader.Mode.INTERNET);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				fetchArticles();
				return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public DrawerHelper.DrawerMenuItem getMenuItem() {
		return DrawerHelper.DrawerMenuItem.Articles;
	}
}
