package com.chteuchteu.blogmotion.adptr;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.at.PostPreviewLoader;
import com.chteuchteu.blogmotion.hlpr.Util;
import com.chteuchteu.blogmotion.obj.Post;

import java.util.List;

public class PostsListAdapter {
	private Context context;
	private LinearLayout container;
	private OnItemSelected onItemSelected;

	public PostsListAdapter(LinearLayout container, Context context) {
		this.container = container;
		this.context = context;
	}

	public void inflate(final List<Post> posts) {
		LinearLayout line = null;

		for (final Post post : posts) {
			final int position = posts.indexOf(post);

			if (position % 2 == 0) { // New line
				line = new LinearLayout(context);
				line.setOrientation(LinearLayout.HORIZONTAL);
			}

			View rowPostItem = ((Activity) context).getLayoutInflater().inflate(R.layout.row_postitem, this.container, false);
			final ImageView img = (ImageView) rowPostItem.findViewById(R.id.imageview);
			TextView title = (TextView) rowPostItem.findViewById(R.id.title);
			Util.Fonts.setFont(context, title, Util.Fonts.CustomFont.Roboto_Regular);

			title.setText(post.getTitle());
			rowPostItem.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View view) {
					onItemSelected.onItemSelected(position);
				}
			});

			LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
			rowPostItem.setLayoutParams(param);

			if (line != null) // Just to avoid warning
				line.addView(rowPostItem);

			if (post.hasPreviewImage())
				img.setImageBitmap(post.getPreviewImage());
			else {
				new PostPreviewLoader(post, new PostPreviewLoader.PostPreviewLoaderListener() {
					@Override
					public void onPostExecute(Bitmap downloadedBitmap) {
						post.setPreviewImage(downloadedBitmap);

						img.setImageBitmap(downloadedBitmap);

						AlphaAnimation aAnimation = new AlphaAnimation(0.0f, 1.0f);
						aAnimation.setDuration(200);
						aAnimation.setFillAfter(true);
						img.startAnimation(aAnimation);
					}
				}).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}

			if (position % 2 == 1
					&& line != null) // Just to avoid warning
				container.addView(line);
		}
	}

	public void setOnItemSelected(OnItemSelected action) { this.onItemSelected = action; }

	public interface OnItemSelected {
		void onItemSelected(int itemId);
	}
}
