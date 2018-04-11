package com.hostelmanager.hostelmaster;

import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

import java.io.IOException;

public class SignUp extends AppCompatActivity implements AdapterView.OnItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    private static final int PICK_IMAGE_REQUEST = 253;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;

    private Button SignUp;
    private Button btn;
    private int year_x,month_x,day_x;
    private static final int DIALOG_ID = 0;
    private TextView textView,remail,rpass,rpas,tv1,tv2,tv3,tv4,tv5,tv6,tv7,tv8,tv9,tv10,tv11,tv12;
    private RadioGroup rg;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private String email,pass,rpa,country;
    private DatabaseReference databaseReference;
    private CheckBox checkBox;
    private ImageView imageView;
    private Uri filepath;
    private StorageReference storageReference;
    String name,dob,gender,mobile1,mobile2,address,father,mother,college,guardian,guardianmobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        storageReference= FirebaseStorage.getInstance().getReference();

        progressDialog = new ProgressDialog(this);
        firebaseAuth=FirebaseAuth.getInstance();

        //for database to store info
        databaseReference = FirebaseDatabase.getInstance().getReference();

        //gettig id of sign up page view to store in database
        tv1 = findViewById(R.id.registrationName);
        tv2 = findViewById(R.id.registrationDobtf);
        rg  = findViewById(R.id.registrationRadiogroup);
        tv3 = findViewById(R.id.registrationMobile);
        tv4 = findViewById(R.id.registrationMobile2);
        tv5 = findViewById(R.id.registrationAddress);
        tv6 = findViewById(R.id.registrationFatherName);
        tv7 = findViewById(R.id.registrationMotherName);
        tv8 = findViewById(R.id.registrationCollege);
        tv9 = findViewById(R.id.registrationGuardianName);
        tv10 = findViewById(R.id.registrationGuardionMobile);


        tv11 = findViewById(R.id.namett);
        tv12 = findViewById(R.id.dobtt);
        checkBox = findViewById(R.id.registrationTnCcheckbox);

        //For profile image
        imageView = findViewById(R.id.registrationiv);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagechoose();
            }
        });

        GoogleSignInOptions googleSignInOptions=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        final GoogleApiClient googleApiClient= new GoogleApiClient.Builder(this).enableAutoManage(this,this).addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions).build();


        showDialog();
        spinner=(Spinner)findViewById(R.id.registrationCountryspinner);
        adapter = ArrayAdapter.createFromResource(this , R.array.Country,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        remail = findViewById(R.id.registrationemail);
        rpass = findViewById(R.id.registrationpassword);
        rpas = findViewById(R.id.registrationpassword2);

        //For SignUp Button
        SignUp=findViewById(R.id.registrationsignupbtn);
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registeruser();
            }
        });
    }

    //when SignUp Button clicked
    private void registeruser() {

        email = remail.getText().toString();
        pass = rpass.getText().toString();
        rpa = rpas.getText().toString();


        name = tv1.getText().toString().trim();
        dob = tv2.getText().toString().trim();

        //getting text from selected radio button
        int rgbid = rg.getCheckedRadioButtonId();
        RadioButton bbtn=findViewById(rgbid);
        gender = bbtn.getText().toString().trim();

        mobile1 = tv3.getText().toString().trim();
        mobile2 = tv4.getText().toString().trim();
        address = tv5.getText().toString().trim();
        father = tv6.getText().toString().trim();
        mother = tv7.getText().toString().trim();
        college = tv8.getText().toString().trim();
        guardian = tv9.getText().toString().trim();
        guardianmobile = tv10.getText().toString().trim();

        if(tv1.getText().toString().equals("")) {
            tv11.setText("Please Enter Name");
            Toast.makeText(this , "Please enter your name",Toast.LENGTH_LONG).show();
            return;
        }
        else{
            tv11.setText("");
        }
        if(tv2.getText().toString().equals("")){
            tv12.setText("Please Select date of birth");
            Toast.makeText(this , "Please Select date of birth",Toast.LENGTH_LONG).show();
            return;
        }

        if( pass.equals("") || rpa.equals("") ){
            Toast.makeText(this , "Please enter password",Toast.LENGTH_LONG).show();
            return ;
        } else if(!pass.equals(rpa)){
            Toast.makeText(SignUp.this,"Password did not match",Toast.LENGTH_SHORT).show();
            return ;
        } else if(email.equals("")){
            Toast.makeText(this , "Please enter email",Toast.LENGTH_LONG).show();
            return ;
        } else if(mobile1.equals("")||mobile2.equals("")){
            Toast.makeText(this , "Please provide at least one mobile number",Toast.LENGTH_LONG).show();
            return ;
        } else if(address.equals("")){
            Toast.makeText(this , "Please enter your address",Toast.LENGTH_LONG).show();
            return ;
        } else if(guardian.equals("")){
            Toast.makeText(this , "Please enter your Guardian name",Toast.LENGTH_LONG).show();
            return ;
        } else if(guardianmobile.equals("")){
            Toast.makeText(this , "Please enter your guardian mobile number.",Toast.LENGTH_LONG).show();
            return ;
        }

        if(!checkBox.isChecked()) {
            Toast.makeText(this, "You have to accept the TERMS and CONDITION", Toast.LENGTH_LONG).show();
            return;
        }

        //For Dialog
        progressDialog.setMessage("Registerring user please wait...");
        progressDialog.show();

        //For Registering Users with only email and Password
        firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    uploadimg();
                    Toast.makeText(SignUp.this,"User registered Successfully",Toast.LENGTH_SHORT).show();
                    saveuserinfo();
                    //startActivity(new Intent(SignUp.this,MainActivity.class));
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(SignUp.this,"User registration failed .\n     Please Try Again .",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void uploadimg(){
        if(filepath!=null) {

            final ProgressDialog progressDialo= new ProgressDialog(this);
            progressDialo.setTitle("Uploading...");
            progressDialo.show();
            StorageReference profileRef = storageReference.child("images/profile.jpg");

            profileRef.putFile(filepath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialo.dismiss();
                            Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this,MainActivity.class));
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialo.dismiss();
                            Toast.makeText(getApplicationContext(),"Failed",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUp.this,MainActivity.class));
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            long dp=100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount();
                            progressDialo.setMessage((int)dp+"% uploaded" );

                        }
                    })
            ;
        } else{
            Toast.makeText(this,"Select any Image",Toast.LENGTH_SHORT).show();
        }
    }

    //for Choosing Image
    private void imagechoose(){
        Intent intent=new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select an image"),PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(  requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK && data!=null){
            filepath=data.getData();

            try {
                Bitmap bitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //for saving user info into database
    private void saveuserinfo() {

        UserInfo userInfo=new UserInfo(name,dob,gender,country,mobile1,mobile2,address,father,mother,college,guardian,guardianmobile);

        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userInfo);

    }

    //For Date Picker Button if Clicked
    public void showDialog(){
        btn=(Button)findViewById(R.id.registrationpicDatebtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(DIALOG_ID);
            }
        });
    }

    //For Date Picker
    @Override
    protected Dialog onCreateDialog(int id){
        if(id==DIALOG_ID)
            return new DatePickerDialog(this , dpickerListner , year_x , month_x , day_x);
        return null;
    }

    //for Setting Date in text View.
    private DatePickerDialog.OnDateSetListener dpickerListner
        = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            year_x=year;
            month_x=month+1;
            day_x=dayOfMonth;
            textView=(TextView)findViewById(R.id.registrationDobtf);
            textView.setText(day_x + "/" + month_x + "/" + year_x);

        }
    };


    //For Spinner for Country.
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        country=parent.getItemAtPosition(position).toString();
        String s="";
        int i=1;
        while(country.charAt(i)!=')'){
            s=s+country.charAt(i);
            i++;
        }
        TextView tv=findViewById(R.id.showcountry);
        tv.setText(s);
    }
    //also For Spinner if nothing is Selected but India as Default is already set
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
