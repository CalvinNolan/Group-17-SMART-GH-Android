package com.example.calvinnolan.group17smart_ghandroid;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity{

    public final static String EXTRA_MESSAGE = "com.example.calvinnolan.group17smart_ghandroid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.typesOfRoutes, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
    /** called when user clicks View Map button */
    public void viewMap(View view) throws Exception{
        String from, to = "";
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.editText1);
        to = editText.getText().toString();

        editText = (EditText) findViewById(R.id.editText2);
        from = editText.getText().toString();

        if(!to.equals("") && !from.equals("")) {
            geocoder toGeocoder = new geocoder();
            geocoder fromGeocoder = new geocoder();

            //Hardcoding the bounds to the greater Dublin region.
            toGeocoder.setBounds(-6.38390, 53.40870, -6.07250, 53.26600);
            fromGeocoder.setBounds(-6.38390, 53.40870, -6.07250, 53.26600);

            double[] toPoints = toGeocoder.name2place(to);
            double[] fromPoints = fromGeocoder.name2place(from);

            double[] allPoints = new double[4];
            allPoints[0] = toPoints[0];
            allPoints[1] = toPoints[1];
            allPoints[2] = fromPoints[0];
            allPoints[3] = fromPoints[1];

            Intent intent = new Intent(this, Selectmap.class);
            intent.putExtra(EXTRA_MESSAGE, allPoints);
            startActivity(intent);
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Ensure both locations are not blank!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    /** Called when the user clicks the Search button */
    public void searchRoute(View view) throws Exception {

        String from, to = "";
        // Do something in response to button
        EditText editText = (EditText) findViewById(R.id.editText1);
        to = editText.getText().toString();
        Log.i("SearchRoute", to);

        editText = (EditText) findViewById(R.id.editText2);
        from = editText.getText().toString();
        Log.i("SearchRoute", from);

        if(!to.equals("") && !from.equals("")) {

            String transport = "";

            switch(view.getId()) {
                case R.id.imageButton:
                    transport = "car";
                    break;
                case R.id.imageButton2:
                    transport = "bike";
                    break;
                case R.id.imageButton3:
                    transport = "foot";
                    break;
                default :
                    transport = "foot";
            }

            Spinner spinner = (Spinner) findViewById(R.id.spinner1);
            String routeSelected = spinner.getSelectedItem().toString();

            Log.i("SearchRoute", routeSelected);

            geocoder toGeocoder = new geocoder();
            geocoder fromGeocoder = new geocoder();

            //Hardcoding the bounds to the greater Dublin region.
            toGeocoder.setBounds(-6.38390, 53.40870, -6.07250, 53.26600);
            fromGeocoder.setBounds(-6.38390, 53.40870, -6.07250, 53.26600);

            double[] toPoints = toGeocoder.name2place(to);
            double[] fromPoints = fromGeocoder.name2place(from);

            Log.i(to, "lat: " + toPoints[0] + "long: " + toPoints[1]);

            Log.i(from, "lat: " + fromPoints[0] + "long: " + fromPoints[1]);

            requestRoute request = new requestRoute(fromPoints[0], fromPoints[1], toPoints[0], toPoints[1], routeSelected, transport);
            String route = request.sendRoute();

            Log.i("", "results from requestRoute: " + route);

            Intent intent = new Intent(this, DisplayMessageActivity.class);
            intent.putExtra(EXTRA_MESSAGE, route);
            startActivity(intent);
        }
        else{
            Context context = getApplicationContext();
            CharSequence text = "Ensure both locations are not blank!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }
}
