package com.example.amado.pharmacymap;

import android.content.Intent;
import android.location.Location;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks
, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapClickListener{

    private static final String TAG = "MainActivity";

    GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    public static double sUserLongitude;
    public static double sUserLatitude;
    private ArrayList<Pharmacy> mTest;
    private ArrayList<Pharmacy> mPharmacies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if(UserDataSource.getCurrentUser() == null){
            Intent i = new Intent(this, SignInActivity.class);
            startActivity(i);
        }


        MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        addGoogleAPIClient();





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.show_list:
                Intent i = new Intent(MainActivity.this, PharmacyListActivity.class);
                startActivity(i);
                return true;
            default:
                return false;
        }
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapClickListener(this);
        mTest = (ArrayList)ParseDataSource.getPharmaciesFromParse();
        if(mTest.isEmpty()){
            Log.d(TAG, "is empty");
        }else{
            Log.d(TAG, mTest.get(0).getName());
        }
        mPharmacies= PharmacyLocations.getPharmacies();
        mMap.setInfoWindowAdapter(new MarkerAdapter(getLayoutInflater()));
        for(Pharmacy pharmacy: mPharmacies){
            addMarker(pharmacy);
        }
    }


    @Override
    public void onMapClick(LatLng latLng) {

    }

    private void addGoogleAPIClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mGoogleApiClient.connect();

    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "conected");
        Location location = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        sUserLatitude = location.getLatitude();
        sUserLongitude = location.getLongitude();



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private void addMarker(Pharmacy pharmacy) {
        Marker marker =mMap.addMarker(new MarkerOptions()
                .position(new LatLng(pharmacy.latitude, pharmacy.longitude)));


    }

}
