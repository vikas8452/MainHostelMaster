package com.hostelmanager.hostelmasterr;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by sudha on 05-Apr-18.
 */
@IgnoreExtraProperties
class FCM_Device_Tokens {


    private String token;

    public FCM_Device_Tokens() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
