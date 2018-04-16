package com.example.android.quakereport;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */
public final class QueryUtils {

    /** Sample JSON response for a USGS query */
   /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link Earthquake} objects that has been built up from
     * parsing a JSON response.
     */
    public static List<Earthquake> fetchEarthquakeData(String queryString) {

        //create URL
        URL queryURL = getURL(queryString);

        //Get JSON response using query
        String JsonResponse = makeHttpConnection(queryURL);
        Log.i("Responseeee : ",JsonResponse);

        List<Earthquake> earthquakes = extractFeatureFromJson(JsonResponse);

        // Return the list of earthquakes
        return earthquakes;
    }

    private static URL getURL(String a){
        URL url = null;
        try {
            url = new URL(a);
        } catch (MalformedURLException e) {
            Log.e("QueryUtils", "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpConnection(URL a){
        String jsonResponse = "";
        if (a == null){
            return jsonResponse;
        }
        InputStream inputStream = null;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) a.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //check response from website
            if(urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }
            else{
                Log.e("QueryUtils","Error connecting to website responsecode:"+urlConnection.getResponseCode());
            }
        }catch(IOException e){
            Log.e("QueryUtils","Error connecting",e);
        }
        finally {
            if (urlConnection != null){
                urlConnection.disconnect();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream i) throws IOException{
        StringBuilder builder = new StringBuilder();
        if(i != null){
            InputStreamReader streamReader = new InputStreamReader(i, Charset.forName("UTF-8"));
            BufferedReader buffer = new BufferedReader(streamReader);
            String line = buffer.readLine();
            while (line != null){
                builder.append(line);
                line = buffer.readLine();
            }
        }
        return builder.toString();
    }

    private static List<Earthquake> extractFeatureFromJson(String JsonResponse){
        ArrayList<Earthquake> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject eq = new JSONObject(JsonResponse);
            JSONArray features = eq.getJSONArray("features");
            for(int i=0;i<features.length(); i++){
                JSONObject e = features.getJSONObject(i);
                JSONObject properties = e.getJSONObject("properties");
                double mag = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                earthquakes.add(new Earthquake(mag,place,time,url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return earthquakes;

    }

}
