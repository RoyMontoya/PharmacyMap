package com.example.amado.pharmacymap;

import android.app.FragmentManager;
import android.app.ListFragment;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;


/**
 * A placeholder fragment containing a simple view.
 */
public class PharmacyListFragment extends ListFragment {

    private ArrayList<Pharmacy> mPharmacies;
    private PharmacyAdapter mAdapter;
    public static final String EXTRA_POSITION= "position";

    public PharmacyListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mPharmacies= PharmacyLocations.getPharmacies();
        mAdapter = new PharmacyAdapter(mPharmacies);
        setListAdapter(mAdapter);

    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {

        PharmacyDetailFragment pharmacyDetailFragment =PharmacyDetailFragment.newIntance(position);

        FragmentManager fm = getFragmentManager();
        fm.beginTransaction().replace(R.id.container,pharmacyDetailFragment)
                .addToBackStack("list")
                .commit();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_pharmacy_list, container, false);

        return v;
    }

    private class PharmacyAdapter extends ArrayAdapter<Pharmacy>{


        public PharmacyAdapter(ArrayList<Pharmacy> pharmacies) {
            super(getActivity(), 0, pharmacies);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView== null){
                convertView =getActivity().getLayoutInflater().inflate(R.layout.list_pharmacy_item, null );
            }
                Pharmacy pharmacy = getItem(position);

            TextView name = (TextView)convertView.findViewById(R.id.item_pharmacy_name);
            name.setText(pharmacy.getName());
            TextView address = (TextView)convertView.findViewById(R.id.item_pharmacy_address);
            address.setText(pharmacy.getAddress());
            TextView distance = (TextView)convertView.findViewById(R.id.item_pharmacy_distance);
            distance.setText("distance: "+doubleToString(pharmacy.getDistance())+"km");


            return convertView;
        }
    }

    public static  String doubleToString(Double num){
        String string = ""+num;
        String smallString = " ";
        try{
            smallString= string.substring(0,3);
        }catch (Exception e){
            smallString = string;
        }
        return smallString;
    }
}
