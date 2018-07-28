package com.hostelmanager.hostelmasterr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

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

public class BuyBookList extends AppCompatActivity {


    DatabaseReference db;
    //RecyclerView grid;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    CustomAdapterForBooksSellAndBuy adapter;
    //CustomAdapterForSB adapter;
    GridView gridViewForBooks;
    ArrayList<BuySellSubject> buySellSubjects=new ArrayList<BuySellSubject>();
    FireBaseHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_book_list);
        firebaseAuth=FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser();
        gridViewForBooks=findViewById(R.id.gridBuyBook);

        Intent getData=getIntent();
        String department=getData.getStringExtra("get_department");
        String semester=getData.getStringExtra("get_semester");
        if(semester!=""&&semester!=null&&department!=null&& department!="")
        {
            db= FirebaseDatabase.getInstance().getReference("BuyAndSell").child("BooksForSell").child(department).child(semester);
        }
        else
        {
            Toast.makeText(this,"Please Try Again Later !",Toast.LENGTH_SHORT).show();
        }


        db.keepSynced(true);
        //   helper=new FireBaseHelper(db);

        adapter=new CustomAdapterForBooksSellAndBuy(this,retrieve(db));
        gridViewForBooks.setAdapter(adapter);


    }


    private void fetchData(DataSnapshot dataSnapshot)
    {
        //buySellSubjects.clear();

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
                Log.d("sdsd","Calling Fetch data");
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
