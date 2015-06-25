package com.example.amado.pharmacymap;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.File;
import java.util.ArrayList;


public class MainActivity extends ActionBarActivity implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks
, GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapClickListener, ParseDataSource.Listener{

    private static final String TAG = "MainActivity";

    GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
    public static double sUserLongitude;
    public static double sUserLatitude;
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
        if(isOnline()){
            //if is online download the pharmacies and after the callback fillmarkers
            downloadInfoFromParse();
        }else{
            //if is offline try retrieving from database
            try {
                mPharmacies = PharmacyLocations.getPharmacies();
                fillMarkers(mPharmacies);
            }catch (Exception e){
                Log.d(TAG,"download and retrieving info from database failed");
                Toast.makeText(this, R.string.connection_toast, Toast.LENGTH_LONG)
                        .show();
            }
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

    private void fillMarkers(ArrayList<Pharmacy> pharmacies){
        mMap.setInfoWindowAdapter(new MarkerAdapter(getLayoutInflater()));
        for(Pharmacy pharmacy: pharmacies){
            addMarker(pharmacy);
        }
    }

    @Override
    public void onFetchedParsePharmacies(ArrayList<Pharmacy> pharmaciesFromParse) {
        updateDatabase(pharmaciesFromParse);
        PharmacyLocations.setPharmacies(pharmaciesFromParse);
        fillMarkers(pharmaciesFromParse);

    }

    private void updateDatabase(ArrayList<Pharmacy> pharmaciesFromParse) {
        String databaseName = getString(R.string.database_name);
        File database = this.getDatabasePath(databaseName);
        if(database.exists()){
            database.delete();
            DataSource.createDb(pharmaciesFromParse);
        }else{
            //if it doesn't exist create a new one
            DataSource.createDb(pharmaciesFromParse);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public void downloadInfoFromParse(){
        ParseDataSource dataSource = new ParseDataSource(this, this);
        dataSource.downloadPharmaciesFromParse();

    }
}
