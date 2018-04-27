package com.hostelmanager.hostelmaster;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hostelmanager.hostelmaster.helper.FireBaseHelper;


public class YourBooks extends Fragment {

    DatabaseReference db;
    CustomAdapterForBooksSellAndBuy adapter;
    GridView gridViewForBooks;
    FireBaseHelper helper;
    public YourBooks() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view= inflater.inflate(R.layout.fragment_your_books, container, false);

      gridViewForBooks=view.findViewById(R.id.grid);
        db= FirebaseDatabase.getInstance().getReference();
        helper=new FireBaseHelper(db);

        //Adapter
         adapter=new CustomAdapterForBooksSellAndBuy(getActivity(),helper.getBuySellSubjects());

     //   Toast.makeText(getActivity(), "vkjhbnmbnm", Toast.LENGTH_SHORT).show();
            gridViewForBooks.setAdapter(adapter);
       // return null;
        return view;
    }


}
