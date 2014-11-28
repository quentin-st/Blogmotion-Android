package com.chteuchteu.blogmotion.hlpr;

import android.app.Activity;
import android.content.Context;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;

import com.chteuchteu.blogmotion.R;

public class DrawerHelper {
	private Context context;
	private DrawerLayout drawerLayout;
	private View drawerView;
	private boolean isDrawerOpened;


	public DrawerHelper(Activity activity, Context context) {
		this.drawerLayout = (DrawerLayout) activity.findViewById(R.id.drawer_layout);
		this.drawerView = activity.findViewById(R.id.drawer);
		this.context = context;

		setPadding();
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
