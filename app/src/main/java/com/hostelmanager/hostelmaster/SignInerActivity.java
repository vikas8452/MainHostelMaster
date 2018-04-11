package com.hostelmanager.hostelmaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class SignInerActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    private String email,pass,mobile1;
    private Button button;
    private TextView textView1;
    private TextView textView2;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private TextView textView;
    private ImageButton imgbtn;
    private static final int REQ=9002;
    private String gemail,guid,gname;
    private Uri filepath;
    GoogleSignInResult googleSignInResult;
    GoogleApiClient googleApiClient;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_iner);

        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleApiClient= new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();

        databaseReference = FirebaseDatabase.getInstance().getReference();

        ImageButton imgbtn = findViewById(R.id.AppsignupGSignin);
        imgbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent= Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,REQ);
            }
        });

        progressDialog=new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        textView1=findViewById(R.id.signinemail);
        textView2=findViewById(R.id.signinpassword);
        button=findViewById(R.id.signinsigninbtn);

        //Sign in button click listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        //For opening sign up page by clicking sign up textview
        textView=findViewById(R.id.signinsignuptv);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in= new Intent(SignInerActivity.this,AppSignUp.class);
                startActivity(in);
            }
        });

    }

    private void userLogin() {
        email=textView1.getText().toString().trim();
        pass=textView2.getText().toString().trim();

        if(email.equals("")){
            Toast.makeText(SignInerActivity.this , "Please enter email",Toast.LENGTH_LONG).show();
            return;
        }else if(pass.equals("")){
            Toast.makeText(SignInerActivity.this , "Please enter password",Toast.LENGTH_LONG).show();
            return;
        }
        progressDialog.setMessage("Signing in please wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(SignInerActivity.this , "User logged in",Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(SignInerActivity.this,MainActivity.class));
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SignInerActivity.this , "Incorrect user id or password",Toast.LENGTH_SHORT).show();
                    textView2.setText("");
                }
            }
        });

    }
   /* public void applyTexts(String s1) {
        mobile1 = s1;


        progressDialog.setMessage("Registerring user please wait...");
        progressDialog.show();

        //*****************************************************************
        //For Registering Users with only email and Password
        //*****************************************************************
        firebaseAuth.createUserWithEmailAndPassword(gemail,guid).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(SignInerActivity.this,"User registered Successfully",Toast.LENGTH_SHORT).show();

                    // saving user information();
                    UserInfo userInfo=new UserInfo(gname,mobile1,gemail,guid);

                    FirebaseUser user = firebaseAuth.getCurrentUser();
                    databaseReference.child(user.getUid()).setValue(userInfo);
                    //upload image();
                    startActivity(new Intent(SignInerActivity.this,MainActivity.class));
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SignInerActivity.this,"User registration failed .\n     Please Try Again .",Toast.LENGTH_SHORT).show();
                }

            }
        });
    }*/

    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==REQ){
            googleSignInResult= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(googleSignInResult.isSuccess()){

                final GoogleSignInAccount account = googleSignInResult.getSignInAccount();

                gemail = account.getEmail();
                guid = gemail + "741258963";
                filepath = account.getPhotoUrl();


                progressDialog.setMessage("Please wait...");
                progressDialog.show();

                firebaseAuth.signInWithEmailAndPassword(gemail,guid).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(SignInerActivity.this , "User logged in",Toast.LENGTH_SHORT).show();
                            finishAndRemoveTask();
                            startActivity(new Intent(SignInerActivity.this,MainActivity.class));
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(SignInerActivity.this , "Incorrect user id or password",Toast.LENGTH_SHORT).show();
                            //googleSignInResult= Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                            //if(googleSignInResult.isSuccess()){

                                //GoogleSignInAccount account = googleSignInResult.getSignInAccount();

                                gname = account.getDisplayName();
                               // gemail = account.getEmail();
                                //guid = account.getId();
                                //filepath = account.getPhotoUrl();
                                //MobileAndOTP mobileAndOTP =new MobileAndOTP(gemail,guid,guid,filepath);
                                Intent intent = new Intent(SignInerActivity.this , MobileAndOTP.class);
                                intent.putExtra("email",gemail);
                                intent.putExtra("uid",guid);
                                intent.putExtra("name",gname);
                                intent.putExtra("filepath",filepath);
                                startActivity(intent);
                                finishAndRemoveTask();
                            //}
                            //else
                                Toast.makeText(SignInerActivity.this,"Sign up Failed ",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
            else
                Toast.makeText(this,"Sign in Failed ",Toast.LENGTH_SHORT).show();
        }
    }
    public void onBackPressed(){
        finish();
        startActivity(new Intent(this,MainActivity.class));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void signInWithPhoneAuthCredential(PhoneAuthCredential phoneAuthCredential) {
        firebaseAuth.signInWithCredential(phoneAuthCredential)
                .addOnCompleteListener(this , new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(SignInerActivity.this,"Doneeee",Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignInerActivity.this,"Invaliddd",Toast.LENGTH_SHORT).show();

            }
        });
    }

    /*@Override
    public void applyOtp(String s1) {

    }*/
}
