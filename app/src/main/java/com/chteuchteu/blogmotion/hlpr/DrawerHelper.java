package com.chteuchteu.blogmotion.hlpr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.ui.MusicMotionActivity;

import java.util.ArrayList;
import java.util.Collections;

public class DrawerHelper {
	private Context context;
	private DrawerLayout drawerLayout;
	private View drawerView;
	private boolean isDrawerOpened;
	private ListView listView;

	private static final String[] listViewItems = { "MusicMotion" };

	public DrawerHelper(Activity activity, final Context context) {
		this.drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
		this.drawerView = activity.findViewById(R.id.drawer);
		this.context = context;

		setPadding();

		// Init list
		this.listView = (ListView) this.drawerView.findViewById(R.id.listview);
		final ArrayList<String> list = new ArrayList<String>();
		Collections.addAll(list, listViewItems);
		final ArrayAdapter adapter = new ArrayAdapter(context,
				android.R.layout.simple_list_item_1, list);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				switch (i) {
					case 0:
						context.startActivity(new Intent(context, MusicMotionActivity.class));
						Util.setTransition(context, Util.TransitionStyle.DEEPER);
						break;
				}
			}
		});
	}

	public void toggleDrawer() {
		if (isDrawerOpened)
			drawerLayout.closeDrawer(Gravity.LEFT);
		else
			drawerLayout.openDrawer(Gravity.LEFT);
	}

	/**
	 * Set drawer top padding
	 */
	private void setPadding() {
		if (Util.isStatusBarTransparencyAvailable(context))
			this.drawerView.setPadding(
					this.drawerView.getPaddingLeft(),
					Util.getStatusBarHeight(context),
					this.drawerView.getPaddingRight(),
					this.drawerView.getPaddingBottom());
	}

	public void setDrawerListener(DrawerLayout.DrawerListener listener) {
		this.drawerLayout.setDrawerListener(listener);
	}
	public boolean isDrawerOpened() { return this.isDrawerOpened; }
	public void setDrawerOpened(boolean val) { this.isDrawerOpened = val; }
}
