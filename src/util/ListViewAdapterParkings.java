package util;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.parkingmotov1.R;

public class ListViewAdapterParkings extends BaseAdapter {
	Activity context;

	String name[];
	String address[];

	public ListViewAdapterParkings(Activity context, String[] title,
			String[] address) {
		super();
		
		this.context = context;
		
		this.name = title;
		this.address = address;
	}

	public int getCount() {
		return name.length;
	}

	public Object getItem(int position) {
		return null;
	}

	public long getItemId(int position) {
		return 0;
	}

	private class ViewHolder {
		TextView txtViewName;
		TextView txtViewAddress;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		LayoutInflater inflater = context.getLayoutInflater();

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.list_favorites, null);
			holder = new ViewHolder();
			holder.txtViewAddress = (TextView) convertView
					.findViewById(R.id.addressParking);
			
			holder.txtViewName = (TextView) convertView
					.findViewById(R.id.nameParking);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtViewAddress.setText(address[position]);
		holder.txtViewName.setText(name[position]);

		return convertView;
	}

}
