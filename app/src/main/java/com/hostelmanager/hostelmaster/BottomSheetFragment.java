package com.hostelmanager.hostelmaster;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.TextInputEditText;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hostelmanager.hostelmaster.helper.FireBaseHelper;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

@SuppressLint("ValidFragment")
public class BottomSheetFragment extends BottomSheetDialogFragment {
    Button finalSubmit;
    FireBaseHelper helper;
    DatabaseReference db;

    TextInputEditText actualPrice;
RadioGroup rgCondition;
    String value;
    RadioButton radioButton;
    public BottomSheetFragment(){}
    String bookName;
    String authorName;
    String description;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        final View view = inflater.inflate(R.layout.bottom_sheet_for_set_price, container, false);
//Firebase Helper
    db=FirebaseDatabase.getInstance().getReference();
    helper=new FireBaseHelper(db);


        finalSubmit = view.findViewById(R.id.finalSubmit);
        actualPrice= view.findViewById(R.id.actualPrice);
        rgCondition= view.findViewById(R.id.rgCondition);

     //
        Bundle bundle=getArguments();
        bookName=bundle.get("bookName").toString();
        authorName=bundle.get("authorName").toString();
        description=bundle.get("description").toString();

        finalSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   rgCondition=view.findViewById(rgCondition.getCheckedRadioButtonId());
                //value =((RadioButton)view.findViewById(rgCondition.getCheckedRadioButtonId())).getText().toString();
                int selectedId = rgCondition.getCheckedRadioButtonId();

                // find the radiobutton by returned id
                radioButton = (RadioButton) view.findViewById(selectedId);
         //       value=radioButton.getText().toString();
                Toast.makeText(getActivity(), selectedId+"", Toast.LENGTH_SHORT).show();
                setData();
                Intent intent = new Intent(getContext(), BuyAndSell.class);
                intent.putExtra("msg", "openMyBook");
                startActivity(intent);
            }
            //  Toast.makeText(getContext(), "scjhddjc", Toast.LENGTH_SHORT).show();

        });

        return view;


    }

    private void setData() {
       // Toast.makeText(getActivity(), "sdfed", Toast.LENGTH_SHORT).show();
        String price=actualPrice.getText().toString();
     //   value=radioButton.getText().toString();
        BuySellSubject b=new BuySellSubject();
       // b.setBookPrice(price);
        b.setBookName(bookName);
        b.setAuthorName(authorName);
        b.setBookCondition(description);
       // b.setBookCondition(value);

        if(helper.saveMySellBook(b))
        {
            actualPrice.setText("");
        }


    }

}
