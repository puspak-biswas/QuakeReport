package com.example.android.quakereport;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.start;

/**
 * Created by Puspak Biswas on 09-01-2018.
 */

public class EarthquakeAdapter extends ArrayAdapter<Earthquake> {

    private static final String LOCATION_SEPARATOR = "of";

    public EarthquakeAdapter(Activity a, List<Earthquake> e){
        super(a,0,e);
    }
//Override the getView method of array adapter
    public View getView(final int position, View convertView, ViewGroup parent){
         View listItem = convertView;
        if (listItem == null){
            listItem = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item,parent,false);
        }
        final Earthquake currentEarthquake = getItem(position);

        TextView magnitude = (TextView) listItem.findViewById(R.id.magnitude);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitude.getBackground();
        double mag = currentEarthquake.getMagnitude();
        int circleColorValue = getColor(mag);
        magnitudeCircle.setColor(circleColorValue);
        DecimalFormat formatter = new DecimalFormat("0.0");
        String textMag = formatter.format(mag);
        magnitude.setText(textMag);

        TextView offset = (TextView) listItem.findViewById(R.id.offset);
        TextView primary = (TextView) listItem.findViewById(R.id.primary);
        String textPlace = currentEarthquake.getPlace();
        int indexOfOF = textPlace.indexOf(LOCATION_SEPARATOR);
        String primaryLocation;
        String offsetLocation;
        if (indexOfOF == -1){
            offsetLocation = getContext().getString(R.string.near_the);
            primaryLocation = textPlace;
        }else{
            offsetLocation = textPlace.substring(0,indexOfOF+2);
            primaryLocation = textPlace.substring(indexOfOF+3);
        }
        offset.setText(offsetLocation);
        primary.setText(primaryLocation);

        TextView date = (TextView) listItem.findViewById(R.id.date);
        TextView time = (TextView) listItem.findViewById(R.id.time);
        String textDate = currentEarthquake.getDate();
        String[] parts = textDate.split("/");
        date.setText(parts[0]);
        time.setText(parts[1]);

        //just testing how listview sends position to array adapter
        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getContext();
                String toastText = "clicked" + position;
                int duration = Toast.LENGTH_SHORT;
                Toast.makeText(context,toastText,duration).show();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(currentEarthquake.getUrl()));
                context.startActivity(intent);
            }
        });

        return listItem;
    }

    private int getColor(double m){
        int magInt = (int) Math.round(m);
        int colReturn;
        switch (magInt){
            case 0:
            case 1:
            case 2:
                colReturn= ContextCompat.getColor(getContext(), R.color.Magnitude1);
            break;
            case 3:
                colReturn= ContextCompat.getColor(getContext(), R.color.Magnitude3);
            break;
            case 4:
                colReturn= ContextCompat.getColor(getContext(), R.color.Magnitude4);
            break;
            case 5:
                colReturn= ContextCompat.getColor(getContext(), R.color.Magnitude5);
            break;
            case 6:
                colReturn= ContextCompat.getColor(getContext(), R.color.Magnitude6);
            break;
            case 7:
                colReturn= ContextCompat.getColor(getContext(), R.color.Magnitude7);
            break;
            case 8:
                colReturn= ContextCompat.getColor(getContext(), R.color.Magnitude8);
            break;
            case 9:
                colReturn= ContextCompat.getColor(getContext(), R.color.Magnitude9);
            break;
            case 10:
                colReturn= ContextCompat.getColor(getContext(), R.color.Magnitude10);
            break;
            default:
                colReturn=ContextCompat.getColor(getContext(), R.color.Magnitude10plus);
        }
        return colReturn;
    }
}
