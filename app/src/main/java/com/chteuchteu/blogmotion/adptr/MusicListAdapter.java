package com.chteuchteu.blogmotion.adptr;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.at.YoutubePreviewLoader;
import com.chteuchteu.blogmotion.hlpr.Util;
import com.chteuchteu.blogmotion.obj.MusicPost;

import java.util.List;

public class MusicListAdapter extends ArrayAdapter<MusicPost> {
	private Context context;
	private List<MusicPost> posts;

	public MusicListAdapter(Context context, List<MusicPost> posts) {
		super(context, R.layout.row_musicitem, posts);
		this.context = context;
		this.posts = posts;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = convertView != null
				? convertView
				: inflater.inflate(R.layout.row_musicitem, parent, false);

		TextView tv_publishedOn2 = (TextView) rowView.findViewById(R.id.mediaPublishedOn2);
		TextView tv_mediaName = (TextView) rowView.findViewById(R.id.mediaName);
		final ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);

		final MusicPost post = this.posts.get(position);

		tv_publishedOn2.setText(post.getPubDate());
		Util.Fonts.setFont(context, tv_publishedOn2, Util.Fonts.CustomFont.Roboto_Medium);

		if (post.hasTitle())
			tv_mediaName.setText(post.getTitle());
		else
			tv_mediaName.setVisibility(View.GONE);

		// Load preview picture
		if (post.getType() == MusicPost.MusicPostType.YOUTUBE) {
			if (post.hasPreviewImage())
				imageView.setImageBitmap(post.getPreviewImage());
			else {
				new YoutubePreviewLoader(post, new YoutubePreviewLoader.YoutubePreviewLoaderListener() {
					@Override
					public void onPostExecute(Bitmap downloadedBitmap) {
						post.setPreviewImage(downloadedBitmap);
						imageView.setImageBitmap(downloadedBitmap);
					}
				}).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
			}
		}

		return rowView;
	}
} 