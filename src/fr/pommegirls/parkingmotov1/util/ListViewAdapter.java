package fr.pommegirls.parkingmotov1.util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import fr.pommegirls.parkingmotov1.R;

public class ListViewAdapter extends BaseAdapter {
	Activity context;
	String points[];
	String userLogin[];
	String place[];

	public ListViewAdapter(Activity context, String[] title,
			String[] description, String[] place) {
		super();
		this.context = context;
		this.points = title;
		this.userLogin = description;
		this.place = place;
	}

	@Override
	public int getCount() {
		return points.length;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolder {
		TextView txtViewPoints;
		TextView txtViewLoginUser;
		TextView txtViewPlaces;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_text, null);
			holder = new ViewHolder();
			holder.txtViewPlaces = (TextView) convertView
					.findViewById(R.id.place);
			holder.txtViewPoints = (TextView) convertView
					.findViewById(R.id.points);
			holder.txtViewLoginUser = (TextView) convertView
					.findViewById(R.id.loginUser);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtViewPlaces.setText(place[position]);
		holder.txtViewPoints.setText(points[position]);
		holder.txtViewLoginUser.setText(userLogin[position]);

		return convertView;
	}

}
