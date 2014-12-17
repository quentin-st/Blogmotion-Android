package com.chteuchteu.blogmotion.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebView;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.hlpr.Util;

public class TwitterActivity extends BMActivity {
	private static final String TWITTER_URL = "https://twitter.com/xhark";

	private static final String baseURl = "https://twitter.com";

	private static final String widgetInfo = "<a class=\"twitter-timeline\" href=\"https://twitter.com/search?q=%23xhark\" data-widget-id=\"394415351972114432\">Tweets about \"#xhark" +
			"\"</a> " +
			"<script>!function(d,s,id){var js,fjs=d.getElementsByTagName(s)[0],p=/^http:/.test(d.location)?'http':'https';if(!d.getElementById(id)){js=d.createElement(s);js.id=id;js.src=p+\"://platform.twitter.com/widgets.js\";fjs.parentNode.insertBefore(js,fjs);}}(document,\"script\",\"twitter-wjs\");</script>";

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
		webView.loadDataWithBaseURL(baseURl, widgetInfo, "text/html", "UTF-8", null);

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
