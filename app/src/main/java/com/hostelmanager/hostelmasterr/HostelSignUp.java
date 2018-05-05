package com.hostelmanager.hostelmasterr;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class HostelSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private TextView tv1,tv3,tv5;
    private Button btn2;
    private Spinner sp1,sp2;
    private String s2,s4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_sign_up);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.94),(int)(height*.60));
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        tv1 = findViewById(R.id.username);

        tv3 = findViewById(R.id.userroom);
        tv5 = findViewById(R.id.skip);

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sp1 = findViewById(R.id.spinner1);
        sp2 = findViewById(R.id.spinner2);

        sp1.setOnItemSelectedListener(this);
        sp2.setOnItemSelectedListener(this);

        btn2 = findViewById(R.id.register);

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s1 = tv1.getText().toString();
                String s3 = tv3.getText().toString();
                if(s1.equals("")){
                    Toast.makeText(HostelSignUp.this,"please enter name",Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(s1.equals("")){
                    Toast.makeText(HostelSignUp.this,"please select college",Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(s1.equals("")){
                    Toast.makeText(HostelSignUp.this,"please enter alloted Room no.",Toast.LENGTH_SHORT).show();
                    return ;
                }
                else if(s1.equals("")){
                    Toast.makeText(HostelSignUp.this,"Please select your Hostel",Toast.LENGTH_SHORT).show();
                    return ;
                }

                register(s1,s2,s3,s4);
                finish();

            }
        });

    }

    private void register(String s1, String s2, String s3, String s4) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child(s4).child(s2).child("7388796555").child("name").setValue(s1);
        databaseReference.child(s4).child(s2).child("7388796555").child("roomno").setValue(s3);
        databaseReference.child(s4).child(s2).child("7388796555").child("roomno").setValue(s3);
        databaseReference.child(s4).child(s2).child("7388796555").child("Issues").child("topic").setValue("");
        databaseReference.child(s4).child(s2).child("7388796555").child("Issues").child("description").setValue("");
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {

        switch(adapterView.getId())
        {
            case R.id.spinner1:
                // On selecting a spinner item
                s2 = adapterView.getItemAtPosition(i).toString();
                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + s2, Toast.LENGTH_LONG).show();
                break;
            case R.id.spinner2:
                // On selecting a spinner item
                s4 = adapterView.getItemAtPosition(i).toString();
                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + s4, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
