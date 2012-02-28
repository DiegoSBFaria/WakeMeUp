package br.com.assinchronus.wakemeup.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectView;
import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import br.com.assinchronus.wakemeup.R;
import br.com.assinchronus.wakemeup.adapter.AddressAdapter;

public class HomeActivity extends RoboActivity implements OnClickListener, OnItemClickListener {

	@InjectView(R.id.address)
	private EditText address;
	@InjectView(R.id.search)
	private Button search;
	@InjectView(R.id.address_list)
	private ListView listAddresses;

	private ProgressDialog progressDialog;

	ArrayList<br.com.assinchronus.wakemeup.model.Address> addresses = new ArrayList<br.com.assinchronus.wakemeup.model.Address>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		search.setOnClickListener(this);
		listAddresses.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		String addressStr = this.address.getText().toString();
		searchAddress(addressStr);
	}

	private void searchAddress(String addressStr) {
		progressDialog = ProgressDialog.show(this, getString(R.string.app_name), getString(R.string.searching));

		new AsyncTask<String, Void, List<Address>>() {

			@Override
			protected List<Address> doInBackground(String... params) {
				List<Address> addresses = null;
				try {
					addresses = new Geocoder(HomeActivity.this, Locale.getDefault()).getFromLocationName(params[0], 10);
				} catch (IOException e) {
					Log.e(HomeActivity.class.getSimpleName(), "Não foi possivel obter o endereço", e);
				}
				return addresses;
			}

			@Override
			protected void onPostExecute(List<Address> addresses) {
				super.onPostExecute(addresses);
				for (Address address : addresses) {
					br.com.assinchronus.wakemeup.model.Address addressAux = new br.com.assinchronus.wakemeup.model.Address();
					addressAux.street = address.getThoroughfare();
					addressAux.zip = address.getPostalCode();
					addressAux.subAdmin = address.getSubLocality();
					addressAux.city = address.getAdminArea();
					addressAux.latitude = address.getLatitude();
					addressAux.longitude = address.getLongitude();
					HomeActivity.this.addresses.add(addressAux);
				}

				listAddresses.setAdapter(new AddressAdapter(HomeActivity.this.addresses, HomeActivity.this));

				progressDialog.cancel();
			}
		}.execute(new String[] { addressStr });
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		AddressAdapter adapter = (AddressAdapter) arg0.getAdapter();
		br.com.assinchronus.wakemeup.model.Address item = adapter.getItem(position);

		Intent intent = new Intent(this, MapaActivity.class);
		intent.putExtra("address", item);
		startActivity(intent);
	}
}