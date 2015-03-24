package com.example.calvinnolan.group17smart_ghandroid;


import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.ItemizedIconOverlay;
import org.osmdroid.views.overlay.OverlayItem;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.Point;
import android.os.Bundle;

public class Selectmap extends Activity {
    private MapView         mMapView;
    private MapController   mMapController;
    private ArrayList<OverlayItem> overlay;
    private double[] toFromPoints;
    private OverlayItem to;
    private OverlayItem from;
    private ItemizedIconOverlay<OverlayItem> itemizedIconOverlay;
    private final String TILE_SOURCE = "../../maps/dublin-m50.osm";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        toFromPoints = intent.getDoubleArrayExtra(MainActivity.EXTRA_MESSAGE);
        setContentView(R.layout.activity_selectmap);
        mMapView = (MapView) findViewById(R.id.mapview);



        mMapView.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE);
        mMapView.setBuiltInZoomControls(true);

        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(14);

        GeoPoint startA = new GeoPoint(toFromPoints[0],toFromPoints[1]);
        GeoPoint startB = new GeoPoint(toFromPoints[2],toFromPoints[3]);
        OverlayItem from = new OverlayItem("startA", "A", startA);
        OverlayItem to = new OverlayItem("startB", "B", startB);

        overlay = new ArrayList<OverlayItem>();
        overlay.add(to);
        overlay.add(from);

        mMapController.setCenter(startA);

        itemizedIconOverlay = new ItemizedIconOverlay<OverlayItem>(this, overlay, null);
        mMapView.setMultiTouchControls(true);
        mMapView.setClickable(true);
        mMapView.getOverlays().add(itemizedIconOverlay);
    }
    /*public void addMarker(View view, MotionEvent e) {
        Projection proj = mMapView.getProjection();
        GeoPoint p = (GeoPoint)proj.fromPixels((int)e.getX(), (int)e.getY());
        if(mMapView.getOverlays() == null){
            OverlayItem from = new OverlayItem("startA", "A", p);
            itemizedIconOverlay.addItem(from);
            mMapView.getOverlays().add(itemizedIconOverlay);
        }
        else{
            OverlayItem to = new OverlayItem("startB", "B", p);
            itemizedIconOverlay.addItem(to);
            mMapView.getOverlays().clear();
            mMapView.getOverlays().add(itemizedIconOverlay);
        }
    }*/
}