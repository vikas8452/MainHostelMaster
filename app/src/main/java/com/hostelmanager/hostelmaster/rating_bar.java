package com.hostelmanager.hostelmaster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class rating_bar extends AppCompatActivity {
     Button send;
     Button cancel;
     RatingBar ratingBar;
     float stars;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_bar);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.94),(int)(height*.47));

        FirebaseDatabase database= FirebaseDatabase.getInstance();
        final DatabaseReference myRef=database.getReference("Rating");
        ratingBar=findViewById(R.id.ratingBar);
        send=findViewById(R.id.send);
        cancel=findViewById(R.id.cancel);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(rating_bar.this, "stars :"+ v, Toast.LENGTH_SHORT).show();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stars=ratingBar.getRating();
                myRef.setValue(stars);
                Toast.makeText(rating_bar.this, "stars : "+ ratingBar.getRating(), Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(rating_bar.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
