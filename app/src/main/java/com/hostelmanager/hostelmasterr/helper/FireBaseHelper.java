package com.hostelmanager.hostelmasterr.helper;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.hostelmanager.hostelmasterr.Model.BuySellSubject;

import java.util.ArrayList;

public class FireBaseHelper {

    private FirebaseAuth firebaseAuth;
    DatabaseReference db;
    Boolean saved=null;
    FirebaseUser currentUser;

    ArrayList<BuySellSubject> buySellSubjects=new ArrayList<BuySellSubject>();

    public FireBaseHelper(DatabaseReference db)
    {
        this.db=db;
    }

    public Boolean saveMySellBook(BuySellSubject buySellSubject)
    {
        Log.d("'sddsds","Entered in firebase helper");
        firebaseAuth= FirebaseAuth.getInstance();
        currentUser= firebaseAuth.getCurrentUser();

        if(buySellSubject==null)
        {
            saved=false;
        }
        else
        {
            try
            {
                db.child(currentUser.getPhoneNumber()).push().setValue(buySellSubject);
                saved=true;

            }
            catch(DatabaseException ex)
            {
                ex.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }


}
