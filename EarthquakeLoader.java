package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Puspak Biswas on 10-05-2018.
 */

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {
    private String URL;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        URL = url;
        // TODO: Finish implementing this constructor
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Earthquake> loadInBackground() {
        List<Earthquake> earthquakes = null;
        if(URL != null) {
            // Create a fake list of earthquake locations.
            earthquakes = QueryUtils.fetchEarthquakeData(URL);
        }
        return earthquakes;
        // TODO: Implement this method
    }
}
