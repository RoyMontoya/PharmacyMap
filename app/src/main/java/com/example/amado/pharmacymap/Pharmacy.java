package com.example.amado.pharmacymap;

import com.orm.SugarRecord;

/**
 * Created by Amado on 25/05/2015.
 */
public class Pharmacy extends SugarRecord<Pharmacy>{
    String name;
     String Address;
     String phone;
     double latitude;
     double longitude;
     double distance;
    private final int mR = 6371;

    public Pharmacy(){

    }

    public Pharmacy(String name, String address, String phone, double latitude, double longitude){
        this.name=name;
        this.Address=address;
        this.phone = phone;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public String getName() {
        return name;
    }

    public String getAddress() {
        return Address;
    }

    public String getPhone() {
        return phone;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDistance() {
        distance = 0;


        return  calculateDistance();
    }

    private double calculateDistance(){
        double lat1 = MainActivity.sUserLatitude;
        double lon1 = MainActivity.sUserLongitude;
        double dLat= degToRadian(latitude-lat1);
        double dLon= degToRadian(longitude-lon1);
        double a = (Math.sin(dLat/2)*Math.sin(dLat/2))+(Math.cos(degToRadian(lat1))*Math.cos(degToRadian(latitude))
        *(Math.sin(dLon/2)*Math.sin(dLon/2)));

        double c = 2*(Math.asin(Math.sqrt(a)));
        distance = mR *c;

        return distance;

    }

    private double degToRadian(double degree){
        return degree*(Math.PI/180);
    }


}
