package com.hostelmanager.hostelmasterr;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 3/6/2018.
 */

public class GalleryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener  {
    private static final String TAG = "GalleryActivity";
    private RecyclerView servicesRecyclerview;
    private RecyclerView hostelImageRecyclerView;
    private CircleImageView circleImage;
    private DatabaseReference databaseReference;
    LinearLayout layoutBottomSheet;
    BottomSheetBehavior sheetBehavior;
    String item1="Select Type";
    String item2="Select Facility";



    TextView spinner_error;
    private ArrayList<String> iconsList=new ArrayList<>();
    private ArrayList<String> hostelImageList=new ArrayList<>();
  //  private ArrayList<Integer> dataList=new ArrayList
     private ArrayList<String> iconsName=new ArrayList<>();

@BindView(R.id.showFees)
TextView showFees;
     @BindView(R.id.showButton)
    Button showButton;
     @BindView(R.id.imageButton4)
    ImageButton imgButton;

    FirebaseDatabase database;
    private DatabaseReference myRef,imageRef;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        ButterKnife.bind(this);
        databaseReference= FirebaseDatabase.getInstance().getReference();
        imageRef= FirebaseDatabase.getInstance().getReference("HostelImage").child("Royal Paradise");

        circleImage=findViewById(R.id.circleImage);
        servicesRecyclerview=findViewById(R.id.servicesRecyclerView);
        hostelImageRecyclerView=findViewById(R.id.hostelImageRecyclerView);
      //  servicesRecyclerview=findViewById(R.id.hostel);
        servicesRecyclerview.setHasFixedSize(true);
        hostelImageRecyclerView.setHasFixedSize(true);
        servicesRecyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayout.HORIZONTAL,false));
        hostelImageRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL,false));

        Log.d(TAG, "onCreate: started");

        layoutBottomSheet=findViewById(R.id.bottom_sheet);

        gettingIncomingIntent();
setHostelImageList();

        // database setup
        database = FirebaseDatabase.getInstance();


        //Setup Spinner

        final Spinner spinner1 =  findViewById(R.id.spinner5);
        final Spinner spinner2 =  findViewById(R.id.spinner6);

        // Spinner click listener
        spinner1.setOnItemSelectedListener(this);
        spinner2.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> department = new ArrayList<String>();
        department.add("Select Type");
        department.add("2 Seater");
        department.add("3 Seater");

        final List<String> semester = new ArrayList<String>();
        semester.add("Select Facility");
        semester.add("A-C");
        semester.add("Cooler");


        // Creating adapter for spinner
        final ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, department);
        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, semester);

        // Drop down layout style - list view with radio button
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner1.setAdapter(dataAdapter1);
        spinner2.setAdapter(dataAdapter2);



     sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);



             imgButton.setVisibility(View.INVISIBLE);
      sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
           @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        imgButton.setVisibility(View.VISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //btnBottomSheet.setText("Close Sheet");\
                        imgButton.setVisibility(View.INVISIBLE);
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                      //  btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        Toast.makeText(GalleryActivity.this, "Drag below", Toast.LENGTH_SHORT).show();
                       // sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        imgButton.setVisibility(View.INVISIBLE);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        imgButton.setVisibility(View.INVISIBLE);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
      //@OnClick(R.id.submitBook)



        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(!item1.equals("Select Type")){

                   if(!item2.equals("Select Facility"))
                   {
                       myRef = database.getReference("HostelFees").child("Royal Paradise").child(item1).child(item2);
                       myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {
                               String fees=dataSnapshot.getValue(String.class);
                               showFees.setText("₹ "+fees+" /-");
                           }

                           @Override
                           public void onCancelled(DatabaseError databaseError) {

                           }
                       });
                   }
                   else
                   {
                       showFees.setText("₹  - - - -");
                       spinner_error=(TextView) spinner2.getSelectedView();
                       spinner_error.setTextColor(Color.RED);
                       spinner_error.setTypeface(null, Typeface.BOLD);
                       spinner_error.setText("Select Facility");
                   }

               }
               else{
                   showFees.setText("₹  - - - -");
                    spinner_error=(TextView) spinner1.getSelectedView();
                    spinner_error.setTextColor(Color.RED);
                    spinner_error.setTypeface(null, Typeface.BOLD);
                    spinner_error.setText("Select Type");

                }
            }
        });
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
    //    ImageView hostelImage=findViewById(R.id.hostelImage);
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
       // Picasso.with(this).load(image_url).into(hostelImage);
        rating_gallery.setText(rating_Description);
        rating_Count_gallery.setText(rating_Count);


    }

    private void setHostelImageList()
    {

               imageRef.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {

                       for(DataSnapshot snapshot: dataSnapshot.getChildren())
                       {
                           String hostelPic=snapshot.getValue(String.class);
                           Toast.makeText(GalleryActivity.this, hostelPic, Toast.LENGTH_SHORT).show();
                           hostelImageList.add(hostelPic);


                       }

                       Hostel_image_In_explore_recycler_view hostel_image_in_explore_recycler_view=new Hostel_image_In_explore_recycler_view(GalleryActivity.this,hostelImageList);
                        hostelImageRecyclerView.setAdapter(hostel_image_in_explore_recycler_view);
                        hostel_image_in_explore_recycler_view.notifyDataSetChanged();
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        switch(adapterView.getId())
        {
            case R.id.spinner5:
                // On selecting a spinner item
                item1 = adapterView.getItemAtPosition(i).toString();
                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + item1, Toast.LENGTH_LONG).show();
                break;
            case R.id.spinner6:
                // On selecting a spinner item
                item2 = adapterView.getItemAtPosition(i).toString();
                // Showing selected spinner item
                Toast.makeText(adapterView.getContext(), "Selected: " + item2, Toast.LENGTH_LONG).show();
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
