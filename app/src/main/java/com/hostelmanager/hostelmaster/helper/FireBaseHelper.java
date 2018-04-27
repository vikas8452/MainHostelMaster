package com.hostelmanager.hostelmaster.helper;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.hostelmanager.hostelmaster.BuySellSubject;

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
                db.child("BooksSellAndBuy").child(currentUser.getPhoneNumber()).child("MySellBooks").push().setValue(buySellSubject);
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

    private void fetchDataMySellBook(DataSnapshot dataSnapshot)
    {
        buySellSubjects.clear();

        for(DataSnapshot ds:dataSnapshot.getChildren())
        {
            BuySellSubject buySellSubject=ds.getValue(BuySellSubject.class);
            buySellSubjects.add(buySellSubject);
        }
    }

    public ArrayList<BuySellSubject> getBuySellSubjects() {

        currentUser=firebaseAuth.getCurrentUser();
        db.child("BooksSellAndBuy").child(currentUser.getPhoneNumber()).child("MySellBooks").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    fetchDataMySellBook(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                    fetchDataMySellBook(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return buySellSubjects;
    }
}
