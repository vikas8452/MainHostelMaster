package com.hostelmanager.hostelmaster;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

/**
 * Created by sudha on 17-Feb-18.
 */

public class AppSignUpp extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 255,REQ=9002;

    private Button SignUp;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String email,pass,rpa;
    private CheckBox checkBox;
    private ImageView imageView;
    private Uri filepath;
    private TextView remail,rpas1,rpas2,fn,ln,mob;
    private String fname,mobile1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_sign_up);

        progressDialog = new ProgressDialog(this);
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
                    Toast.makeText(AppSignUpp.this, "Please enter First Name", Toast.LENGTH_LONG).show();
                    return;
                } else if(email.equals("")){
                    Toast.makeText(AppSignUpp.this , "Please enter email",Toast.LENGTH_LONG).show();
                    return ;
                } else if (mobile1.equals("")){
                    Toast.makeText(AppSignUpp.this , "Please enter your Mobile no.",Toast.LENGTH_LONG).show();
                    return ;
                } else if( pass.equals("") || rpa.equals("") ){
                    Toast.makeText(AppSignUpp.this , "Please enter password",Toast.LENGTH_LONG).show();
                    return ;
                } else if(!pass.equals(rpa)){
                    Toast.makeText(AppSignUpp.this,"Password did not match",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(!checkBox.isChecked()) {
                    Toast.makeText(AppSignUpp.this, "You have to accept the TERMS and CONDITION", Toast.LENGTH_LONG).show();
                    return;
                }


            }
        });

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
}
