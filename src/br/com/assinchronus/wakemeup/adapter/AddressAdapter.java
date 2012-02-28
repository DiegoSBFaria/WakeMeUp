package br.com.assinchronus.wakemeup.adapter;

import java.util.ArrayList;

import br.com.assinchronus.wakemeup.R;
import br.com.assinchronus.wakemeup.model.Address;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AddressAdapter extends BaseAdapter {

	private ArrayList<Address> addresses;
	private LayoutInflater inflater;

	public AddressAdapter(ArrayList<Address> addresses, Context context) {
		super();
		this.addresses = addresses;
		this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return addresses.size();
	}

	@Override
	public Address getItem(int position) {
		return addresses.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		convertView = inflater.inflate(R.layout.address_item, null);

		TextView street = (TextView) convertView.findViewById(R.id.street);
		TextView zip = (TextView) convertView.findViewById(R.id.zip);
		TextView subAdmin = (TextView) convertView.findViewById(R.id.subAdmin);
		TextView city = (TextView) convertView.findViewById(R.id.city);

		street.setText(getItem(position).street);
		zip.setText(getItem(position).zip);
		subAdmin.setText(getItem(position).subAdmin);
		city.setText(getItem(position).city);

		return convertView;
	}
}