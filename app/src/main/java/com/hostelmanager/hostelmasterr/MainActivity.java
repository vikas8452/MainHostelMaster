package com.hostelmanager.hostelmasterr;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
//import com.onesignal.OneSignal;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Toolbar toolbar=null;
    NavigationView navigationView=null;
    private FirebaseAuth firebaseAuth;
    boolean doubleBackToExitPressedOnce = false;
    private String userId;
    private DatabaseReference myRef;
    private StorageReference mStorageRef;
    private ImageView imageView;
    private Uri tri;
    private ProgressDialog progressDialog;
    PhoneAuthCredential phoneAuthCredential;

    @Override
    protected void onStart() {
        super.onStart();

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        if(currentUser == null){
            startActivity(new Intent(this,MobileAndOTP.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       /* OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();*/
        setContentView(R.layout.activity_main);



        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        String s1=FirebaseInstanceId.getInstance().getToken();



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait while data is loading");

        FirebaseMessaging.getInstance().subscribeToTopic("News");
        FirebaseMessaging.getInstance().subscribeToTopic("Movies");

        myRef = FirebaseDatabase.getInstance().getReference();
        myRef.child("12").setValue(s1);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        firebaseAuth= FirebaseAuth.getInstance();
        final FirebaseUser currentUse= firebaseAuth.getCurrentUser();



      //  final String phone = currentUser.getPhoneNumber();

      /*  myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                if (!snapshot.hasChild(currentUse.getPhoneNumber())) {
                    // run some code
                    Toast.makeText(MainActivity.this,"You are not yet registered",Toast.LENGTH_SHORT).show();
                }else{
                    FirebaseMessaging.getInstance().subscribeToTopic("News");
                    FirebaseMessaging.getInstance().subscribeToTopic("Movies");
                    myRef.child(currentUse.getPhoneNumber()).child("token").setValue(FirebaseInstanceId.getInstance().getToken());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });*/
        //if(currentUser!=null)
        //OneSignal.sendTag("User_ID",currentUser.getPhoneNumber());
        /*if(currentUser == null){
            startActivity(new Intent(this,MobileAndOTP.class));
        }
        //updateUI(currentUser);
        //if user already logged in set Profile(Activity)
        /*if(currentUser!=null){
            progressDialog.show();
            progressDialog.setCancelable(false);
            userinfo.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {


                    UserInfo userInfo = new UserInfo();
                    userInfo.setFname(ds.child(userId).getValue(UserInfo.class).getFname());
                    userInfo.setMob(ds.child(userId).getValue(UserInfo.class).getMob());

                    NavigationView navigationView = findViewById(R.id.nav_view);
                    TextView txtProfileNam = navigationView.getHeaderView(0).findViewById(R.id.account);
                    txtProfileNam.setText(userInfo.getFname());
                    progressDialog.dismiss();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



            FirebaseUser user = firebaseAuth.getCurrentUser();
            userId = user.getUid();

            final NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            imageView = navigationView.getHeaderView(0).findViewById(R.id.imageView);
            StorageReference storageReference2= mStorageRef.child("profile").child(user.getUid());
            try{
                storageReference2.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Picasso.with(getApplicationContext()).load(uri).into(imageView);
                    }
                });
            }
            catch(Exception e){
                progressDialog.dismiss();
                Toast.makeText(this,""+e,Toast.LENGTH_SHORT).show();
            }
            TextView txtProfileNam = navigationView.getHeaderView(0).findViewById(R.id.viewaccount);
            TextView txtProfileName = navigationView.getHeaderView(0).findViewById(R.id.account);
            txtProfileNam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    profiler prof=new profiler();
                    android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.relativefrag , prof).commit();
                }
            });
            txtProfileName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DrawerLayout drawer = findViewById(R.id.drawer_layout);
                    drawer.closeDrawer(GravityCompat.START);
                    profiler prof=new profiler();
                    android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
                    manager.beginTransaction().replace(R.id.relativefrag , prof).commit();
                }
            });
        } else {
            NavigationView navigationView = findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);
            TextView txtProfileName = navigationView.getHeaderView(0).findViewById(R.id.viewaccount);
            txtProfileName.setText("");
            TextView txtProfileNam = navigationView.getHeaderView(0).findViewById(R.id.account);
            txtProfileNam.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(MainActivity.this,SignInerActivity.class));
                }
            });
            progressDialog.dismiss();
        }*/


        Explore explore=new Explore();
        android.support.v4.app.FragmentManager manager=getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.relativefrag,explore).commit();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        TextView accc=navigationView.getHeaderView(0).findViewById(R.id.viewaccount);
        accc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(MainActivity.this,MainActivity.class));
                Explore explore=new Explore();
                android.support.v4.app.FragmentManager manager=getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.relativefrag,explore).commit();
            }
        });

    }

    @Override
    public void onBackPressed() {
        Explore explore=new Explore();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativefrag, explore).commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return ;
        } else if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,Notifications.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.explore) {
          /*Explore explore=new Explore();
            android.support.v4.app.FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativefrag,explore).commit();
*/
            Explore explore=new Explore();
            android.support.v4.app.FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativefrag,explore).commit();
        }
        else if (id == R.id.myhostel) {
            //startActivity(new Intent(this,HostelSignUp.class));
            MyHostell myHostell=new MyHostell();
            android.support.v4.app.FragmentManager manager=getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativefrag,myHostell).commit();

        } else if (id == R.id.myaccount) {
            profiler prof=new profiler();
            android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.relativefrag , prof).commit();

        }

        else if (id == R.id.issues) {
            startActivity(new Intent(this,MyIssues.class));
        }
        else if (id == R.id.feedback) {
            startActivity(new Intent(this,feedback.class));

        }/* else if (id == R.id.workwithus) {
            startActivity(new Intent(this,workwithus.class));

        }*/else if (id == R.id.buyandSell) {

            Intent intent=new Intent(this,BuyAndSell.class);
          intent.putExtra("msg","");
            startActivity(intent);
        }else if (id == R.id.rateus) {
            startActivity(new Intent(this,QRCodeScanner.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
