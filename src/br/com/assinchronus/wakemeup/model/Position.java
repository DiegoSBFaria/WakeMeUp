package br.com.assinchronus.wakemeup.model;

import java.io.Serializable;

import android.location.Location;

import com.google.android.maps.GeoPoint;

public class Position extends GeoPoint implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public String address;

	public Position(double latitude, double longitude) {
		super((int) (latitude * 1E6), (int) (longitude * 1E6));
	}

	public Position(int latitudeE6, int longitudeE6) {
		super(latitudeE6, longitudeE6);
	}

	public Position(Location location) {
		this(location.getLatitude(), location.getLongitude());
	}
}