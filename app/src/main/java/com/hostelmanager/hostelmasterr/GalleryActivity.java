package com.hostelmanager.hostelmasterr;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 3/6/2018.
 */

public class GalleryActivity extends AppCompatActivity {
    private static final String TAG = "GalleryActivity";
    private RecyclerView servicesRecyclerview;
    private CircleImageView circleImage;
    private DatabaseReference databaseReference;
    private ArrayList<String> iconsList=new ArrayList<>();
  //  private ArrayList<Integer> dataList=new ArrayList<>();

     private ArrayList<String> iconsName=new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        databaseReference= FirebaseDatabase.getInstance().getReference();
       // dataList.add(R.drawable.complete03);
        circleImage=findViewById(R.id.circleImage);
        servicesRecyclerview=findViewById(R.id.servicesRecyclerView);
       // servicesRecyclerview.setHasFixedSize(true);
        servicesRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false));
       // servicesRecyclerview.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));
        Log.d(TAG, "onCreate: started");


        gettingIncomingIntent();

    }
    private void gettingIncomingIntent(){
        Log.d(TAG,"getIncomingIntent: Checking for Incoming Intent");

        if(getIntent().hasExtra("image_url")&&getIntent().hasExtra("hostel_name"))
        {
            Log.d(TAG,"getIncomuing Intent :Intent is coming.");
            String image_url=getIntent().getStringExtra("image_url");
            String hostel_name=getIntent().getStringExtra("hostel_name");
            String rating_Description=getIntent().getStringExtra("rating_Description");
            String rating_Count=getIntent().getStringExtra("rating_Count");
            setImage(image_url,hostel_name,rating_Description,rating_Count);
         //   iconsList.add();
        }
    }

    private void setImage(String image_url, final String hostel_name, String rating_Description, String rating_Count){
        Log.d(TAG,"setting image to widget.");


      //  dataList.add(R.drawable.complete03);
        TextView hostelDesc=findViewById(R.id.hostelDesc);
        ImageView hostelImage=findViewById(R.id.hostelImage);
        TextView rating_gallery=findViewById(R.id.rating_gallery);
        TextView rating_Count_gallery=findViewById(R.id.rating_Count_gallery);


     //  circleImage.setImageResource(R.drawable.complete03);
        databaseReference.child("Hostel").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Toast.makeText(getApplicationContext(), "Entered in on data change", Toast.LENGTH_SHORT).show();
             //   hostelNameList.clear();
              //  hostelPicList.clear();
            //    ratingList.clear();
              //  rating_Count.clear();
              //  recyclerview.removeAllViews();
                int counter=0;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    //   Toast.makeText(getApplicationContext(), "Entered in for", Toast.LENGTH_SHORT).show();
                    String hostelName=snapshot.child("name").getValue(String.class);
                 //   String iconLink=snapshot.child("iconLink").getValue(String.class);
                    //String iconName=snapshot.child("iconName").getValue(String.class);
                   // String ratingCount=snapshot.child("ratingCount").getValue(String.class)+" Rating";

                   // circleImage.setImageResource(R.drawable.complete03);
                    if(hostelName.equals(hostel_name)){
                        int iconCounter=Integer.parseInt(snapshot.child("iconCounter").getValue(String.class));
                        //  Toast.makeText(getApplicationContext(), "Entered in if", Toast.LENGTH_SHORT).show();
                        for(int i=1;i<=iconCounter;i++)
                        {
                            String iconLink=snapshot.child("iconLink"+i).getValue(String.class);
                            String iconName=snapshot.child("iconName"+i).getValue(String.class);
                            iconsList.add(iconLink);
                            iconsName.add(iconName);
                          //  Toast.makeText(getApplicationContext(), iconLink, Toast.LENGTH_SHORT).show();
                        }

                        counter++;

                }
                    if(counter==15)
                        break;
                    break;
                }
               ServiceRecyclerViewAdapter serviceRecyclerViewAdapter=new ServiceRecyclerViewAdapter(GalleryActivity.this,iconsList,iconsName);
                servicesRecyclerview.setAdapter(serviceRecyclerViewAdapter);
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        hostelDesc.setText(hostel_name);
        Picasso.with(this).load(image_url).into(hostelImage);
        rating_gallery.setText(rating_Description);
        rating_Count_gallery.setText(rating_Count);


    }
}
