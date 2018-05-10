package com.hostelmanager.hostelmasterr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
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
import com.hostelmanager.hostelmasterr.Model.ConfStatus;
import com.hostelmanager.hostelmasterr.Model.GetHostelByUID;
import com.hostelmanager.hostelmasterr.Model.HostelerInfo;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HostelSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private DatabaseReference myRef;
    private FirebaseAuth firebaseAuth;
    private TextView tv1,tv2,tv3,tv4,tv5,tv6;
    private Button btn1,btn2;
    private ArrayAdapter<CharSequence> adapter;
    private Spinner sp1,sp2;
    private String s2,s4;
    private ScrollView sv;
    private LinearLayout ll;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hostel_sign_up);

       /* DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.94),(int)(height*.60));*/
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        ll = findViewById(R.id.lluid);
        ll.setVisibility(View.VISIBLE);

        sv = findViewById(R.id.svlmn);
        sv.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progressBar);

        tv1 = findViewById(R.id.hsuskip);
        tv2 = findViewById(R.id.hsuname);
        tv3 = findViewById(R.id.hsumobile);
        tv3.setText(firebaseUser.getPhoneNumber());

        tv4 = findViewById(R.id.hsuuniquecode);
        tv5 = findViewById(R.id.hsuhostel);
        tv6 = findViewById(R.id.hsuroom);

        btn1 = findViewById(R.id.hsuuniqueidbtn);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String str1 = tv4.getText().toString();

                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                        .child("HostelUID");

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot ds:dataSnapshot.getChildren()) {

                            GetHostelByUID getHostelByUID = ds.getValue(GetHostelByUID.class);

                            if (getHostelByUID.getUid().equals(str1)) {
                                Toast.makeText(HostelSignUp.this, "Success", Toast.LENGTH_SHORT).show();

                                ll.setVisibility(View.GONE);
                                tv5.setText(getHostelByUID.getHname());
                                sv.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

               // Toast.makeText(HostelSignUp.this,databaseReference+"",Toast.LENGTH_SHORT).show();
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HostelSignUp.this,MainActivity.class));
                finish();
            }
        });

        sp2 = findViewById(R.id.hsucollege);
        sp2.setOnItemSelectedListener(this);
        adapter = ArrayAdapter.createFromResource(this, R.array.Colleges, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        sp2.setAdapter(adapter);

        btn2 = findViewById(R.id.hsuregister);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str1=tv2.getText().toString().trim();
                String str2=tv3.getText().toString().trim();
                String str3=tv5.getText().toString().trim();
                String str4=s2.trim();
                String str5=tv6.getText().toString().trim();

                if(str1.equals("")) {
                    Toast.makeText(HostelSignUp.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str4.equals("Choose College")) {
                    Toast.makeText(HostelSignUp.this, "Please select College", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(str5.equals("")) {
                    Toast.makeText(HostelSignUp.this, "Please enter name", Toast.LENGTH_SHORT).show();
                    return;
                }
                register(str1,str2,str3,str4,str5);
                progressBar.setVisibility(View.VISIBLE);

            }
        });



    }

    private void register(String str1, String str2, String str3, String str4, String str5) {
        myRef = FirebaseDatabase.getInstance().getReference().child("Students").child(str2);
        HostelerInfo hostelerInfo = new HostelerInfo();
        hostelerInfo.setName(str1);
        hostelerInfo.setMobile(str2);
        hostelerInfo.setHostel(str3);
        hostelerInfo.setCollege(str4);
        hostelerInfo.setRoomno(str5);
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM");
        String mon = sdf.format(Calendar.getInstance().getTime());
        int year = Calendar.getInstance().get(Calendar.YEAR);
        hostelerInfo.setYear(mon+" "+year);
        myRef.setValue(hostelerInfo);
        progressBar.setVisibility(View.GONE);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("Hostels").child(str3).child(str5);
        String key = databaseReference.push().getKey();
        ConfStatus confStatus = new ConfStatus();
        confStatus.setMobile(str2);
        confStatus.setName(str1);
        confStatus.setStat("1");
        databaseReference.child(str2).setValue(confStatus);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("Students");
        mDatabase.child(str2).child("token").setValue(refreshedToken);
        final AlertDialog.Builder alertbox = new AlertDialog.Builder(this);
        alertbox.setMessage("Thank you for choosing "+str3.toUpperCase());
        alertbox.setTitle("Thank you");

        alertbox.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0,
                                        int arg1) {

                        startActivity(new Intent(HostelSignUp.this,MainActivity.class));
                        finish();

                    }
                });
        alertbox.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long id) {

        switch(adapterView.getId())
        {
            case R.id.hsucollege:
                // On selecting a spinner item
                s2 = adapterView.getItemAtPosition(i).toString();
                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + s2, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
