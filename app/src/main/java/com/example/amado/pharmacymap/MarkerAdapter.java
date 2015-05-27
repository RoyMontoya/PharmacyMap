package com.example.amado.pharmacymap;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by Amado on 26/05/2015.
 */
public class MarkerAdapter implements GoogleMap.InfoWindowAdapter {
    private LayoutInflater mLayoutInflater;
    private View mView;
    private ArrayList<Pharmacy> mPharmacies;
    private static final String TAG = "MarkerAdapter";


    MarkerAdapter(LayoutInflater layoutInflater ){
        mLayoutInflater = layoutInflater;

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @Override
    public View getInfoContents(Marker marker) {
        if (mView == null){
            mView= mLayoutInflater.inflate(R.layout.custom_marker, null);
        }

        mPharmacies= PharmacyLocations.getPharmacies();
            Pharmacy pharmacy = mPharmacies.get(getMarkerIndex(marker));
            TextView markerName = (TextView) mView.findViewById(R.id.marker_name);
            markerName.setText(pharmacy.getName());
            TextView markerDistance = (TextView) mView.findViewById(R.id.marker_address);
            markerDistance.setText("Distance: " + PharmacyListFragment.doubleToString(pharmacy.getDistance()) + "Km");




        return mView;
    }

    private int getMarkerIndex(Marker marker){
        String stringID = marker.getId();
        String index = stringID.substring(1);
        int numberIndex = Integer.parseInt(index);
        return numberIndex;
    }
}
