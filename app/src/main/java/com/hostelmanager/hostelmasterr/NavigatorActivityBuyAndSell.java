package com.hostelmanager.hostelmasterr;

/**
 * Created by Himanshu on 7/20/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NavigatorActivityBuyAndSell extends Activity {

    private Button btnBuy;
    private Button btnSell;
    private Button btnSyllabus;
    private Button btnAboutUs;
    private Button btnSettings;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigator);

        btnBuy = (Button) findViewById(R.id.btnBuy);
        btnSell = (Button) findViewById(R.id.btnSell);
        btnSyllabus = (Button) findViewById(R.id.btnSyllabus);
        btnAboutUs = (Button) findViewById(R.id.btnAboutUs);
        btnSettings = (Button) findViewById(R.id.btnSettings);

        btnBuy.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            // Intent i= new Intent(NavigatorActivityBuyAndSell.this, BuyActivity.class);
              //  startActivity(i);

            }

        });

        btnSell.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

              //  Intent i= new Intent(NavigatorActivityBuyAndSell.this, SellActivity.class);
              //  startActivity(i);
            }

        });

        btnSyllabus.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //Intent i= new Intent(NavigatorActivityBuyAndSell.this, SingleSyllabusActivity.class);
                //startActivity(i);
            }

        });

        btnSettings.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                //Intent i= new Intent(NavigatorActivityBuyAndSell.this, SettingsActivity.class);
                //startActivity(i);
            }

        });

        btnAboutUs.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

          //      Intent i= new Intent(NavigatorActivityBuyAndSell.this, AboutUsActivity.class);
            //    startActivity(i);
            }

        });

    }
}