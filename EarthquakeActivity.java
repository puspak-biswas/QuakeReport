/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String queryString = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        AsyncClass task = new AsyncClass();
        task.execute(queryString);
    }

    private void updateUI(List<Earthquake> earthquakes){
        EarthquakeAdapter adapter = new EarthquakeAdapter(this,earthquakes);
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);
        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }

    private class AsyncClass extends AsyncTask<String,Void,List<Earthquake>>{
        protected List<Earthquake> doInBackground(String...a){
            List<Earthquake> earthquakes = null;
            if(a[0] != null) {
                // Create a fake list of earthquake locations.
                earthquakes = QueryUtils.fetchEarthquakeData(a[0]);
            }
                return earthquakes;
        }
        protected void onPostExecute(List<Earthquake> e){
            if (e.isEmpty()){
                return;
            }
            updateUI(e);
        }
    }
}
