package com.example.amado.pharmacymap;

import android.app.AlarmManager;
import android.app.IntentService;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by Amado on 24/06/2015.
 */
public class updateService extends IntentService {
    private static final String TAG = "updateService";
    private static final int SERVICE_INTERVAL = 1000*15;


    public updateService(){
        super(TAG);
    }


    @Override
    public void onHandleIntent(Intent intent) {
       if(isOnline()){
           Log.d(TAG, "requested pharmacies from parse");
           ParseDataSource.downloadPharmaciesFromParse();

       }
    }



    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    public static void setServiceAlarm(Context context, boolean isOn){
        Intent i = new Intent(context, updateService.class);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);

        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        if(isOn){
            alarmManager.setRepeating(AlarmManager.RTC, System.currentTimeMillis(),SERVICE_INTERVAL, pi);
        }else{
            alarmManager.cancel(pi);
            pi.cancel();
        }


    }
}
