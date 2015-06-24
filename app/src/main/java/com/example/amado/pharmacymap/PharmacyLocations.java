package com.example.amado.pharmacymap;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Amado on 25/05/2015.
 */
public class PharmacyLocations {
    private static final String TAG = "PharmacyLocations";

    public static ArrayList<Pharmacy> sPharmacies;

    public static ArrayList<Pharmacy> getPharmacies() {
        if (sPharmacies == null) {
                sPharmacies = DataSource.getPharmaciesFromDataBase();
                if(sPharmacies.isEmpty()) {
                    Log.d(TAG, "Failed to get data from Database, creating new one");
                    DataSource.createDb();
                    sPharmacies = DataSource.getPharmaciesFromDataBase();
                }else{
                    Log.d(TAG, "Data fetched from Db");
                }
            return sPharmacies;

        } else {
            return sPharmacies;
        }
    }



    private static boolean DbHasObjects(){
        if(sPharmacies.isEmpty()){
            return false;
        }else{
            return true;
        }
    }

}
