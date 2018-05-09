package com.hostelmanager.hostelmasterr;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hostelmanager.hostelmasterr.Model.BuySellSubject;
import com.hostelmanager.hostelmasterr.helper.FireBaseHelper;

import java.util.ArrayList;


public class YourBooks extends Fragment {

    DatabaseReference db;
    //RecyclerView grid;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    CustomAdapterForBooksSellAndBuy adapter;
    //CustomAdapterForSB adapter;
    GridView gridViewForBooks;
    ArrayList<BuySellSubject> buySellSubjects=new ArrayList<BuySellSubject>();
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
      //  grid=view.findViewById(R.id.grid);
        firebaseAuth=FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser();
        gridViewForBooks=view.findViewById(R.id.grid);
        db= FirebaseDatabase.getInstance().getReference("BuyAndSell").child("MyBooksForSell");
        db.keepSynced(true);
     //   helper=new FireBaseHelper(db);

        adapter=new CustomAdapterForBooksSellAndBuy(getActivity().getBaseContext(),retrieve(db));
        gridViewForBooks.setAdapter(adapter);

        return view;
    }


    private void fetchData(DataSnapshot dataSnapshot)
    {
        buySellSubjects.clear();

        for (DataSnapshot ds : dataSnapshot.getChildren())
        {
            Log.d("ds","Hell");
            try {
                BuySellSubject spacecraft = ds.getValue(BuySellSubject.class);
                buySellSubjects.add(spacecraft);
               // Toast.makeText(getActivity(), buySellSubjects.get(0).getBookName() + "", Toast.LENGTH_SHORT).show();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    //READ BY HOOKING ONTO DATABASE OPERATION CALLBACKS
    public ArrayList<BuySellSubject> retrieve(DatabaseReference db)
    {
        Log.d("sdsd","Entered in the listener");
        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Log.d("sdsd","Calling Fetvch data");
                fetchData(dataSnapshot);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
              fetchData(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return  buySellSubjects;
    }

/*
    @Override
    public void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<BuySellSubject,BuySellSubjectViewHolder> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<BuySellSubject, BuySellSubjectViewHolder>(BuySellSubject.class,R.layout.book_description_model, BuySellSubjectViewHolder.class,db) {
            @Override
            public BuySellSubjectViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull BuySellSubjectViewHolder holder, int position, @NonNull BuySellSubject model) {

            }
        };
        gridViewForBooks.setAdapter(firebaseRecyclerAdapter);

    }

    public static class BuySellSubjectViewHolder extends RecyclerView.ViewHolder {
.        public BuySellSubjectViewHolder(View itemView) {
            super(itemView);
            mView=itemView;
        }
    }*/
}
