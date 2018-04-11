package com.hostelmanager.hostelmaster;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class MobileAndOTP extends AppCompatActivity {

    private TextView tv1,tv2,tv3,nochange,tv4,tv5;
    private Button button1,button2;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private DatabaseReference databaseReference;
    String phoneNumber;
    String gname,email,uid;
    Uri filepath;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



       /* savedInstanceState = getIntent().getExtras();
        gname = savedInstanceState.getString("name");
        email = savedInstanceState.getString("email");
        uid = savedInstanceState.getString("uid");
        filepath = (Uri) savedInstanceState.get("filepath");
        phoneNumber = savedInstanceState.getString("mobile");*/
        setContentView(R.layout.activity_mobile_and_otp);

        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        tv1 = findViewById(R.id.phone);
        tv2 = findViewById(R.id.otp);
        button1 = findViewById(R.id.send);
        button2 = findViewById(R.id.verify);
        nochange = findViewById(R.id.nochange);
        tv4 = findViewById(R.id.noshowe);
        tv5 = findViewById(R.id.noshower);

        tv2.setVisibility(View.GONE);
        tv4.setVisibility(View.GONE);
        tv5.setVisibility(View.GONE);
        nochange.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);

        tv3 = findViewById(R.id.detail);
        tv3.setText(gname +" "+ email +" "+ uid);

        if(phoneNumber!=null){
            tv1.setText(phoneNumber);
            startPhoneNumberVerification(phoneNumber);
        }

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                mVerificationInProgress = false;
                Toast.makeText(MobileAndOTP.this,"Verification done",Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);
                //register();
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                Toast.makeText(MobileAndOTP.this,"Verification Failed",Toast.LENGTH_SHORT).show();
                //register();
                if(e instanceof FirebaseAuthInvalidCredentialsException)
                {
                    Toast.makeText(MobileAndOTP.this,"Invalid Phone Number",Toast.LENGTH_SHORT).show();
                }
                else if(e instanceof FirebaseTooManyRequestsException) {
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(MobileAndOTP.this,"OTP has been sent to your number",Toast.LENGTH_SHORT).show();

                mVerificationId = s;
                mResendToken = forceResendingToken;

                tv1.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                tv5.setText(tv5.getText() + phoneNumber);
                tv2.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.VISIBLE);
                nochange.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                nochange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv2.setVisibility(View.GONE);
                        tv4.setVisibility(View.GONE);
                        tv5.setVisibility(View.GONE);
                        nochange.setVisibility(View.GONE);
                        button2.setVisibility(View.GONE);
                        nochange.setVisibility(View.GONE);

                        tv1.setVisibility(View.VISIBLE);
                        button1.setVisibility(View.VISIBLE);
                    }
                });
            }
        };

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyPhoneNumberWithCode(mVerificationId , tv2.getText().toString());
            }
        });

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneNumber = tv1.getText().toString();
                tv3.setText(phoneNumber);
                startPhoneNumberVerification(phoneNumber);
            }
        });
    }
    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }


    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);

        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
        //register();
    }

    /*private void register(){
        progressDialog.setMessage("Registerring user please wait...");
        progressDialog.show();
        progressDialog.setCancelable(false);

        //*****************************************************************
        //For Registering Users with only email and Password
        //*****************************************************************
        mAuth.createUserWithEmailAndPassword(email,uid).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    //uploadimg();
                    Toast.makeText(MobileAndOTP.this,"User registered Successfully",Toast.LENGTH_SHORT).show();
                    saveuserinfo();
                    mAuth.signInWithEmailAndPassword(email,uid);
                    startActivity(new Intent(MobileAndOTP.this,MainActivity.class));
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(MobileAndOTP.this,"User registration failed .\n     Please Try Again .",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void saveuserinfo() {

        UserInfo userInfo=new UserInfo(gname,phoneNumber,email,uid);

        FirebaseUser user = mAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInfo);

    }*/


    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MobileAndOTP.this,"Verified",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MobileAndOTP.this,MainActivity.class);
                            UserInfo userInfo=new UserInfo(" "," ");
                            databaseReference.child("issues").setValue(userInfo);
                            startActivity(intent);
                        } else {
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(MobileAndOTP.this,"Invalid verification",Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }
    public void onBackPressed() {

        if (doubleBackToExitPressedOnce) {
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
}
