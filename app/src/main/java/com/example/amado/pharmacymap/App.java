package com.example.amado.pharmacymap;

import android.app.Application;

import com.orm.SugarApp;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;

/**
 * Created by Amado on 22/06/2015.
 */
public class App extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this, "eGilveRnaekhSQKR2VR71pm4oXdAeuNNUF6cvEsW", "N6c3LeQvulvaYmeI9IF74rvfHWgz88S92wQIMcED");
        ParseFacebookUtils.initialize(this);
    }
}
