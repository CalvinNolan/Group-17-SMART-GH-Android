package com.example.calvinnolan.group17smart_ghandroid;

import org.osmdroid.util.GeoPoint;
import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class DisplayMessageActivity extends ActionBarActivity {

    public final static String EXTRA_MESSAGE = "com.example.calvinnolan.group17smart_ghandroid.DisplayMessageActivity";
    private String points;
    private List<GeoPoint> decodedPoints;
    private double[] routeLatLong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String route = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        String[][] temp;
        String parsedRoute = "";
        try{
            routeParser parser = new routeParser(route);
            parser.parseRoute();
            points = parser.getEncodedPoints();
            temp = parser.getRoute();
            decodedPoints = parser.getDecodedPoints();
            Log.i("", points);
            for(int i = 0; i < temp.length;i++){
                parsedRoute = parsedRoute + temp[i][0] + "\n";
            }
            routeLatLong = new double[decodedPoints.size() * 2];

            for(int i = 0; i < decodedPoints.size(); i+= 2){
                routeLatLong[i] = decodedPoints.get(i).getLatitude();
                routeLatLong[i+1] = decodedPoints.get(i).getLongitude();
            }
        }
        catch( Exception E ){

        }

        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(13);
        textView.setText(parsedRoute);
        setContentView(R.layout.activity_display_message);

        // Set the text view as the activity layout
        //setContentView(textView);
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
