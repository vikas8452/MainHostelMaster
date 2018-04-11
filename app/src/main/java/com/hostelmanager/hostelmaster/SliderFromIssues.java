package com.hostelmanager.hostelmaster;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SlidingDrawer;
import android.widget.Toast;

public class SliderFromIssues extends AppCompatActivity {

    SlidingDrawer slidingdrawer;
    Button SlidingButton;
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // slidingdrawer = (SlidingDrawer)findViewById(R.id.slidingDrawer1);
        //SlidingButton = (Button)findViewById(R.id.handle);

        slidingdrawer.setOnDrawerOpenListener(new SlidingDrawer.OnDrawerOpenListener() {

            @Override
            public void onDrawerOpened() {

                Toast.makeText(SliderFromIssues.this, "Sliding drawer open", Toast.LENGTH_LONG).show();
            }
        });

        slidingdrawer.setOnDrawerCloseListener(new SlidingDrawer.OnDrawerCloseListener() {
            @Override
            public void onDrawerClosed() {
                Toast.makeText(SliderFromIssues.this, "Sliding drawer close", Toast.LENGTH_LONG).show();

            }

        });

    }
}
