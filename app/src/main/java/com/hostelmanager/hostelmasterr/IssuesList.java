package com.hostelmanager.hostelmasterr;

import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hostelmanager.hostelmasterr.Model.HostelerInfo;
import com.hostelmanager.hostelmasterr.Model.SendRecieveIssues;

import java.util.ArrayList;

public class IssuesList extends AppCompatActivity {

    BottomSheetBehavior sheetBehavior;
    private MyItemRecyclerViewAdapter adapter;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser currentUser;
    private GridView gridView;
    private DatabaseReference databaseReference,databaseReferenc;
    private ArrayList<SendRecieveIssues> sendRecieveIssues=new ArrayList<SendRecieveIssues>();
    private String luid,room;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_item);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        databaseReferenc = FirebaseDatabase.getInstance().getReference().child("Students").child(currentUser.getPhoneNumber());

        databaseReferenc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HostelerInfo hostelerInfo = dataSnapshot.getValue(HostelerInfo.class);
                luid = hostelerInfo.getLuid();
                room = hostelerInfo.getRoomno();
                databaseReference = FirebaseDatabase.getInstance().getReference().child("Issues").child(luid).child(room);
                databaseReference.keepSynced(true);
                adapter=new MyItemRecyclerViewAdapter(getBaseContext(),retrieve(databaseReference));
                gridView.setAdapter((ListAdapter) adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setGravity(Gravity.BOTTOM);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.94),(int)(height*.60));

        firebaseAuth= FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser();
        gridView = findViewById(R.id.gridissue);


        //   helper=new FireBaseHelper(databaseReference);



    }

    private void fetchData(DataSnapshot dataSnapshot)
    {
        //sendRecieveIssues.clear();
        //Toast.makeText(this, dataSnapshot.getKey(), Toast.LENGTH_SHORT).show();
        //if(dataSnapshot.getKey().equals("105"))
          //  for (DataSnapshot ds : dataSnapshot.getChildren()) {
                Log.d("ds", "Hell");
                try {
                    SendRecieveIssues spacecraft = dataSnapshot.getValue(SendRecieveIssues.class);
                    sendRecieveIssues.add(spacecraft);
                    // Toast.makeText(getActivity(), buySellSubjects.get(0).getBookName() + "", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            //}

    }

    public ArrayList<SendRecieveIssues> retrieve(DatabaseReference db)
    {
        Log.d("sdsd","Entered in the listener");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("sdsd","Calling Fetvch data");
                fetchData(dataSnapshot);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot);
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

        return  sendRecieveIssues;
    }
}
