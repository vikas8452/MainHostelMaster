package com.hostelmanager.hostelmasterr;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MobileAndOTP extends AppCompatActivity {

    private TextView tv1,tv2,nochange,tv4,tv5,tv6;
    private Button button1,button2;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth mAuth;
    String phoneNumber;
    private boolean doubleBackToExitPressedOnce;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_and_otp);

        mAuth = FirebaseAuth.getInstance();

        tv1 = findViewById(R.id.phone);
        tv2 = findViewById(R.id.otp);
        button1 = findViewById(R.id.send);
        button2 = findViewById(R.id.verify);
        nochange = findViewById(R.id.nochange);
        tv4 = findViewById(R.id.noshowe);
        tv5 = findViewById(R.id.noshow);
        tv6 = findViewById(R.id.noshower);

        tv2.setVisibility(View.GONE);
        tv4.setVisibility(View.GONE);
        tv5.setVisibility(View.GONE);
        tv6.setVisibility(View.GONE);
        nochange.setVisibility(View.GONE);
        button2.setVisibility(View.GONE);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                mVerificationInProgress = false;
                Toast.makeText(MobileAndOTP.this,"Verification done",Toast.LENGTH_SHORT).show();
                signInWithPhoneAuthCredential(phoneAuthCredential);

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
                    Toast.makeText(MobileAndOTP.this,"Please Try again...",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                Toast.makeText(MobileAndOTP.this,"OTP has been sent to your number",Toast.LENGTH_SHORT).show();

                mVerificationId = s;
                mResendToken = forceResendingToken;

                mVerificationId = s;
                mResendToken = forceResendingToken;

                tv1.setVisibility(View.GONE);
                button1.setVisibility(View.GONE);
                tv2.setVisibility(View.VISIBLE);
                tv4.setVisibility(View.VISIBLE);
                tv5.setVisibility(View.VISIBLE);
                tv6.setVisibility(View.VISIBLE);
                nochange.setVisibility(View.VISIBLE);
                button2.setVisibility(View.VISIBLE);
                nochange.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        tv2.setVisibility(View.GONE);
                        tv4.setVisibility(View.GONE);
                        tv5.setVisibility(View.GONE);
                        tv6.setVisibility(View.GONE);
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

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MobileAndOTP.this,"Verified",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MobileAndOTP.this,MainActivity.class);
                            startActivity(intent);
                            startActivity(new Intent(MobileAndOTP.this, Issues.class));
                            overridePendingTransition(R.anim.slide_in_up, R.anim.slide_out_up);
                            finish();

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
