public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquake>> {

    private String URL

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
