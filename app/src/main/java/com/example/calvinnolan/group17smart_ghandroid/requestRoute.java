package com.example.calvinnolan.group17smart_ghandroid;

/**
 * Created by calvinnolan on 08/03/15.
 */
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class requestRoute{

    private static String url;
    private static String results;

    public requestRoute(String route)
    {
        url = route;
        results = "";
    }

    public requestRoute(double fromLong, double fromLat, double toLong, double toLat, String weighting, String vehicle)
    {
        results = "";

        url = "http://172.16.160.132:8989/route/";
        url += "?point=" + fromLong + "%2C" + fromLat;
        url += "&point=" + toLong + "%2C" + toLat;
        url += "&vehicle=" + vehicle;
        if(weighting.equals("Least Noise Pollution")) url += "&weighting=least_noise";
        else if(weighting.equals("Least Air Pollution")) url += "&weighting=least_air_pollution";
        else url += "&weighting=" + weighting;

        url += "&locale=en-US";
    }

    public String sendRoute() throws Exception{
        new Thread(new Runnable(){

            public void run(){
                try{
                    URL obj = new URL(url);
                    //Log.i("", "Url made 1/7");

                    HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
                    //Log.i("", "Connection made 2/7");

                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0");
                    connection.setRequestProperty("connection", "close");
                    //Log.i("", "Connection set 3/7");

                    BufferedReader in = new BufferedReader(
                            new InputStreamReader(connection.getInputStream()));
                    //Log.i("", "BufferedReader made 4/7");

                    String inputLine;
                    StringBuffer response = new StringBuffer();
                    //Log.i("", "StringBuffer made 5/7");

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    //Log.i("", "Response Parsed 6/7");

                    results = response.toString();
                    //Log.i("", "Results Set 7/7");
                }
                catch (Exception E){

                }
            }
        }).start();

        return this.getResults();

    }

    public String getResults(){
        while(results.equals(""))
        {

        }
        return results;
    }
}