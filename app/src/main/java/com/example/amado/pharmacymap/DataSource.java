package com.example.amado.pharmacymap;

import android.content.Context;

import com.parse.ParseObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amado on 27/05/2015.
 */
public class DataSource {

   private static ArrayList<Pharmacy> sPharmacies;


    public static void createDb(ArrayList<Pharmacy> pharmacies){
            sPharmacies = pharmacies;
            for (Pharmacy pharmacy : sPharmacies) {
                pharmacy.save();
            }

    }

    public static ArrayList<Pharmacy> getPharmaciesFromDataBase(){
            List<Pharmacy> pharmacies = Pharmacy.listAll(Pharmacy.class);
        return (ArrayList<Pharmacy>)pharmacies;
        }



}
