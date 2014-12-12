package com.chteuchteu.blogmotion.adptr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.chteuchteu.blogmotion.R;
import com.chteuchteu.blogmotion.hlpr.DrawerHelper;
import com.chteuchteu.blogmotion.hlpr.Util;

import java.util.List;

public class DrawerAdapter extends ArrayAdapter<DrawerHelper.DrawerItem> {
	private Context context;
	private List<DrawerHelper.DrawerItem> items;

	public DrawerAdapter(Context context, List<DrawerHelper.DrawerItem> items) {
		super(context, R.layout.row_draweritem, items);

		this.context = context;
		this.items = items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.row_draweritem, parent, false);

		DrawerHelper.DrawerItem drawerItem = items.get(position);

		ImageView icon = (ImageView) rowView.findViewById(R.id.icon);
		TextView text = (TextView) rowView.findViewById(R.id.text);
		View row = rowView.findViewById(R.id.row);

		Util.Fonts.setFont(context, text, Util.Fonts.CustomFont.Roboto_Medium);

		if (drawerItem.getIconRes() != -1)
			icon.setImageResource(drawerItem.getIconRes());
		text.setText(drawerItem.getTitleRes());

		if (drawerItem.isActive())
			row.setBackgroundResource(R.color.drawer_selectedcolor);

		return rowView;
	}

	public List<DrawerHelper.DrawerItem> getItems() { return this.items; }

}
