package com.hostelmanager.hostelmasterr;

import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by sudha on 30-Mar-18.
 */

public class RecieveNotification2 extends FirebaseInstanceIdService {

    private static final String TAG = "MyFirebaseIIDService";
FirebaseAuth firebaseAuth=FirebaseAuth.getInstance();
FirebaseUser currrentUser=firebaseAuth.getCurrentUser();
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("dcq","Entered in the Receice");
        //Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.

        //sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {

       DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Students");
        mDatabase.child(currrentUser.getPhoneNumber()).child("token").setValue(refreshedToken);

    }

}
