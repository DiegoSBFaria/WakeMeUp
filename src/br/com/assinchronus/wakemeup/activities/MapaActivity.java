package br.com.assinchronus.wakemeup.activities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboMapActivity;
import roboguice.inject.InjectView;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import br.com.assinchronus.wakemeup.R;
import br.com.assinchronus.wakemeup.model.Address;
import br.com.assinchronus.wakemeup.model.Position;
import br.com.assinchronus.wakemeup.model.StopOverlay;
import br.com.assinchronus.wakemeup.service.Http;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.inject.Inject;

public class MapaActivity extends RoboMapActivity {

	@InjectView(R.id.mapaView)
	private MapView mapa;
	@Inject
	private LocationManager locationManager;

	private Location location;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapa);

		Address address = (Address) getIntent().getSerializableExtra("address");

		address.position = new Position(address.latitude, address.longitude);

		location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		mapa.getController().setCenter(new Position(location));

		StringBuilder url = getUrl(new Position(location), address.position);

		findGeoPoints(url);
	}

	public void findGeoPoints(StringBuilder url) {
		Http http = new Http();
		try {
			String kml = http.doGet(url.toString());

			kml = kml.split("coordinates")[43].replace(">", "").replace("</", "").replace("0.000000", "");

			ArrayList<GeoPoint> poly = new ArrayList<GeoPoint>();

			String[] geos = kml.split(",");

			for (int i = 0; i < geos.length - 1; i += 2) {
				double lat = Double.parseDouble(geos[i + 1]);
				double lng = Double.parseDouble(geos[i]);

				Position p = new Position(lat, lng);
				poly.add(p);
			}

			drawPath(poly, 1);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void drawPath(ArrayList<GeoPoint> geoPoints, int color) {
		List<Overlay> overlays = mapa.getOverlays();

		for (int i = 1; i < geoPoints.size(); i++) {
			overlays.add(new StopOverlay(geoPoints.get(i - 1), geoPoints.get(i)));
		}
	}

	@Override
	protected boolean isRouteDisplayed() {
		return true;
	}

	public StringBuilder getUrl(GeoPoint src, GeoPoint dest) {
		StringBuilder urlString = new StringBuilder();

		urlString.append("http://maps.google.com/maps?f=d&hl=en");
		urlString.append("&saddr=");
		urlString.append(Double.toString((double) src.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		urlString.append(Double.toString((double) src.getLongitudeE6() / 1.0E6));
		urlString.append("&daddr=");// to
		urlString.append(Double.toString((double) dest.getLatitudeE6() / 1.0E6));
		urlString.append(",");
		urlString.append(Double.toString((double) dest.getLongitudeE6() / 1.0E6));
		urlString.append("&ie=UTF8&0&om=0&output=kml");

		return urlString;
	}

}