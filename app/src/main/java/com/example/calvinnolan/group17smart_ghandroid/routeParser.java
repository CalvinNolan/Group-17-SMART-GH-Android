package com.example.calvinnolan.group17smart_ghandroid;
import android.location.Geocoder;

import org.json.*;
import org.osmdroid.util.GeoPoint;

import java.util.ArrayList;
import java.util.List;

/*
 * When JSON String is passed in as it is sent from the GET request,
 * the JSONParser has a shitfit on the points:"..." object due to random
 * characters in the encoded string. Need to fix this
 * 
 * 
 */
 
public class routeParser {
    private JSONObject routeString;
    private JSONArray instructions;
    private int distance;
    private int eta;
    private String points;
    private String[][] route;
    private List<GeoPoint> decodedPoints;
   
   
    public routeParser(String route) throws Exception{
            routeString = new JSONObject(route);      
            distance = routeString.getInt("distance");
            eta = routeString.getInt("time");
            JSONObject temp = routeString.getJSONArray("paths").getJSONObject(0);
            instructions = temp.getJSONArray("instructions");
           
            //Points - "points":"giqdI`wce@LBVRvAjAjA~@|BtBpAlApGmW\\wA`DfDlBfBFQrEkIlB{CrCiDT?HM?Cb@m@Ta@pAsCp@sBh@oB|\\`Lz@XK\\"
            //characters within the points string are being read as illegal escape characters            
            points = temp.getString("points");
    }
    public void parseRoute() throws Exception{
                int t, d;
               
            route = new String[instructions.length()][3];
            for (int i = 0; i < instructions.length(); i++)
            {
                route[i][0] = instructions.getJSONObject(i).getString("text");
                t = instructions.getJSONObject(i).getInt("time");
                route[i][1] = "" + t;
                d = instructions.getJSONObject(i).getInt("distance");
                route[i][2] = "" + d;
            }
    }
    public int getDistance(){
            return distance;
    }
    public String getEncodedPoints(){
        return points;
    }
    public List<GeoPoint> getDecodedPoints(){
        return decodedPoints;
    }
    public int getETA(){
            return eta;
    }
    public String[][] getRoute(){
            return route;
    }
    public List<GeoPoint> decodePoly(String encoded) {

        List<GeoPoint> poly = new ArrayList<GeoPoint>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            GeoPoint p = new GeoPoint((int) (((double) lat / 1E5) * 1E6),
                    (int) (((double) lng / 1E5) * 1E6));
            poly.add(p);
        }

        return poly;
    }
}

