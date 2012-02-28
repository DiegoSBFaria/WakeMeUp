package br.com.assinchronus.wakemeup.model;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

public class StopOverlay extends Overlay {
	public GeoPoint position;
	public GeoPoint currentPosition;
	public Paint paint;

	public StopOverlay(GeoPoint position, GeoPoint currentPosition) {
		this.position = position;
		this.currentPosition = currentPosition;
		paint = new Paint();
	}

	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		Projection projection = mapView.getProjection();
		Paint paint = new Paint();
		Point point = new Point();
		projection.toPixels(currentPosition, point);
		paint.setColor(Color.BLUE);
		Point point2 = new Point();
		projection.toPixels(position, point2);
		paint.setStrokeWidth(7);
		paint.setAlpha(150);
		canvas.drawLine(point.x, point.y, point2.x, point2.y, paint);
		super.draw(canvas, mapView, shadow);
	}

	protected int getY(Point p, Bitmap bitmap) {
		return p.y - bitmap.getHeight() / 2;
	}

	protected int getX(Point p, Bitmap bitmap) {
		return p.x - bitmap.getWidth() / 2;
	}
}