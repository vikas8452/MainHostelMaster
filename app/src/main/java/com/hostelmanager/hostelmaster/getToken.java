package com.hostelmanager.hostelmaster;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by dell on 1/29/2018.
 */

public class getToken extends Activity implements AdapterView.OnItemSelectedListener {

    private String selected;
    private TextView date_show;
    private Button btn_get_token;
    private int i;
    private String value;
    String currentDate;
    TextView validate;
    TextView spinner_error;


    @Override

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gettoken);
        FirebaseDatabase database = FirebaseDatabase.getInstance();

        //Firebase storage



        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width=dm.widthPixels;
        int height=dm.heightPixels;

        getWindow().setLayout((int)(width*.94),(int)(height*.6));


        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.college_name, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        validate=findViewById(R.id.validate);



        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("MMM d, yyyy");
        String currentDate = df.format(calendar.getTime());
        //final String e= DateFormat.getDateInstance().format(calendar.getTime());
        final DatabaseReference myRef = database.getReference("Get Token/"+currentDate);


        date_show=findViewById(R.id.date_show);
        selected="Select Your College";
        date_show.setText(currentDate);
        Toast.makeText(getToken.this,selected,Toast.LENGTH_SHORT).show();
        btn_get_token=findViewById(R.id.btn_get_token);
        btn_get_token.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                // Read from the database
                //      Toast.makeText(getToken.this,"entered",Toast.LENGTH_SHORT).show();
                if (!selected.equals("Select Your College"))
                {
                btn_get_token.setEnabled(false);
                validate.setText("Allowed");
                validate.setTextColor(Color.parseColor("#0b7c03"));
                Timer buttonTimer = new Timer();
                buttonTimer.schedule(new TimerTask() {

                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                btn_get_token.setEnabled(true);
                                validate.setText(" Not Allowed");
                                validate.setTextColor(Color.parseColor("#ffcc0000"));

                            }
                        });
                    }
                }, 5000);
                myRef.child(selected).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        value = dataSnapshot.getValue(String.class);
                        int i = Integer.parseInt(value);
                        i += 1;
                        value = Integer.toString(i);
                        myRef.child(selected).setValue(value);

                        Toast.makeText(getToken.this, value, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });
            }
            else{
                    spinner_error=(TextView) spinner.getSelectedView();
                    spinner_error.setTextColor(Color.RED);
                    spinner_error.setTypeface(null, Typeface.BOLD);
                    spinner_error.setText("Select Your College");

                }
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
        selected=adapterView.getItemAtPosition(position).toString();

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
