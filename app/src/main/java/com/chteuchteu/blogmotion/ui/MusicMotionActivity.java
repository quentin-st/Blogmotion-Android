package com.chteuchteu.blogmotion.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.chteuchteu.blogmotion.BM;
import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.adptr.MusicListAdapter;
import com.chteuchteu.blogmotion.hlpr.Util;

public class MusicMotionActivity extends BMActivity {
	private ListView listView;
	private ProgressBar progressBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_musicmotion);

		this.menuRes = R.menu.menu_musicmotion;

		this.listView = (ListView) findViewById(R.id.listview);
		this.progressBar = Util.prepareGmailStyleProgressBar(this, this.actionBar);

		super.afterOnCreate();

		loadArticles(true);

		this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				String targetUrl = BM.getInstance(context).getMusicPosts().get(i).getTargetUrl();
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(targetUrl)));
			}
		});
	}

	private void loadArticles(boolean forceLoad) {
		// Load articles on first launch
		if (BM.getInstance(context).getMusicPosts().isEmpty() || forceLoad)
			BM.getInstance(context).loadMusicArticles(new Util.ProgressListener() {
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
					refreshList();
					progressBar.setProgress(0);
					progressBar.setVisibility(View.GONE);
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
}