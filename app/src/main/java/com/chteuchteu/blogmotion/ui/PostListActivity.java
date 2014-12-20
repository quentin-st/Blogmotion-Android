package com.chteuchteu.blogmotion.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.chteuchteu.blogmotion.BM;
import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.adptr.PostsListAdapter;
import com.chteuchteu.blogmotion.hlpr.Util;
import com.chteuchteu.blogmotion.obj.Post;
import com.crashlytics.android.Crashlytics;

import java.util.List;

public class PostListActivity extends BMActivity {
	private ProgressBar progressBar;
	private boolean refreshing;
	private LinearLayout postsContainer;
	private PostsListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

	    Crashlytics.start(this);

	    // Init BM
	    BM.getInstance(this);

	    this.menuRes = R.menu.postlist;
	    this.currentActivity = PostListActivity.class;
	    this.postsContainer = (LinearLayout) findViewById(R.id.list_container);

	    super.afterOnCreate();

	    this.progressBar = Util.prepareGmailStyleProgressBar(this, this.actionBar);
	    fetchArticles(false);

	    this.adapter = new PostsListAdapter((LinearLayout) findViewById(R.id.list_container), this.context);
	    this.adapter.setOnItemSelected(new PostsListAdapter.OnItemSelected() {
		    @Override
		    public void onItemSelected(int itemId) {
			    long id = BM.getInstance(context).getPosts().get(itemId).getId();

			    Intent detailIntent = new Intent(context, PostDetailActivity.class);
			    detailIntent.putExtra(PostDetailFragment.ARG_ITEM_ID, id);
			    startActivity(detailIntent);
			    Util.setTransition(context, Util.TransitionStyle.DEEPER);
		    }
	    });
	    this.adapter.inflate(BM.getInstance(context).getPosts());
    }

	public void fetchArticles(boolean forceLoad) {
		if (refreshing)
			return;

		refreshing = true;

		// Load articles on first launch (or if forceload)
		if (BM.getInstance(context).getPosts().isEmpty() || forceLoad)
			BM.getInstance(context).loadArticles(new Util.ProgressListener() {
				private long lastArticleId;

				@Override
				public void onPreExecute() {
					progressBar.setProgress(0);
					progressBar.setVisibility(View.VISIBLE);
					progressBar.setIndeterminate(true);

					List<Post> articles = BM.getInstance(context).getPosts();
					if (articles.size() > 0)
						this.lastArticleId = articles.get(articles.size() - 1).getId();
					else
						this.lastArticleId = -1;

					Util.setViewAlpha(postsContainer, 0.7f);
				}

				@Override
				public void onProgress(int progress, int total) {
					if (progressBar.isIndeterminate())
						progressBar.setIndeterminate(false);
					progressBar.setProgress(progress);
					progressBar.setMax(total);
				}

				@Override
				public void onPostExecute() {
					// Find lastArticleId and compare it (see if there has been any changes)
					List<Post> articles = BM.getInstance(context).getPosts();

					long lastArticleId = -1;
					if (articles.size() > 0)
						lastArticleId = articles.get(articles.size() - 1).getId();

					boolean newArticles = this.lastArticleId != lastArticleId;
					if (newArticles) {
						BM.log("Removing postsContainer child views");
						postsContainer.removeAllViews();
						adapter.inflate(BM.getInstance(context).getPosts());
					}

					Util.setViewAlpha(postsContainer, 1f);

					progressBar.setProgress(0);
					progressBar.setVisibility(View.GONE);
					refreshing = false;
				}
			}, forceLoad);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				fetchArticles(true);
				return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
