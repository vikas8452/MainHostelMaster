package com.hostelmanager.hostelmaster;

import android.content.Intent;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;

import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class finalBookDetails extends AppCompatActivity {


    @BindView(R.id.submitBook)
    Button submitDetails;
@BindView(R.id.bookImageInContent)
    ImageView bookImageInContent;
@BindView(R.id.bookNameInContent)
    TextInputEditText bookNameInContent;
@BindView(R.id.authorNameInContent)
    TextInputEditText authorNameInContent;
@BindView(R.id.descriptionInContent)
    EditText descriptionInContent;



//BottomSheetBehavior sheetBehavior;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_final_book_details);
        ButterKnife.bind(this);

        // sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

    /*     sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
           @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                        //btnBottomSheet.setText("Close Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                      //  btnBottomSheet.setText("Expand Sheet");
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });
*/
        //@OnClick(R.id.submitBook)

        submitDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BottomSheetFragment bottomSheetFragment = new BottomSheetFragment();
                Bundle bundle=new Bundle();
                bundle.putString("bookName",bookNameInContent.getText().toString());
                bundle.putString("authorName",authorNameInContent.getText().toString());
                bundle.putString("description",descriptionInContent.getText().toString());
                bottomSheetFragment.setArguments(bundle);
                bottomSheetFragment.show(getSupportFragmentManager(), bottomSheetFragment.getTag());
            }



        });


    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        //transaction=getSupportFragmentManager().executePendingTransactions();
        transaction.add(R.id.frame_container,fragment);
        //  transaction.addToBackStack(null);
        transaction.commit();
    }
}
