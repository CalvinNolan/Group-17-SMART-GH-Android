package com.example.calvinnolan.group17smart_ghandroid;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DisplayMessageActivity extends ActionBarActivity {

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
            temp = parser.getRoute();
            for(int i = 0; i < temp.length;i++){
                parsedRoute = parsedRoute + temp[i][0] + "\n";
            }
        }
        catch( Exception E ){

        }


        // Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(13);
        textView.setText(parsedRoute);

        // Set the text view as the activity layout
        setContentView(textView);
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
