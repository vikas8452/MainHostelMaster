package com.hostelmanager.hostelmasterr;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.hostelmanager.hostelmasterr.Model.HostelerInfo;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyHostell extends Fragment {
    private TextView my_hostel_name, room;
    private String TAG;
    private Button mess_Button;
    private Button get_Token;
    private FirebaseAuth firebaseAuth;
    private String hname,roomno,mob;
    private DatabaseReference databaseReference;
    FirebaseDatabase database;
    private DatabaseReference myRef;


    public MyHostell() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_hostel);
        final View view = inflater.inflate(R.layout.fragment_my_hostell, container, false);

        my_hostel_name = view.findViewById(R.id.my_hostel_name);
        mess_Button = view.findViewById(R.id.mess_Button);
        get_Token = view.findViewById(R.id.get_Token);
        room = view.findViewById(R.id.assigned);


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        mob = currentUser.getPhoneNumber().toString();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Students").child(mob);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HostelerInfo hostelerInfo = dataSnapshot.getValue(HostelerInfo.class);
                hname = hostelerInfo.getHostel();
                roomno = hostelerInfo.getRoomno();
                room.setText(roomno);
                my_hostel_name.setText(hname);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild("7388796555")) {
                    // run some code
                    Toast.makeText(getContext(),"You are not yet registered",Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseMessaging.getInstance().subscribeToTopic("News");
                    FirebaseMessaging.getInstance().subscribeToTopic("Movies");
                    myRef.child("7388796555").child("token").setValue(FirebaseInstanceId.getInstance().getToken());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        /*myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = (String)dataSnapshot.getValue(String.class);
                my_hostel_name.setText(value);

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });*/
        get_Token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), QRCodeScanner.class));


            }
        });
        mess_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), rating_bar.class));
            }
        });
        return view;

    }
}
