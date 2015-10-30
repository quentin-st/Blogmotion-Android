package com.chteuchteu.blogmotion.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.hlpr.DrawerHelper;

public class BMActivity extends ActionBarActivity {
	protected Context context;
	protected ActionBar actionBar;

	private Menu menu;
	protected int menuRes;

	protected Class currentActivity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.context = this;
	}

	protected void afterOnCreate() {
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		this.actionBar = getSupportActionBar();
		this.actionBar.setDisplayShowHomeEnabled(false);

		new DrawerHelper(this, toolbar);
	}

	protected void createOptionsMenu() {
		if (this.menuRes == 0)
			return;

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(this.menuRes, menu);
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		this.menu = menu;

		createOptionsMenu();

		return true;
	}

	public DrawerHelper.DrawerMenuItem getMenuItem() {
		return null;
	}
}
