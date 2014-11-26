package com.chteuchteu.blogmotion;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.balysv.materialmenu.MaterialMenuDrawable;
import com.balysv.materialmenu.extras.toolbar.MaterialMenuIconToolbar;
import com.readystatesoftware.systembartint.SystemBarTintManager;

public class BMActivity extends ActionBarActivity {
	protected Context context;
	protected ActionBar actionBar;
	private MaterialMenuIconToolbar materialMenu;
	private Toolbar toolbar;

	private Menu menu;
	protected int menuRes;
	private DrawerLayout drawerLayout;
	private boolean isDrawerOpened;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.context = this;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			int id = getResources().getIdentifier("config_enableTranslucentDecor", "bool", "android");
			if (id != 0 && getResources().getBoolean(id)) { // Translucent available
				Window w = getWindow();
				//w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
				w.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

				// On Android KitKat => statusBarColor. Above => actionBarColor
				int statusBarColor = Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT ? R.color.statusBarColor : R.color.actionBarColor;
				SystemBarTintManager tintManager = new SystemBarTintManager(this);
				tintManager.setStatusBarTintEnabled(true);
				tintManager.setStatusBarTintResource(statusBarColor);
			}
		}
	}

	protected void afterOnCreate() {
		this.toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);
		this.actionBar = getSupportActionBar();
		this.actionBar.setDisplayShowHomeEnabled(false);

		this.materialMenu = new MaterialMenuIconToolbar(this, Color.WHITE, MaterialMenuDrawable.Stroke.THIN) {
			@Override public int getToolbarViewId() {
				return R.id.toolbar;
			}
		};
		this.materialMenu.setNeverDrawTouch(true);
	}

	protected void createOptionsMenu() {
		if (this.menuRes == 0)
			return;

		MenuInflater inflater = getMenuInflater();
		inflater.inflate(this.menuRes, menu);
	}

	protected void toggleDrawer() {
		if (isDrawerOpened)
			drawerLayout.closeDrawer(Gravity.LEFT);
		else
			drawerLayout.openDrawer(Gravity.LEFT);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case android.R.id.home:
				toggleDrawer();
				return true;
		}

		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		this.menu = menu;

		this.drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

		this.drawerLayout.setDrawerListener(new DrawerLayout.DrawerListener() {
			@Override
			public void onDrawerSlide(View view, float slideOffset) {
				materialMenu.setTransformationOffset(
						MaterialMenuDrawable.AnimationState.BURGER_ARROW,
						isDrawerOpened ? 2 - slideOffset : slideOffset
				);
			}

			@Override
			public void onDrawerOpened(View view) {
				isDrawerOpened = true;
				materialMenu.animatePressedState(MaterialMenuDrawable.IconState.ARROW);

				actionBar.setSubtitle(actionBar.getTitle());
				actionBar.setTitle(getString(R.string.app_name));

				menu.clear();
				getMenuInflater().inflate(menuRes, menu);
			}

			@Override
			public void onDrawerClosed(View view) {
				isDrawerOpened = false;
				materialMenu.animatePressedState(MaterialMenuDrawable.IconState.BURGER);

				actionBar.setTitle(actionBar.getSubtitle());
				actionBar.setSubtitle(null);

				createOptionsMenu();
			}

			@Override
			public void onDrawerStateChanged(int i) {
			}
		});

		createOptionsMenu();

		return true;
	}
}
