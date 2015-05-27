package com.example.amado.pharmacymap;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Amado on 26/05/2015.
 */
public class PharmacyDetailFragment extends Fragment{
    private Pharmacy mPharmacy;
    private ArrayList<Pharmacy> mPharmacies;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle(R.string.pharmacy_detail);
        int position = getArguments().getInt(PharmacyListFragment.EXTRA_POSITION);
        mPharmacies = PharmacyLocations.getPharmacies();
        mPharmacy= mPharmacies.get(position);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_pharmacy_detail, container, false);

        TextView detailName = (TextView)v.findViewById(R.id.detail_pharmacy_name);
        detailName.setText(mPharmacy.getName());
        TextView detailAddress = (TextView)v.findViewById(R.id.detail_pharmacy_address);
        detailAddress.setText(mPharmacy.getAddress());
        TextView detailPhone = (TextView)v.findViewById(R.id.detail_pharmacy_phone);
        detailPhone.setText(mPharmacy.getPhone());
        detailPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri = "tel:" + mPharmacy.getPhone().trim();
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(uri));
                startActivity(i);
            }
        });

        TextView detailDistance = (TextView)v.findViewById(R.id.detail_pharmacy_distance);
        detailDistance.setText("Distance: "+doubleToString(mPharmacy.getDistance())+"Km");
        TextView detailLatitude = (TextView)v.findViewById(R.id.detail_pharmacy_latitude);
        detailLatitude.setText("Latitude: "+doubleToString(mPharmacy.getLatitude()));
        TextView detailLongitude = (TextView)v.findViewById(R.id.detail_pharmacy_longitude);
        detailLongitude.setText("Longitude: "+doubleToString(mPharmacy.getLongitude()));



        return v;
    }

    public static PharmacyDetailFragment newIntance(int position){
        Bundle args = new Bundle();
        args.putInt(PharmacyListFragment.EXTRA_POSITION, position);
        PharmacyDetailFragment fragment = new PharmacyDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private  String doubleToString(Double num){
        String string = ""+num;
        String smallString = " ";
        try{
            smallString= string.substring(0,8);
        }catch (Exception e){
            smallString = string;
        }
        return smallString;
    }




}
