package com.chteuchteu.blogmotion.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chteuchteu.blogmotion.BM;
import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.adptr.MusicListAdapter;
import com.chteuchteu.blogmotion.hlpr.DrawerHelper;
import com.chteuchteu.blogmotion.hlpr.Util;

public class MusicMotionActivity extends BMActivity {
	private ListView listView;
	private SwipeRefreshLayout swipeRefreshLayout;

	private boolean refreshing;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_musicmotion);

		this.menuRes = R.menu.menu_musicmotion;
		this.currentActivity = MusicMotionActivity.class;

		this.listView = (ListView) findViewById(R.id.listview);

		super.afterOnCreate();

		this.swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
		this.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
			@Override
			public void onRefresh() {
				loadArticles(true);
			}
		});

		this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				String targetUrl = BM.getInstance(context).getMusicPosts().get(i).getPermalink();
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl)));
				Util.setTransition(context, Util.TransitionStyle.DEEPER);
			}
		});
		this.listView.setScrollingCacheEnabled(false);

		loadArticles(true);
	}

	private void loadArticles(boolean forceLoad) {
		if (refreshing)
			return;

		refreshing = true;
		// Load articles on first launch
		if (BM.getInstance(context).getMusicPosts().isEmpty() || forceLoad)
			BM.getInstance(context).loadMusicArticles(new Util.ProgressListener() {
				@Override
				public void onPreExecute() {
					swipeRefreshLayout.setRefreshing(true);
				}

				@Override
				public void onProgress(int progress, int total) { }

				@Override
				public void onPostExecute() {
					refreshList();
					swipeRefreshLayout.setRefreshing(false);
					refreshing = false;
				}
			}, forceLoad);
	}

	private void refreshList() {
		MusicListAdapter adapter = new MusicListAdapter(this, BM.getInstance(context).getMusicPosts());

		this.listView.setAdapter(adapter);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.menu_refresh:
				loadArticles(true);
				return true;
			default: return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, PostListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		Util.setTransition(context, Util.TransitionStyle.SHALLOWER);
	}

	public DrawerHelper.DrawerMenuItem getMenuItem() {
		return DrawerHelper.DrawerMenuItem.MusicMotion;
	}
}
