package com.example.amado.pharmacymap;

import com.parse.ParseUser;

/**
 * Created by Amado on 23/06/2015.
 */
public class UserDataSource {
    private static final String FIRST_NAME= "firstName";
    private static final String PICTURE_URL = "pictureURL";
    private static User sUserCurrent;

    public static User getCurrentUser(){
        if(sUserCurrent == null&& ParseUser.getCurrentUser()!= null){
            ParseUser user = ParseUser.getCurrentUser();
            sUserCurrent = new User();
            sUserCurrent.setFirstName(user.getString(FIRST_NAME));
            sUserCurrent.setPictureURL(user.getString(PICTURE_URL));
        }
        return sUserCurrent;
    }
}
