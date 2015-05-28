package com.example.amado.pharmacymap;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Amado on 25/05/2015.
 */
public class PharmacyLocations {
    private static final String TAG = "PharmacyLocations";
    private static String[] name = {"Farmacon Morelos", "Farmacia Benavides", "Farmacia Similares", "Farmacia VH", "FarmaAhorra", "Cruz Rosa", "Farmacia DH", "Farmacia Vida", "Farmacia Avanzada", "Farmacia Sahuaro"};
    private static String[] address = {"Bv. morelos #83", "Perf. Norte # 189", "Calle de la Reforma #208", "Xoloth #244", "Av.Venado Bura #1", "Yanez #145", "Del Seri #157", "Luis Encinas #400", "Nayarit #9", "Solidaridad #170"};
    private static String[] phone = {"01-622-210-6665", "01-662-215-5254", "01-662-212-1618", "01-6222-237-3311", "01-622-252-6379", "01-662-212-0081", "01-662-251-7944", "01-662-217-1082", "01-662-210-9610", "01-662-260-0368"};
    private static double[] latitude = {29.1127, 29.1106, 29.0797, 29.0242, 29.0081, 29.0806, 29.0760, 29.0948, 29.0953, 29.1024};
    private static double[] longitude = {-110.9506, -110.9651, -110.9657, -110.9486, -110.9318, -110.9565, -110.9371, -110.9382, -110.9546, -110.9930};


    public static ArrayList<Pharmacy> sPharmacies;

    public static ArrayList<Pharmacy> getPharmacies() {
        if (sPharmacies == null) {
            sPharmacies = DataSource.createPharmacies();
            /*
            try {
                sPharmacies = DataSource.getPharmaciesFromDataBase();
            } catch (Exception e) {
                Log.d(TAG, "Failed to get data from Database");
            }
            if (sPharmacies == null) {
                DataSource.createDataBase();
                Log.d(TAG, "Database Created");
                sPharmacies = DataSource.getPharmaciesFromDataBase();
            }
            */
            return sPharmacies;

        } else {
            return sPharmacies;
        }
    }

    private static ArrayList<Pharmacy> createPharmacies() {
        sPharmacies = new ArrayList<Pharmacy>();
        for (int i = 0; i < name.length; i++) {
            Pharmacy pharmacy = new Pharmacy(name[i], address[i], phone[i], latitude[i], longitude[i]);

            sPharmacies.add(pharmacy);
        }
        return sPharmacies;


    }
}
