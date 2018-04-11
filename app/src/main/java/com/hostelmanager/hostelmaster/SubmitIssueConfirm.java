package com.hostelmanager.hostelmaster;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

/**
 * Created by sudha on 03-Feb-18.
 */

public class SubmitIssueConfirm extends AppCompatDialogFragment {

    private TextView type;
    private TextView desc;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference,myRef;
    String s1,s2;

    public SubmitIssueConfirm(){}
    @SuppressLint("ValidFragment")
    public SubmitIssueConfirm(String s1, String s2){
        this.s1=s1;
        this.s2=s2;
    }


    public Dialog onCreateDialog(Bundle savedInstanceState){


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_issue_submition,null);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference(firebaseUser.getUid());

        TextView tv1 = view.findViewById(R.id.dialogIssueSubmitionType);
        tv1.setText(s1);
        TextView tv2 = view.findViewById(R.id.dialogIssueSubmitionDesc);
        tv2.setText(s2);
        final String userId = firebaseUser.getUid();

        myRef = FirebaseDatabase.getInstance().getReference().child(userId).child("issues");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                UserInfo userInfo = new UserInfo();
                userInfo.setIssuetype(ds.getValue(UserInfo.class).getIssuetype());
                userInfo.setDesc(ds.getValue(UserInfo.class).getDesc());
                if(userInfo.getIssuetype() !=null) {
                    s1 = s1 + ", " + userInfo.getIssuetype();
                    s2 = s2 + ", " + userInfo.getDesc();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        builder.setView(view)
                .setTitle("Press confirm to submit")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        UserInfo userInfo=new UserInfo(s1,s2);
                        databaseReference.child("issues").setValue(userInfo);
                    }
                });
        return builder.create();
    }
}
