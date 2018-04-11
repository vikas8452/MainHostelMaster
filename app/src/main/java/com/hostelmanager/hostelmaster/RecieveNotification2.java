package com.hostelmanager.hostelmaster;

import android.content.Intent;
import android.util.Log;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

/**
 * Created by sudha on 30-Mar-18.
 */

public class RecieveNotification2 extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";

    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
        DatabaseReference fcmDatabaseRef = mDatabase.child("FCM_Device_Tokens").push();

        FCM_Device_Tokens obj = new FCM_Device_Tokens();
        obj.setToken(refreshedToken);
        fcmDatabaseRef.setValue(obj);
    }

}
