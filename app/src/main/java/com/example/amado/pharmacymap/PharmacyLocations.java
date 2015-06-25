package com.example.amado.pharmacymap;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Amado on 25/05/2015.
 */
public class PharmacyLocations {
    private static final String TAG = "PharmacyLocations";

    public static ArrayList<Pharmacy> sPharmacies;

    public static void setPharmacies(ArrayList<Pharmacy> pharmacies) {
        sPharmacies = pharmacies;
    }

    public static ArrayList<Pharmacy> getPharmacies() {
        if (sPharmacies == null) {
                sPharmacies = DataSource.getPharmaciesFromDataBase();
            return sPharmacies;

        } else {
            return sPharmacies;
        }
    }

}
