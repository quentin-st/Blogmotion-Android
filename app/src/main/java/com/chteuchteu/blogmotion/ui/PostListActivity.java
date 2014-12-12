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
import com.crashlytics.android.Crashlytics;

public class PostListActivity extends BMActivity {
	private ProgressBar progressBar;
	private boolean refreshing;
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

		// Disappear animation
		if (!BM.getInstance(context).getPosts().isEmpty()) {
			final LinearLayout container = (LinearLayout) findViewById(R.id.list_container);
			container.removeAllViews();
		}

		// Load articles on first launch
		if (BM.getInstance(context).getPosts().isEmpty() || forceLoad)
			BM.getInstance(context).loadArticles(new Util.ProgressListener() {
				@Override
				public void onPreExecute() {
					progressBar.setProgress(0);
					progressBar.setVisibility(View.VISIBLE);
					progressBar.setIndeterminate(true);
				}

				@Override
				public void onProgress(int progress, int total) {
					progressBar.setIndeterminate(false);
					progressBar.setProgress(progress);
					progressBar.setMax(total);
				}

				@Override
				public void onPostExecute() {
					adapter.inflate(BM.getInstance(context).getPosts());
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
