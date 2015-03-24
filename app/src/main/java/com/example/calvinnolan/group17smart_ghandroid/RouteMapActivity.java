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
import android.os.Bundle;
import android.widget.Toast;

public class RouteMapActivity extends Activity {
    private MapView         mMapView;
    private MapController   mMapController;
    private ItemizedIconOverlay<OverlayItem> itemizedIconOverlay;
    private PathOverlay thePath;
    private double[] routePoints;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        routePoints = intent.getDoubleArrayExtra(DisplayMessageActivity.EXTRA_MESSAGE);

        setContentView(R.layout.activity_route_map);
        mMapView = (MapView) findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMapView.setBuiltInZoomControls(true);

        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(14);

        /*for(int i = 0; i < routePoints.length; i+=2){
            GeoPoint p = new GeoPoint(routePoints[i], routePoints[i+1]);
            thePath.addPoint(p);
        }*/
        mMapView.setMultiTouchControls(true);
        mMapView.setClickable(true);
        GeoPoint gPt = new GeoPoint(53.348256,-6.255879);
        mMapController.setCenter(gPt);
        //mMapView.getOverlays().add(thePath);
    }
}