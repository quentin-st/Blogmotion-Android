package com.chteuchteu.blogmotion.hlpr;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.ui.BMActivity;
import com.chteuchteu.blogmotion.ui.MusicMotionActivity;
import com.chteuchteu.blogmotion.ui.PostListActivity;
import com.chteuchteu.blogmotion.ui.TwitterActivity;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.community_material_typeface_library.CommunityMaterial;

import java.util.HashMap;

public class DrawerHelper {
	private static final String CONTRIBUTE_URL = "https://github.com/chteuchteu/Blogmotion-Android";

	private Context context;
	private Activity activity;
	private Toolbar toolbar;
	private HashMap<DrawerMenuItem, IDrawerItem> drawerItems;

	public enum DrawerMenuItem {
		Articles(0),
		MusicMotion(1),
		Twitter(2),
		APropos(3),
		Contribuer(4);

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
				new SecondaryDrawerItem()
						.withName(R.string.about)
						.withIdentifier(DrawerMenuItem.APropos.getIdentifier())
						.withIcon(CommunityMaterial.Icon.cmd_information)
						.withSelectable(false)
		);

		// Contribuer
		this.drawerItems.put(DrawerMenuItem.Contribuer,
				new SecondaryDrawerItem()
						.withName(R.string.contribute)
						.withIdentifier(DrawerMenuItem.Contribuer.getIdentifier())
						.withIcon(CommunityMaterial.Icon.cmd_github_box)
						.withSelectable(false)
		);

		DrawerBuilder builder = new DrawerBuilder()
				.withActivity(this.activity)
				.withToolbar(this.toolbar);

		// Add items
		builder.addDrawerItems(
				this.drawerItems.get(DrawerMenuItem.Articles),
				this.drawerItems.get(DrawerMenuItem.MusicMotion),
				this.drawerItems.get(DrawerMenuItem.Twitter),
				new DividerDrawerItem(),
				this.drawerItems.get(DrawerMenuItem.APropos),
				this.drawerItems.get(DrawerMenuItem.Contribuer)
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
						new AlertDialog.Builder(context)
								.setTitle(R.string.about)
								.setMessage(R.string.about_text)
								.setPositiveButton(R.string.about_more, new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog, int which) {
										Intent intent = new Intent(Intent.ACTION_VIEW);
										intent.setData(Uri.parse("http://blogmotion.fr/a-propos"));
										context.startActivity(intent);
									}
								})
								.show();
						return true;
					case Contribuer:
						context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(CONTRIBUTE_URL)));
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
