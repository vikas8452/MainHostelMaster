package com.hostelmanager.hostelmaster;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputEditText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.hostelmanager.hostelmaster.Model.BuySellSubject;
import com.hostelmanager.hostelmaster.helper.FireBaseHelper;

import java.util.Objects;

import butterknife.BindView;

@SuppressLint("ValidFragment")
public class BottomSheetFragment extends BottomSheetDialogFragment {
    Button finalSubmit;
    FireBaseHelper helper;
    DatabaseReference db,myRef;

    TextInputEditText actualPrice;
RadioGroup rgCondition;
    String condition;
    RadioButton radioButton;
    public BottomSheetFragment(){}
    String bookName;
    String authorName;
    String description;
    String imagepath;
    String price;
    StorageReference riversRef;
    FirebaseUser currentUser;
    FirebaseAuth firebaseAuth;
    Boolean saved;
    String department="";
    String semester="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.bottom_sheet_for_set_price, container, false);
//Firebase Helper

        currentUser=FirebaseAuth.getInstance().getCurrentUser();


        BuySellSubject b;

        finalSubmit = view.findViewById(R.id.finalSubmit);
        actualPrice= view.findViewById(R.id.actualPrice);
        rgCondition= view.findViewById(R.id.rgCondition);

     //
        Bundle bundle=getArguments();
        bookName=bundle.getString("bookName");
        authorName=bundle.getString("authorName");




        if(bundle.get("imagePath")!=null)
            imagepath= (String) bundle.get("imagePath");



            description = bundle.getString("description");
            department = bundle.getString("Department");
            semester = bundle.getString("Semester");
        Toast.makeText(getActivity(), semester, Toast.LENGTH_SHORT).show();
            // description=bundle.get("description").toString();



// getting reference to the database for sending the data for show in the BuyBooks
        Toast.makeText(getContext(), department, Toast.LENGTH_SHORT).show();
        try {
            db = FirebaseDatabase.getInstance().getReference("BuyAndSell").child("BooksForSell").child(department).child(semester);
           // helper = new FireBaseHelper(db);
        }
        catch (NullPointerException e)
        {
            e.printStackTrace();
        }
        helper = new FireBaseHelper(db);
        finalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //getting the condition of the books
                 rgCondition = (RadioGroup) view.findViewById(R.id.radioGroup1);
              //  condition =((RadioButton)view.findViewById(rgCondition.getCheckedRadioButtonId())).getText().toString();
              //  Toast.makeText(getActivity(), selectedId+"", Toast.LENGTH_SHORT).show();
                condition="poor";

                setData();
                if(imagepath!=null)
                    uploadFile(imagepath);
                Log.d("f","Calling buy And Sell");
                Intent intent = new Intent(getActivity(), BuyAndSell.class);
                Log.d("da","calling buy and sell");
                intent.putExtra("msg", "openMyBook");
                startActivity(intent);
            }
            //  Toast.makeText(getContext(), "scjhddjc", Toast.LENGTH_SHORT).show();

        });

        return view;


    }

    private void setData() {
       // Toast.makeText(getActivity(), "sdfed", Toast.LENGTH_SHORT).show();
     //   String price=actualPrice.getText().toString();
     //   value=radioButton.getText().toString();
        BuySellSubject b=new BuySellSubject();
       // b.setBookPrice(price);
        price=actualPrice.getText().toString();


        b.setBookName(bookName);
        b.setAuthorName(authorName);
        b.setBookCondition(condition);
        b.setBookPrice(price);
        b.setBookDescription(description);

        if(helper.saveMySellBook(b))
        {
            actualPrice.setText("");
            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
        }

        if(saveDataForShowInYourBook(b))
        {
            actualPrice.setText("");
            Toast.makeText(getActivity(), "Saved", Toast.LENGTH_SHORT).show();
        }


    }
    private void uploadFile(String filepath) {
        //if there is a file to upload
        Log.d("dssd","Entered in upload file");
        Uri filePath=Uri.parse(filepath);
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setTitle("Uploading");
            progressDialog.show();

          riversRef = FirebaseStorage.getInstance().getReference().child("images/pic.jpg");
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            //if the upload is successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying a success toast
                          //  Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successfull
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                        //    Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }


    private Boolean saveDataForShowInYourBook(BuySellSubject b)
    {
        firebaseAuth= FirebaseAuth.getInstance();
        currentUser= firebaseAuth.getCurrentUser();
myRef=FirebaseDatabase.getInstance().getReference("BuyAndSell").child("MyBooksForSell");
        if(b==null)
        {
            saved=false;
        }
        else
        {
            try
            {
                myRef.child(currentUser.getPhoneNumber()).push().setValue(b);
                saved=true;

            }
            catch(DatabaseException ex)
            {
                ex.printStackTrace();
                saved=false;
            }
        }
        return saved;
    }

}
