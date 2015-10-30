package com.chteuchteu.blogmotion.hlpr;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.ui.AboutActivity;
import com.chteuchteu.blogmotion.ui.BMActivity;
import com.chteuchteu.blogmotion.ui.MusicMotionActivity;
import com.chteuchteu.blogmotion.ui.PostListActivity;
import com.chteuchteu.blogmotion.ui.TwitterActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;

import java.util.HashMap;

public class DrawerHelper {
	private Context context;
	private Activity activity;
	private Toolbar toolbar;
	private HashMap<DrawerMenuItem, IDrawerItem> drawerItems;

	public enum DrawerMenuItem {
		Articles(0),
		MusicMotion(1),
		Twitter(2),
		APropos(3);

		private int identifier;
		public int getIdentifier() { return this.identifier; }
		DrawerMenuItem(int identifier) { this.identifier = identifier; }
		public static DrawerMenuItem from(int identifier) {
			for (DrawerMenuItem item : DrawerMenuItem.values()) {
				if (item.getIdentifier() == identifier)
					return item;
			}
			return Articles;
		}
	}

	public DrawerHelper(Activity activity, Toolbar toolbar) {
		this.activity = activity;
		this.context = activity;
		this.toolbar = toolbar;
		this.drawerItems = new HashMap<>();

		buildDrawer();
	}

	private void buildDrawer() {
		// Articles
		this.drawerItems.put(DrawerMenuItem.Articles,
				new PrimaryDrawerItem()
						.withName(R.string.articles)
						.withIdentifier(DrawerMenuItem.Articles.getIdentifier())
						.withIcon(CommunityMaterial.Icon.cmd_book)
		);

		// MusicMotion
		this.drawerItems.put(DrawerMenuItem.MusicMotion,
				new PrimaryDrawerItem()
						.withName(R.string.musicmotion)
						.withIdentifier(DrawerMenuItem.MusicMotion.getIdentifier())
						.withIcon(CommunityMaterial.Icon.cmd_audiobook)
		);

		// Twitter
		this.drawerItems.put(DrawerMenuItem.Twitter,
				new PrimaryDrawerItem()
						.withName(R.string.twitter)
						.withIdentifier(DrawerMenuItem.Twitter.getIdentifier())
						.withIcon(CommunityMaterial.Icon.cmd_twitter)
		);

		// About
		this.drawerItems.put(DrawerMenuItem.APropos,
				new PrimaryDrawerItem()
						.withName(R.string.about)
						.withIdentifier(DrawerMenuItem.APropos.getIdentifier())
						.withIcon(CommunityMaterial.Icon.cmd_information)
		);

		DrawerBuilder builder = new DrawerBuilder()
				.withActivity(this.activity)
				.withToolbar(this.toolbar);

		// Add items
		builder.addDrawerItems(
				this.drawerItems.get(DrawerMenuItem.Articles),
				this.drawerItems.get(DrawerMenuItem.MusicMotion),
				this.drawerItems.get(DrawerMenuItem.Twitter),
				this.drawerItems.get(DrawerMenuItem.APropos)
		);

		builder.withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
			@Override
			public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
				DrawerMenuItem item = DrawerMenuItem.from(drawerItem.getIdentifier());

				switch (item) {
					case Articles:
						context.startActivity(new Intent(context, PostListActivity.class));
						Util.setTransition(context, Util.TransitionStyle.DEEPER);
						return true;
					case MusicMotion:
						context.startActivity(new Intent(context, MusicMotionActivity.class));
						Util.setTransition(context, Util.TransitionStyle.DEEPER);
						return true;
					case Twitter:
						context.startActivity(new Intent(context, TwitterActivity.class));
						Util.setTransition(context, Util.TransitionStyle.DEEPER);
						return true;
					case APropos:
						context.startActivity(new Intent(context, AboutActivity.class));
						Util.setTransition(context, Util.TransitionStyle.DEEPER);
						return true;
					default:
						return false;
				}
			}
		});

		// Header
		AccountHeader header = new AccountHeaderBuilder()
				.withActivity(this.activity)
				.withCompactStyle(true)
				.withHeaderBackground(R.drawable.header)
				.build();
		builder.withAccountHeader(header);

		// Selected item
		if (this.activity instanceof BMActivity) {
			DrawerMenuItem item = ((BMActivity) activity).getMenuItem();
			builder.withSelectedItem(item.getIdentifier());
		}

		builder.build();
	}
}
