package com.hostelmanager.hostelmaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.hostelmanager.hostelmaster.helper.BottomNavigationBehavior;





public class BuyAndSell extends AppCompatActivity {




    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_Buy:
              //      mTextMessage.setText(R.string.title_home);
                fragment=new BuyBooks();
                loadFragment(fragment);
                    return true;
                case R.id.navigation_Sell:
                //    mTextMessage.setText(R.string.title_dashboard);
                    loadFragment(new SellBooks());
                    return true;
                case R.id.navigation_yourBooks:
                  //  mTextMessage.setText(R.string.title_notifications);
                    loadFragment(new YourBooks());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_and_sell);
        Intent intent =getIntent();
       String msg= intent.getStringExtra("msg");
       if(msg.equals("openMyBook"))
       {
           loadFragment(new YourBooks());

       }else loadFragment(new BuyBooks());

     //   mTextMessage = (TextView) findViewById(R.id.message);
       BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) navigation.getLayoutParams();
        layoutParams.setBehavior(new BottomNavigationBehavior());
   // actionBar=getSupportActionBar();
   // actionBar.setTitle("Buy");

    }


    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction=getSupportFragmentManager().executePendingTransactions();
        transaction.replace(R.id.frame_container,fragment);
      //  transaction.addToBackStack(null);
        transaction.commit();
    }
}
