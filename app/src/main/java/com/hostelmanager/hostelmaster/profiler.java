package com.hostelmanager.hostelmaster;


import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;


/**
 * A simple {@link Fragment} subclass.
 */
public class profiler extends Fragment {
    private TextView tv1,tv2;
    private TextView tv3,tv4;
    private ImageView imageView , editpen;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private Button update;


    public profiler() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_profiler, container, false);
        tv1  = view.findViewById(R.id.profileUserName);
        tv2 = view.findViewById(R.id.profileUserMobile);
        update = view.findViewById(R.id.profilerUpdate);
        editpen = view.findViewById(R.id.profilerEditpen);

        tv1.setFocusable(false);
        tv1.setFocusableInTouchMode(false);
        tv1.setCursorVisible(false);
        tv2.setFocusable(false);
        tv2.setFocusableInTouchMode(false);
        tv2.setCursorVisible(false);
        update.setVisibility(View.GONE);



        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser==null){
            startActivity(new Intent(getActivity(),SignInerActivity.class));
            return null;
        }

        final String userId = firebaseUser.getUid();
        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef = FirebaseDatabase.getInstance().getReference();

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {


                UserInfo userInfo = new UserInfo();
                userInfo.setFname(ds.child(userId).getValue(UserInfo.class).getFname());
                userInfo.setMob(ds.child(userId).getValue(UserInfo.class).getMob());

                tv1.setText(userInfo.getFname());
                tv2.setText(userInfo.getMob());

                imageView = view.findViewById(R.id.profilepic);

                FirebaseUser user = firebaseAuth.getCurrentUser();
                StorageReference storageReference2= mStorageRef.child("profile").child(user.getUid());
                try{
                storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getContext()).load(uri).into(imageView);
                    }
                });}
                catch(Exception e){
                    Toast.makeText(getActivity(),""+e,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        tv3=view.findViewById(R.id.profileEmailid);
        tv3.setText(firebaseUser.getEmail());

        tv4=view.findViewById(R.id.profileLogouttv);
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity() , SignInerActivity.class));
                firebaseAuth.signOut();
            }
        });

        editpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv2.setFocusable(true);
                tv1.setFocusableInTouchMode(true);
                tv1.setCursorVisible(true);
                tv1.setFocusable(true);
                tv2.setFocusableInTouchMode(true);
                tv2.setCursorVisible(true);
                update.setVisibility(View.VISIBLE);
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialo= new ProgressDialog(getContext());
                progressDialo.setTitle("Please Wait...");
                progressDialo.show();
                progressDialo.setCancelable(false);
                String fname = tv1.getText().toString().trim();
                String mob = tv2.getText().toString().trim();

                DatabaseReference stf1 = myRef.child(userId).child("fname");
                stf1.setValue(fname);

                DatabaseReference stf2 = myRef.child(userId).child("mob");
                stf2.setValue(mob);

                progressDialo.dismiss();

            }
        });
        return view;
    }

}
