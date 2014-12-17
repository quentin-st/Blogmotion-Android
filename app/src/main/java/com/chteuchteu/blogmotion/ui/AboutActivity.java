package com.chteuchteu.blogmotion.ui;

import android.content.Intent;
import android.os.Bundle;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.hlpr.Util;

public class AboutActivity extends BMActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		this.menuRes = R.menu.menu_about;
		this.currentActivity = AboutActivity.class;

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
