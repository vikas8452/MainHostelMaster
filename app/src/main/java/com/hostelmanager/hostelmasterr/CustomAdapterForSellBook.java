package com.hostelmanager.hostelmasterr;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hostelmanager.hostelmasterr.Model.BuySellSubject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterForSellBook extends BaseAdapter {

Context c;
ArrayList<BuySellSubject> buySellSubjects;
/*
    public CustomAdapterForBooksSellAndBuy(Context c, ArrayList<BuySellSubject> buySellSubjects) {
        this.c = c;
        this.buySellSubjects = buySellSubjects;
    }

    @Override
    public AdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(c).inflate(R.layout.book_description_model,parent,false);
        return new AdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterViewHolder holder, int position) {
       // holder.hostelName.setText(hostelNameList.get(position));
        Log.d("cd","Enetred in Bind View");
        final BuySellSubject buySellSubject=(BuySellSubject) buySellSubjects.get(position);
        holder.bookNameInAdapter.setText(buySellSubject.getBookName());
        holder.authorNameInAdapter.setText(buySellSubject.getAuthorName());
     //   holder.priceInAdapter.setText(buySellSubject.getBookPrice());
       // holder.bookdCondition.setText(buySellSubject.getBookCondition());
    }

    @Override
    public int getItemCount() {
        return buySellSubjects.size();
    }

    public class AdapterViewHolder extends RecyclerView.ViewHolder {

        ImageView bookImage;
        TextView bookNameInAdapter;
        TextView authorNameInAdapter;
        TextView bookdCondition;
                TextView priceInAdapter;
        public AdapterViewHolder(View view) {
            super(view);
             bookImage=view.findViewById(R.id.bookImage);
             bookNameInAdapter=view.findViewById(R.id.bookNameInAdapter);
            // authorNameInAdapter=view.findViewById(R.id.authorNameInAdapter);
          //   priceInAdapter=view.findViewById(R.id.priceInAdapter);
         //    bookdCondition=view.findViewById(R.id.conditionBook);
        }
    }

*/
    public CustomAdapterForSellBook(Context c, ArrayList<BuySellSubject> buySellSubjects) {
        this.c = c;
        this.buySellSubjects = buySellSubjects;
        Log.d("dfddf", "CustomAdapterForSellBook");
      //  Toast.makeText(c,"vhbjkjnm,.",Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getCount() {
        return buySellSubjects.size();
    }

    @Override
    public Object getItem(int i) {
        return buySellSubjects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
    Toast.makeText(c,"sds",Toast.LENGTH_SHORT).show();
        Log.d("dfdad","Enetred in getView");
        if(view==null)
           view=LayoutInflater.from(c).inflate(R.layout.book_description_model,viewGroup,false);
        Toast.makeText(c, "Layout Inflater",Toast.LENGTH_SHORT).show();
        ImageView bookImage=view.findViewById(R.id.bookImage);
        TextView bookNameInAdapter=view.findViewById(R.id.bookNameInAdapter);
        TextView authorNameInAdapter=view.findViewById(R.id.authorNameInAdapter);
       // TextView priceInAdapter=view.findViewById(R.id.priceInAdapter);
     //   TextView bookCondition=view.findViewById(R.id.conditionBook);
        final BuySellSubject buySellSubject= (BuySellSubject) this.getItem(i);
//Toast.makeText(c,buySellSubject.getBookName(),Toast.LENGTH_SHORT).show();
        //bookImage.
        Picasso.with(c).load("http://rta.org.af/eng/wp-content/uploads/2016/02/book.png").into(bookImage);
        bookNameInAdapter.setText(buySellSubject.getBookName());
        authorNameInAdapter.setText(buySellSubject.getAuthorName());
       // priceInAdapter.setText(buySellSubject.getBookPrice());
      //  bookCondition.setText(buySellSubject.getBookCondition());

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(c,buySellSubject.getBookName(),Toast.LENGTH_SHORT).show();

            }
        });

        return view;
    }
}
