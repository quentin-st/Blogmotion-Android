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
		View rowView;

		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.row_musicitem, parent, false);
		}
		else
			rowView = convertView;

		TextView textView = (TextView) rowView.findViewById(R.id.textView);
		final ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);

		final MusicPost post = this.posts.get(position);

		textView.setText(post.getTitle());
		Util.Fonts.setFont(context, textView, Util.Fonts.CustomFont.Roboto_Medium);

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