package com.hostelmanager.hostelmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class propertysignup extends AppCompatActivity {
    private EditText property_name,total_bed,total_bedroom,address_1,address_2,state,country,city,post_code,contact_number,contact_number_2,manager_email,booking_email,contact_name;
  Button sign_up;
  String cont_name,prop_name,tot_bed,tot_bedroom,add_1,add_2,state_string,country_string,city_string,post,cont_number,cont_number_2,manag_email,book_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_propertysignup);


        property_name=findViewById(R.id.property_name);
        total_bed=findViewById(R.id.total_bed);
        total_bedroom=findViewById(R.id.total_bedroom);
        address_1=findViewById(R.id.adress_1);
        address_2=findViewById(R.id.address_2);
        state=findViewById(R.id.state);
        country=findViewById(R.id.country);
        city=findViewById(R.id.city);
        post_code=findViewById(R.id.post_code);
        contact_number=findViewById(R.id.contact_number);
        contact_number_2=findViewById(R.id.contact_number2);
        contact_name=findViewById(R.id.contact_name);
        manager_email=findViewById(R.id.manager_email);
        booking_email=findViewById(R.id.booking_email);

        //getting reference from firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference myRef = database.getReference("work with us users");
        //Button listener
        sign_up=findViewById(R.id.sing_up_button);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();

                final Work_with_us_users  wwuu = new Work_with_us_users("Property name : "+property_name.getText().toString(),"Total bedroom : "+ tot_bedroom,"Total bed : "+tot_bed,"Address1 : "+ add_1,"Landmark : "+ add_2,"State : "+ state_string,"Post-code : "+ post,"Country : " +country_string,"City : "+city_string,"Contact name : "+cont_name,"Manager-email : "+ manag_email,"Booking-email : "+ book_email,"Contact no 1 : "+ cont_number,"Contact number 2 : "+ cont_number_2);


                myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       if(dataSnapshot.child(wwuu.getProp_name()).exists()){
                           Toast.makeText(propertysignup.this,"The Phone number already exists",Toast.LENGTH_SHORT).show();
                       }
                       else {
                           myRef.child(wwuu.getCont_number()).setValue(wwuu);
                       }
                   }

                   @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });

            }
        });


    }

    public void register(){
        prop_name=property_name.getText().toString().trim();
        tot_bedroom=total_bedroom.getText().toString().trim();
        tot_bed=total_bed.getText().toString().trim();
        state_string=state.getText().toString().trim();
        add_1=address_1.getText().toString().trim();
        add_2=address_2.getText().toString().trim();
        post=post_code.getText().toString().trim();
        country_string=country.getText().toString().trim();
        city_string=city.getText().toString().trim();
        cont_name=contact_name.getText().toString().trim();
        manag_email=manager_email.getText().toString().trim();
        book_email=booking_email.getText().toString().trim();
        cont_number=contact_number.getText().toString().trim();
        cont_number_2=contact_number_2.getText().toString().trim();

       if(!validation())
       {
            Toast.makeText(this,"Sign up failed",Toast.LENGTH_SHORT).show();
       }
        else
       {
           Intent myintent=new Intent(propertysignup.this,review_edit.class);
           myintent.putExtra("prop_name",prop_name);
           myintent.putExtra("cont_name",cont_name);
           myintent.putExtra("tot_bed",tot_bed);
           myintent.putExtra("tot_bedroom",tot_bedroom);
           myintent.putExtra("add_1",add_1);
           myintent.putExtra("add_2" ,add_2);
           myintent.putExtra("state_string",state_string);
           myintent.putExtra("country_string",country_string);
           myintent.putExtra("city_string",city_string);
           myintent.putExtra("cont_number",cont_number);
           myintent.putExtra("cont_number_2",cont_number_2);
           myintent.putExtra("manag_email",manag_email);
           myintent.putExtra("book_email",book_email);
           startActivity(myintent);

       }
    }
    public boolean validation(){
        boolean valid=true;
      if(cont_name.isEmpty()||cont_name.length()>32)
        {
            contact_name.setError("Please Enter valid name");
            valid=false;
        }
        if(prop_name.isEmpty()){
            property_name.setError("Please enter Property Name");
            valid=false;
        }
        if(!(tot_bedroom.isEmpty()&&(tot_bed.isEmpty())))
        if(Integer.valueOf(tot_bedroom)>Integer.valueOf(tot_bed)){
            total_bedroom.setError("Total bedroom should not be greater than total bed");
            valid=false;
        }
        if(cont_number.isEmpty()){
            contact_number.setError("Please enter Contact Number");
            valid=false;
        }
        if(country_string.isEmpty())
        {
            country.setError("Please enter Country name ");
            valid=false;
        }
        if(city_string.isEmpty())
        {
            city.setError("Please enter City");
        }
        if(add_1.isEmpty())
        {
            address_1.setError("Please enter Address");
            valid=false;

        }
        if(book_email.equals(""))
        {
         booking_email.setError("Please enter booking e-mail");
         valid=false;
        }


        return valid;
    }


}
