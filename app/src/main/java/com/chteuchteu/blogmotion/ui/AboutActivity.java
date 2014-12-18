package com.chteuchteu.blogmotion.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.hlpr.Util;

public class AboutActivity extends BMActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);

		this.menuRes = R.menu.menu_about;
		this.currentActivity = AboutActivity.class;

		Util.Fonts.setFont(this, ((TextView) findViewById(R.id.textView)), Util.Fonts.CustomFont.Roboto_Regular);
		Util.Fonts.setFont(this, ((Button) findViewById(R.id.button)), Util.Fonts.CustomFont.Roboto_Regular);

		findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(Intent.ACTION_VIEW);
				intent.setData(Uri.parse("http://blogmotion.fr/a-propos"));
				startActivity(intent);
			}
		});

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
