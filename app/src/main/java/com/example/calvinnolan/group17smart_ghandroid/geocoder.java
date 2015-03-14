package com.example.calvinnolan.group17smart_ghandroid;

/**
 * Created by calvinnolan on 14/03/15.
 */

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class geocoder {

    private String nominatimUrl;
    private String nominatimReverseUrl;
    private String bounds;
    private int timeoutInMillis = 10000;
    private String userAgent = "GraphHopper Web Service";
    private double[] points = {-1,-1};
    private String placeString = "";

    public geocoder(){

        nominatimUrl = "http://open.mapquestapi.com/nominatim/v1/search.php";
        nominatimReverseUrl = "http://open.mapquestapi.com/nominatim/v1/reverse.php";

    }

    // Sets the bounds of the area that will be searched in e.g. Only search for places in Dublin City.
    // minLon, minLat, maxLon, maxLat => left, top, right, bottom
    public void setBounds(double minLong, double maxLat, double maxLong, double minLat){

        bounds = minLong + "," + maxLat + "," + maxLong + "," + minLat;

    }

    // Converts the name of a place into it's latitude and longitude points as accurately as possible.
    public double[] name2place(final String name) throws Exception {
        new Thread(new Runnable() {

            public void run() {

                try {
                    //Formulate the url request.
                    String url = nominatimUrl + "?format=json&q=" + WebHelper.encodeURL(name) + "&limit=3";
                    if (bounds != null) {
                        url += "&bounded=1&viewbox=" + bounds;
                    }

                    HttpURLConnection hConn = openConnection(url);
                    String str = WebHelper.readString(hConn.getInputStream());
                    JSONObject json = new JSONArray(str).getJSONObject(0);
                    points[0] = json.getDouble("lat");
                    points[1] = json.getDouble("lon");
                } catch (Exception E) {

                }
            }
        }).start();
        return this.getPoints();
    }

    // Returns the coordinates of a name from name2place.
    // Used to make the function thread-safe.
    private double[] getPoints(){
        while(points[1] == -1)
        {

        }
        return points;
    }

    //Converts coordinates into it's address.
    public String place2name(final double[] place) throws Exception{
        new Thread(new Runnable() {

            public void run() {

                try {
                    String url = nominatimReverseUrl + "?lat=" + place[0] + "&lon=" + place[1]
                            + "&format=json&zoom=16";
                    HttpURLConnection hConn = openConnection(url);
                    String str = WebHelper.readString(hConn.getInputStream());
                    JSONObject json = new JSONObject(str);

                    JSONObject address = json.getJSONObject("address");
                    String name = "";
                    if (address.has("road"))
                    {
                        name += address.get("road") + ", ";
                    }
                    if (address.has("postcode"))
                    {
                        name += address.get("postcode") + " ";
                    }
                    if (address.has("city"))
                    {
                        name += address.get("city") + ", ";
                    } else if (address.has("county"))
                    {
                        name += address.get("county") + ", ";
                    }
                    if (address.has("state"))
                    {
                        name += address.get("state") + ", ";
                    }
                    if (address.has("country"))
                    {
                        name += address.get("country");
                    }

                    placeString = name;

                }
                catch(Exception E)
                {

                }
            }
        }).start();

        return this.getPlaceName();
    }

    // Returns the address of the coordinates passed to place2name.
    // Used to make the function thread-safe.
    private String getPlaceName(){
        while(placeString.equals(""))
        {

        }
        return placeString;
    }

    HttpURLConnection openConnection( String url ) throws IOException
    {
        HttpURLConnection hConn = (HttpURLConnection) new URL(url).openConnection();;
        hConn.setRequestProperty("User-Agent", userAgent);
        hConn.setRequestProperty("content-charset", "UTF-8");
        hConn.setConnectTimeout(timeoutInMillis);
        hConn.setReadTimeout(timeoutInMillis);
        hConn.connect();
        return hConn;
    }

}