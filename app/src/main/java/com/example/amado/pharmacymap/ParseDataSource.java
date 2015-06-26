package com.example.amado.pharmacymap;

import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amado on 23/06/2015.
 */
public class ParseDataSource {
    private static final String TAG = "ParseDataSource";
    private static final String PHARMACY_LOCATION_CLASS = "PharmacyLocations";
    private static final String COLUMN_PHARMACY_NAME = "name";
    private static final String COLUMN_PHARMACY_ADDRESS = "address";
    private static final String COLUMN_PHARMACY_PHONE = "phone";
    private static final String COLUMN_PHARMACY_LATITUDE = "latitude";
    private static final String COLUMN_PHARMACY_LONGITUDE = "longitude";
    private static final String COLUMN_PHARMACY_OBJECT_ID = "objectId";
    private  static Listener mListener;
    private Context mContext;

    ParseDataSource(Context context, Listener listener){
        mContext =context;
        mListener = listener;
    }


    public void uploadPharmacyLocations(ArrayList<Pharmacy> pharmacies){
        if(pharmacies==null) return;
        for(Pharmacy pharmacy: pharmacies){
            uploadPharmacyToParse(pharmacy);
        }
    }

    public void uploadPharmacyToParse(Pharmacy pharmacy){
        ParseObject parsePharmacy = new ParseObject(PHARMACY_LOCATION_CLASS);
        parsePharmacy.put(COLUMN_PHARMACY_NAME, pharmacy.getName());
        parsePharmacy.put(COLUMN_PHARMACY_ADDRESS, pharmacy.getAddress());
        parsePharmacy.put(COLUMN_PHARMACY_PHONE, pharmacy.getPhone());
        parsePharmacy.put(COLUMN_PHARMACY_LATITUDE, pharmacy.getLatitude());
        parsePharmacy.put(COLUMN_PHARMACY_LONGITUDE, pharmacy.getLongitude());
        parsePharmacy.saveInBackground();
    }

    public static void downloadPharmaciesFromParse(){
        final ArrayList<Pharmacy> pharmacies = new ArrayList<>();
        ParseQuery<ParseObject> pharmaciesQuery = new ParseQuery<ParseObject>(PHARMACY_LOCATION_CLASS);
        pharmaciesQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> list, ParseException e) {
                if (e == null) {
                    for (ParseObject parseObject : list) {
                        pharmacies.add(ParseToPharmacy(parseObject));
                    }
                    if(mListener!= null&&pharmacies.size()>0){
                        mListener.onFetchedParsePharmacies(pharmacies);
                    }
                }

            }

        });

    }

    public static Pharmacy ParseToPharmacy(ParseObject object){
        Pharmacy pharmacy = new Pharmacy();
        pharmacy.setName(object.getString(COLUMN_PHARMACY_NAME));
        pharmacy.setAddress(object.getString(COLUMN_PHARMACY_ADDRESS));
        pharmacy.setPhone(object.getString(COLUMN_PHARMACY_PHONE));
        pharmacy.setLatitude(object.getDouble(COLUMN_PHARMACY_LATITUDE));
        pharmacy.setLongitude(object.getDouble(COLUMN_PHARMACY_LONGITUDE));
        pharmacy.setObjectId(object.getString(COLUMN_PHARMACY_OBJECT_ID));
        return pharmacy;
    }

    public interface Listener{
        public void onFetchedParsePharmacies(ArrayList<Pharmacy> pharmaciesFromParse);
    }



}
