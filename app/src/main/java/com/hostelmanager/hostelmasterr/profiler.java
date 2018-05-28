package com.hostelmanager.hostelmasterr;


import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.hostelmanager.hostelmasterr.Model.ConfStatus;
import com.hostelmanager.hostelmasterr.Model.HostelerInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class profiler extends Fragment {

    private String name,hostel,roomno,college,year;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6,chhos;
    private  DatabaseReference databaseReference,databaseReferenc;
    private LinearLayout ll,ll2,ll3;


    public profiler() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_profiler, container, false);

        tv1 = view.findViewById(R.id.sdname);
        tv2 = view.findViewById(R.id.sdmobile);
        tv3 = view.findViewById(R.id.sdcollege);
        tv4 = view.findViewById(R.id.sdhostel);
        tv5 = view.findViewById(R.id.sdroom);
        tv6 = view.findViewById(R.id.sdyear);
        chhos = view.findViewById(R.id.changehos);
        ll = view.findViewById(R.id.statusshow);
        ll2 = view.findViewById(R.id.chhos);
        ll3 = view.findViewById(R.id.statusshowe);

        final RelativeLayout rl = view.findViewById(R.id.loading);
        rl.setVisibility(View.VISIBLE);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser fb = firebaseAuth.getCurrentUser();
        final String mobile = fb.getPhoneNumber();

        databaseReferenc = FirebaseDatabase.getInstance().getReference().child("Students");

        databaseReferenc.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild(mobile)) {
                    // run some code
                    Log.d("iii","iooo");
                    Toast.makeText(getContext(),"You are not yet registered",Toast.LENGTH_SHORT).show();
                    Explore explore=new Explore();
                    android.support.v4.app.FragmentManager manager=getFragmentManager();
                    manager.beginTransaction().replace(R.id.relativefrag,explore).commit();
                    startActivity(new Intent(getContext(),HostelSignUp.class));
                }
                else{

                    Log.d("iii","iiiiiiii");
                    databaseReference = FirebaseDatabase.getInstance().getReference()
                            .child("Students").child(mobile);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            HostelerInfo hostelerInfo = dataSnapshot.getValue(HostelerInfo.class);
                            name = hostelerInfo.getName();
                            hostel = hostelerInfo.getHostel();
                            roomno = hostelerInfo.getRoomno();
                            college = hostelerInfo.getCollege();
                            year = hostelerInfo.getYear();
                            String uid = hostelerInfo.getLuid();

                            tv1.setText(name);
                            tv2.setText(mobile);
                            tv3.setText(college);
                            tv4.setText(hostel);
                            tv5.setText(roomno);
                            tv6.setText(year);
                            DatabaseReference ddb = FirebaseDatabase.getInstance().getReference().child("Hostels").child(uid)
                                    .child(roomno);
                            ddb.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    for(DataSnapshot dds:dataSnapshot.getChildren()){
                                        ConfStatus confStatus = dds.getValue(ConfStatus.class);
                                        if(confStatus.getMobile().equals(mobile)){
                                            if(confStatus.getStat().equals("1")){
                                                ll.setVisibility(View.VISIBLE);
                                            }
                                            if(confStatus.getStat().equals("3")){
                                                ll2.setVisibility(View.VISIBLE);
                                                ll3.setVisibility(View.VISIBLE);
                                            }

                                            break;
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            rl.setVisibility(View.GONE);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        chhos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),HostelSignUp.class));
            }
        });


        return view;
    }
}
