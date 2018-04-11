package com.hostelmanager.hostelmaster;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class AppSignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final int PICK_IMAGE_REQUEST = 255,REQ=9002;

    private Button SignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String email,pass,rpa;
    private CheckBox checkBox;
    private ImageView imageView;
    private Uri filepath;
    private TextView remail,rpas1,rpas2,fn,mob;
    private String fname,mobile1;
    private StorageReference mStorageRef;
    private DatabaseReference databaseReference;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_sign_up);

        progressDialog = new ProgressDialog(this);
       // firebaseAuth=FirebaseAuth.getInstance();

        //***********************************************************
        //for database to store info
        //***********************************************************
        //databaseReference = FirebaseDatabase.getInstance().getReference();

        fn = findViewById(R.id.Appsignupfn);
        remail = findViewById(R.id.Appsignupemail);
        rpas1 = findViewById(R.id.Appsignuppass1);
        rpas2 = findViewById(R.id.Appsignuppass2);
        mob = findViewById(R.id.Appsignupmob);
        checkBox = findViewById(R.id.Appsignupcb);
        imageView = findViewById(R.id.Appsignupiv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagechoose();
            }
        });

       // mStorageRef = FirebaseStorage.getInstance().getReference();

        showDialog();

        ///// For Sign up button
        SignUp = findViewById(R.id.Appsignupsignupbtn);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = remail.getText().toString();
                pass = rpas1.getText().toString();
                rpa = rpas2.getText().toString();
                fname = fn.getText().toString().trim();
                mobile1 = mob.getText().toString().trim();
                if(fname.equals("")) {
                    Toast.makeText(AppSignUp.this, "Please enter First Name", Toast.LENGTH_LONG).show();
                    return;
                } else if(email.equals("")){
                    Toast.makeText(AppSignUp.this , "Please enter email",Toast.LENGTH_LONG).show();
                    return ;
                } else if (mobile1.equals("")){
                    Toast.makeText(AppSignUp.this , "Please enter your Mobile no.",Toast.LENGTH_LONG).show();
                    return ;
                } else if( pass.equals("") || rpa.equals("") ){
                    Toast.makeText(AppSignUp.this , "Please enter password",Toast.LENGTH_LONG).show();
                    return ;
                } else if(!pass.equals(rpa)){
                    Toast.makeText(AppSignUp.this,"Password did not match",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(!checkBox.isChecked()) {
                    Toast.makeText(AppSignUp.this, "You have to accept the TERMS and CONDITION", Toast.LENGTH_LONG).show();
                    return;
                };



                Intent intent = new Intent(getBaseContext() , MobileAndOTP.class);
                intent.putExtra("email",email);
                intent.putExtra("uid",pass);
                intent.putExtra("name",fname);
                intent.putExtra("mobile",mobile1);
                intent.putExtra("filepath",filepath);
                startActivity(intent);

               /* progressDialog.setMessage("Registerring user please wait...");
                progressDialog.show();
                progressDialog.setCancelable(false);

                //*****************************************************************
                //For Registering Users with only email and Password
                //*****************************************************************
                firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            progressDialog.dismiss();
                            uploadimg();
                            Toast.makeText(AppSignUp.this,"User registered Successfully",Toast.LENGTH_SHORT).show();
                            saveuserinfo();
                         //   startActivity(new Intent(AppSignUp.this,MainActivity.class));
                        }else{
                            progressDialog.dismiss();
                            Toast.makeText(AppSignUp.this,"User registration failed .\n     Please Try Again .",Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/
            }
        });
    }


    private void showDialog() {
    }

    private void imagechoose() {
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an image"),PICK_IMAGE_REQUEST);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            filepath = data.getData();

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filepath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

   /* private void saveuserinfo() {
        fname = fn.getText().toString().trim();
        mobile1 = mob.getText().toString().trim();

        UserInfo userInfo=new UserInfo(fname,mobile1,email,pass);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInfo);

    }

    private void uploadimg() {
        final ProgressDialog progressDialo= new ProgressDialog(this);
        if(filepath!=null) {
            progressDialo.setTitle("Uploading...");
            progressDialo.show();
            FirebaseUser user = firebaseAuth.getCurrentUser();
            StorageReference storageReference2= mStorageRef.child("profile").child(user.getUid());
            storageReference2.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialo.dismiss();
                            startActivity(new Intent(AppSignUp.this,MainActivity.class));
                            Toast.makeText(getApplicationContext(),"Profile Uploaded",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AppSignUp.this,MainActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialo.dismiss();
                            startActivity(new Intent(AppSignUp.this,MainActivity.class));
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(AppSignUp.this,MainActivity.class));
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            long dp=100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount();
                            progressDialo.setMessage((int)dp+"% uploaded" );

                        }
                    });
        }
        else{
            progressDialo.dismiss(); 
            startActivity(new Intent(AppSignUp.this,MainActivity.class));
            Toast.makeText(this,"Select any Image",Toast.LENGTH_SHORT).show();
        }
    }*/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
