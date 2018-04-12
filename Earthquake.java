package com.example.android.quakereport;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Puspak Biswas on 09-01-2018.
 */

public class Earthquake {

    private double magnitude;
    private String place;
    private String dateToDisplay;
    private String url;

    public Earthquake(double m, String p, long d, String u){
        magnitude = m;
        place = p;
        Date date = new Date(d);
        SimpleDateFormat simpleDate = new SimpleDateFormat("MMM dd, yyyy/h:mm a");
        dateToDisplay = simpleDate.format(date);
        url = u;
    }

    public double getMagnitude(){
        return this.magnitude;
    }

    public String getPlace(){
        return this.place;
    }

    public String getDate(){
        return this.dateToDisplay;
    }

    public String getUrl(){return this.url;}
}
