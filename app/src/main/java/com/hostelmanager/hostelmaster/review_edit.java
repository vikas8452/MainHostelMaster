package com.hostelmanager.hostelmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class review_edit extends AppCompatActivity {
Button edit,submit;
    String cont_name,prop_name,tot_bed,tot_bedroom,add_1,add_2,state_string,country_string,city_string,post,cont_number,cont_number_2,manag_email,book_email;
private TextView property,total_bed,total_bedroom,address_1,address_2,state,country,city,post_code,contact_number,contact_number_2,manager_email,booking_email,contact_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_edit);


        edit=findViewById(R.id.edit);
        submit=findViewById(R.id.submit);
        property =findViewById(R.id.property);
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

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                onBackPressed();
            }
        });

        Intent intent=getIntent();
        prop_name=intent.getStringExtra("prop_name");
        tot_bed=intent.getStringExtra("tot_bed");
        tot_bedroom=intent.getStringExtra("tot_bedroom");
        add_1=intent.getStringExtra("add_1");
        add_2=intent.getStringExtra("add_2");
        state_string=intent.getStringExtra("state_string");
        country_string=intent.getStringExtra("country_string");
        city_string=intent.getStringExtra("city_string");
        post=intent.getStringExtra("post");
        cont_number=intent.getStringExtra("cont_number");
        cont_number_2=intent.getStringExtra("cont_number_2");
        cont_name=intent.getStringExtra("cont_name");
        manag_email=intent.getStringExtra("manag_email");
        book_email=intent.getStringExtra("book_email");
        post=intent.getStringExtra("post");

        property.setText("Property name : "+prop_name);
       total_bed.setText("Total bed : "+tot_bed);
      total_bedroom.setText("Total bedroom : "+tot_bedroom);
        address_1.setText("Address : "+add_1);
        address_2.setText("Landmark : "+add_2);
        state.setText("State : "+state_string);
        country.setText("Country : "+country_string);
        city.setText("City : "+city_string);
        post_code.setText("Post-Code : "+post);
        contact_number.setText("Contact-No. : "+cont_number);
        contact_number_2.setText("Additional contact no. : "+cont_number_2);
        manager_email.setText("Manager E-mail : "+manag_email);
        booking_email.setText("Booking E-mail : "+book_email);
        contact_name.setText("Contact name : "+cont_name);



    }
}
