package com.hostelmanager.hostelmaster;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.StringTokenizer;

@SuppressWarnings("deprecation")
public class MyIssues extends AppCompatActivity implements AdapterView.OnItemSelectedListener,SimpleGestureFilter.SimpleGestureListener {

    private SimpleGestureFilter detector;
    private static int MY_DLG = 1;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private TextView tv1,tv2;
    private Button submit,issueWifi,issueHousekeeping,button;
    private EditText description;
    String issuetype;
    String descr;
    private int flag = 0;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_issues);
        detector = new SimpleGestureFilter(this,this);

        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser==null){
            startActivity(new Intent(this,SignInerActivity.class));
            finish();
        }else {

            tv2 = findViewById(R.id.swipeup);

            databaseReference = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());

            description = findViewById(R.id.issuesDescription);

            button = findViewById(R.id.button2);

            spinner = (Spinner) findViewById(R.id.issuesissuesp);
            adapter = ArrayAdapter.createFromResource(this, R.array.Issues, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

            issueWifi = findViewById(R.id.issuesWifi);
            issueHousekeeping = findViewById(R.id.issueHousekeeping);


            submit = findViewById(R.id.issueSubmit);
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    descr = description.getText().toString().replace("\n", "   ").trim();

                    if (issuetype.equals("Choose Problem")) {
                        Toast.makeText(MyIssues.this, "Please Select your problem", Toast.LENGTH_LONG).show();
                        return;
                    }
                    if (descr.equals("")) {
                        Toast.makeText(MyIssues.this, "Please describe your problem", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    SubmitIssueConfirm submitIssueConfirm = new SubmitIssueConfirm(issuetype, descr);
                    submitIssueConfirm.show(getSupportFragmentManager(), "Are you sure ??");

                    description.setText("");

                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MyIssues.this,Issues.class));
                    overridePendingTransition( R.anim.slide_in_up, R.anim.slide_out_up );
                }
            });

        }
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        issuetype = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    public void onBackPressed() {
        /*slidingdrawer = findViewById(R.id.slidingDrawer1);
        if (flag==1) {
            flag=0;
            slidingdrawer.close();
            return ;
        } else if (flag==0) {
            super.onBackPressed();
            return;
        }*/
        finishAndRemoveTask();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onSwipe(int direction) {
        String str = "";
        switch (direction) {

            case SimpleGestureFilter.SWIPE_RIGHT : str = "Swipe Right";
                break;
            case SimpleGestureFilter.SWIPE_LEFT :  str = "Swipe Left";
                break;
            case SimpleGestureFilter.SWIPE_DOWN :  str = "Swipe Down";
                break;
            case SimpleGestureFilter.SWIPE_UP :    str = "Swipe Up";
                if(tv2.getVisibility() == View.VISIBLE) {
                    //Toast.makeText(this, "ddddd", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MyIssues.this, Issues.class));
                    overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                }
                break;

        }
        //Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onDoubleTap() {
        //Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
    }
}
