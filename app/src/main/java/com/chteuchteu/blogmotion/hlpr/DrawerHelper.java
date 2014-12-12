package com.chteuchteu.blogmotion.hlpr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.adptr.DrawerAdapter;
import com.chteuchteu.blogmotion.ui.MusicMotionActivity;
import com.chteuchteu.blogmotion.ui.PostListActivity;

import java.util.ArrayList;
import java.util.List;

public class DrawerHelper {
	private Context context;
	private DrawerLayout drawerLayout;
	private boolean isDrawerOpened;
	private DrawerAdapter drawerAdapter;

	public DrawerHelper(Activity activity, final Context context, Class currentActivity) {
		this.drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
		View drawerView = activity.findViewById(R.id.drawer);
		this.context = context;

		// Init list
		ListView listView = (ListView) drawerView.findViewById(R.id.listview);

		List<DrawerItem> drawerItems = getDrawerItems(currentActivity);
		this.drawerAdapter = new DrawerAdapter(context, drawerItems);
		listView.setAdapter(this.drawerAdapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				drawerAdapter.getItems().get(i).getClickListener().onClick();
			}
		});
	}

	public void toggleDrawer() {
		if (isDrawerOpened)
			drawerLayout.closeDrawer(Gravity.LEFT);
		else
			drawerLayout.openDrawer(Gravity.LEFT);
	}

	public void setDrawerListener(DrawerLayout.DrawerListener listener) {
		this.drawerLayout.setDrawerListener(listener);
	}
	public boolean isDrawerOpened() { return this.isDrawerOpened; }
	public void setDrawerOpened(boolean val) { this.isDrawerOpened = val; }

	public class DrawerItem {
		private int titleRes;
		private int iconRes;
		private OnDrawerItemClick clickListener;
		private boolean active;

		public DrawerItem(int titleRes, int iconRes, OnDrawerItemClick clickListener, boolean active) {
			this.titleRes = titleRes;
			this.iconRes = iconRes;
			this.clickListener = clickListener;
			this.active = active;
		}

		public int getTitleRes() { return titleRes; }
		public int getIconRes() { return iconRes; }
		public OnDrawerItemClick getClickListener() { return clickListener; }
		public boolean isActive() { return this.active; }
	}
	public interface OnDrawerItemClick { public void onClick(); }

	private List<DrawerItem> getDrawerItems(Class currentActivity) {
		List<DrawerItem> drawerItems = new ArrayList<>();

		// Articles
		drawerItems.add(new DrawerItem(R.string.articles, R.drawable.ic_articles, new OnDrawerItemClick() {
			@Override
			public void onClick() {
				context.startActivity(new Intent(context, PostListActivity.class));
				Util.setTransition(context, Util.TransitionStyle.DEEPER);
			}
		}, currentActivity == PostListActivity.class));

		// Musicmotion
		drawerItems.add(new DrawerItem(R.string.musicmotion, R.drawable.ic_musicmotion, new OnDrawerItemClick() {
			@Override
			public void onClick() {
				context.startActivity(new Intent(context, MusicMotionActivity.class));
				Util.setTransition(context, Util.TransitionStyle.DEEPER);
			}
		}, currentActivity == MusicMotionActivity.class));

		return drawerItems;
	}
}
