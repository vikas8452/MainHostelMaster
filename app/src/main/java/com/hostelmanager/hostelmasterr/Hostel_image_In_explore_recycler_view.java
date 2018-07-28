package com.hostelmanager.hostelmasterr;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 3/14/2018.
 */


public class Hostel_image_In_explore_recycler_view extends RecyclerView.Adapter<Hostel_image_In_explore_recycler_view.ViewHolder> {
 private ArrayList<String> hostelImages=new ArrayList<>();
 private Context context;

    public Hostel_image_In_explore_recycler_view(Context context, ArrayList<String> hostelImages) {

        this.hostelImages = hostelImages;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_hostel_image,parent,false);
        //dataList.add(R.drawable.ic_launcher_background);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        Log.d("check",hostelImages.get(0));


   Picasso.with(context).load(hostelImages.get(position)).into(holder.hostelImage);

    }

    @Override
    public int getItemCount() {
        return hostelImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

       ImageView hostelImage;
       // TextView facility;

        public ViewHolder(View itemView) {
            super(itemView);
           hostelImage=itemView.findViewById(R.id.hostelpicList1);


           //dataList.add(R.drawable.ic_launcher_background);
           // facility=itemView.findViewById(R.id.facility);
        }
    }
}
