package com.chteuchteu.blogmotion.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.hlpr.Util;

public class TwitterActivity extends BMActivity {
	private static final String TWITTER_URL = "http://blogmotion.fr/public/android/m.twitter/";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_twitter);

		this.menuRes = R.menu.menu_twitter;
		this.currentActivity = TwitterActivity.class;

		WebView webView = (WebView) findViewById(R.id.wv);
		webView.loadUrl(TWITTER_URL);
		webView.getSettings().setDomStorageEnabled(true);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.setBackgroundColor(Color.TRANSPARENT);

		super.afterOnCreate();
	}

	@Override
	public void onBackPressed() {
		Intent intent = new Intent(this, PostListActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		Util.setTransition(context, Util.TransitionStyle.SHALLOWER);
	}
}
