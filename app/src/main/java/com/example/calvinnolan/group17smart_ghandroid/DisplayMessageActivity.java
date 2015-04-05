package com.example.calvinnolan.group17smart_ghandroid;

import org.osmdroid.util.GeoPoint;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class DisplayMessageActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.example.calvinnolan.group17smart_ghandroid.DisplayMessageActivity";
    private String points;
    private List<GeoPoint> decodedPoints;
    private double[] routeLatLong;
    private ListView listView;
    private TextView distanceView;
    private TextView etaView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);

        Intent intent = getIntent();
        String route = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String[][] temp;
        List<String> parsedRoute = new ArrayList<String>();
        double distance = 0;
        double eta = 0;

        try{
            routeParser parser = new routeParser(route);
            parser.parseRoute();

            points = parser.getEncodedPoints();
            distance = parser.getDistance();
            eta = parser.getETA();
            temp = parser.getRoute();
            decodedPoints = parser.getDecodedPoints();

            Log.i("", points);
            for(int i = 0; i < temp.length;i++){
                if(i == temp.length - 1) parsedRoute.add(temp[i][0]);
                else parsedRoute.add(temp[i][0] + ".\n" + temp[i][2] + " taking " + temp[i][1] + ".");
            }

            routeLatLong = new double[decodedPoints.size() * 2];

            for(int i = 0; i < decodedPoints.size(); i+= 2){
                routeLatLong[i] = decodedPoints.get(i).getLatitude();
                routeLatLong[i+1] = decodedPoints.get(i).getLongitude();
            }
        }
        catch( Exception E ){

        }
        //Populate the Text views with the route distance and ETA.
        distanceView = (TextView) findViewById(R.id.textView2);
        distanceView.setTextSize(14);

        if(distance > 999) distanceView.setText("Distance: " + (distance / 1000) + "km");
        else distanceView.setText("Distance: " + (int) distance + "m");

        etaView = (TextView) findViewById(R.id.textView3);
        etaView.setTextSize(14);

        int hours = (int) eta / 3600;
        int minutes = (int) (eta % 3600) / 60;
        int seconds = (int) (eta % 3600) % 60;

        if(hours != 0)  etaView.setText("ETA: " + hours + "h, " + minutes + "m, "
                + seconds + "s");
        else if(minutes != 0) etaView.setText("ETA: " + minutes + "m, "
                   + seconds + "s");
        else etaView.setText("ETA: " + seconds + "s");

        //Populate the List view with the directions.
        listView = (ListView) findViewById(R.id.listView);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                parsedRoute);

        listView.setAdapter(arrayAdapter);

    }

    public void displayRoute(View view){
        Intent intent = new Intent(this, RouteMapActivity.class);
        intent.putExtra(EXTRA_MESSAGE, routeLatLong);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
