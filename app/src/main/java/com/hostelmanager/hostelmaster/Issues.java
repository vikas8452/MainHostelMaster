package com.hostelmanager.hostelmaster;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.BackgroundColorSpan;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.StringTokenizer;

public class Issues extends AppCompatActivity {
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private FirebaseAuth firebaseAuth;
    private TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        getWindow().setGravity(Gravity.BOTTOM);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.94),(int)(height*.60));


        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        tv1 = findViewById(R.id.issues);
        final String userId = firebaseUser.getUid();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        myRef = FirebaseDatabase.getInstance().getReference().child(userId).child("issues");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                UserInfo userInfo = new UserInfo();
                userInfo.setIssuetype(ds.getValue(UserInfo.class).getIssuetype());
                userInfo.setDesc(ds.getValue(UserInfo.class).getDesc());
                tv1 = findViewById(R.id.issues);
                tv1.setText(" ");
                String s1 = userInfo.getIssuetype();
                String s2 = userInfo.getDesc();
                StringTokenizer s3 = new StringTokenizer(s1,",");
                StringTokenizer s4 = new StringTokenizer(s2,",");
                while(s3.hasMoreTokens()){
                    tv1.setText(tv1.getText()  + s3.nextToken() + " : " + s4.nextToken() +"." + "\n\n");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);

            }

        });
    }

    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
    }
    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_in_down, R.anim.slide_out_down);
        finish();
    }
}
