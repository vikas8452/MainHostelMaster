package com.hostelmanager.hostelmaster;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by dell on 3/4/2018.
 */

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {

    Context context;
    ArrayList<String> hostelNameList;
    ArrayList<String> hostelPicList;
    ArrayList<String> ratingList;
    ArrayList<String> ratingCount;
    double longitude;
    double latitude;

    public SearchAdapter(Context context, ArrayList<String> hostelNameList, ArrayList<String> hostelPicList,ArrayList<String> ratingList,ArrayList<String> rating_Count,double latitude,double longitude) {
        this.context = context;
        this.hostelNameList = hostelNameList;
        this.hostelPicList = hostelPicList;
        this.ratingList = ratingList;
        this.ratingCount = rating_Count;
        this.latitude=latitude;
        this.longitude=longitude;

    }

    class SearchViewHolder extends RecyclerView.ViewHolder {
        TextView hostelName;
        ImageView hostelPic;
        RelativeLayout parentLayout;
        LinearLayout rating_Layout;
        TextView rating_Description;
        TextView rating_Count;

        public SearchViewHolder(View itemView) {
            super(itemView);
            hostelName=itemView.findViewById(R.id.hostelName);
           hostelPic=itemView.findViewById(R.id.hostelPic);
           parentLayout=itemView.findViewById(R.id.parentlayout);
            rating_Layout=itemView.findViewById(R.id.rating_Layout);
            rating_Description=itemView.findViewById(R.id.rating_Description);
             rating_Count=itemView.findViewById(R.id.rating_Count);

        }
    }

    @Override
    public SearchAdapter.SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.list_layout,parent,false);
        return new SearchAdapter.SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, final int position) {

        // Write a message to the database
     //   FirebaseDatabase database = FirebaseDatabase.getInstance();
     //   DatabaseReference myRef = database.getReference();

        //myRef.setValue("Hello, World!");



 holder.hostelName.setText(hostelNameList.get(position));
        Picasso.with(context).load(hostelPicList.get(position)).into(holder.hostelPic);
        LatLng from = new LatLng(latitude,longitude);
        LatLng to = new LatLng(28.456957,77.497378);

        double dist= SphericalUtil.computeDistanceBetween(from,to);

       // System.out.print(dist);
      //  holder.rating_Description.setText(ratingList.get(position));
        holder.rating_Description.setText(Double.toString(dist));
        holder.rating_Count.setText(ratingCount.get(position));
       // Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d(TAG,"onClick: clicked on:"+hostelNameList.get(position));
                Toast.makeText(context, hostelNameList.get(position), Toast.LENGTH_SHORT).show();

                Intent intent =new Intent(context,GalleryActivity.class);
                intent.putExtra("image_url" ,hostelPicList.get(position));
                intent.putExtra("hostel_name" ,hostelNameList.get(position));
                intent.putExtra("rating_Description" ,ratingList.get(position));
               intent.putExtra("rating_Count" ,ratingCount.get(position));
                context.startActivity(intent);
            }
        });

        holder.rating_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "Rating", Toast.LENGTH_SHORT).show();

               /* Intent intent =new Intent(context,GalleryActivity.class);

                intent.putExtra("image_url" ,hostelPicList.get(position));
                intent.putExtra("hostel_name" ,hostelNameList.get(position));
                context.startActivity(intent);*/

            }
        });

    }


    @Override
    public int getItemCount() {
        return hostelNameList.size();
    }
}
