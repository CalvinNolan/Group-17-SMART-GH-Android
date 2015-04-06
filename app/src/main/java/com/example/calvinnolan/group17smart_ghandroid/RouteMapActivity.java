package com.example.calvinnolan.group17smart_ghandroid;

import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.PathOverlay;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class RouteMapActivity extends Activity {
    private MapView         mMapView;
    private MapController   mMapController;
    private ItemizedIconOverlay<OverlayItem> itemizedIconOverlay;
    private PathOverlay thePath;
    private String encodedRoute;
    private List<GeoPoint> decodedPoints;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        encodedRoute = intent.getStringExtra(DisplayMessageActivity.ENCODED_ROUTE);

        setContentView(R.layout.activity_route_map);
        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMapView.setBuiltInZoomControls(true);

        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(16);

        decodedPoints = routeParser.decodePoly(encodedRoute);

        thePath = new PathOverlay(Color.BLUE, this);

        //Add the Geopoints to the Overlay Path.
        for(int i = 0; i < decodedPoints.size(); i++){
           thePath.addPoint(decodedPoints.get(i).getLatitudeE6(), decodedPoints.get(i).getLongitudeE6());
        }

        mMapView.setMultiTouchControls(true);
        mMapView.setClickable(true);

        GeoPoint center = new GeoPoint(decodedPoints.get(0).getLatitudeE6()+10000, decodedPoints.get(0).getLongitudeE6()-15000);
        mMapController.setCenter(center);

        mMapView.getOverlays().add(thePath);

        Paint pPaint = thePath.getPaint();
        pPaint.setStrokeWidth(11);
        thePath.setPaint(pPaint);
    }
}