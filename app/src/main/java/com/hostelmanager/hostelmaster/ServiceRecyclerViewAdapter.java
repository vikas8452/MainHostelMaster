package com.hostelmanager.hostelmaster;

import android.content.Context;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by dell on 3/14/2018.
 */

public class ServiceRecyclerViewAdapter extends RecyclerView.Adapter<ServiceRecyclerViewAdapter.ViewHolder> {
 private ArrayList<String> iconsList=new ArrayList<>();
 private ArrayList<String> iconsName=new ArrayList<>();//private ArrayList<Integer> dataList=new ArrayList<>();
 private Context context;

    public ServiceRecyclerViewAdapter( Context context,ArrayList<String> iconsList,ArrayList<String> iconsName) {
        this.iconsList = iconsList;
        this.iconsName = iconsName;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem,parent,false);
        //dataList.add(R.drawable.ic_launcher_background);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

      //  Resources res = getResources(); /** from an Activity */
      //  image.setImageDrawable(res.getDrawable(R.drawable.myimage));
      //  holder.circleImage.setImageResource(dataList.get(position));

        Picasso.with(context).load(iconsList.get(position)).into(holder.circleImage);
      //  Picasso.with(context).load("https://beebom-redkapmedia.netdna-ssl.com/wp-content/uploads/2016/01/Reverse-Image-Search-Engines-Apps-And-Its-Uses-2016.jpg").into(holder.circleImage);
      //  Toast.makeText(context,iconsList.get(position) , Toast.LENGTH_SHORT).show();
      //  holder.facility.setText(iconsName.get(position));
        holder.circleImage.setOnClickListener(new View.OnClickListener() {
            Toast toast;
            @Override
            public void onClick(View view) {
               final Toast toast = Toast.makeText(context,iconsName.get(position) ,Toast.LENGTH_SHORT);
                toast.show();
                new CountDownTimer(1000, 1000)
                {
                    public void onTick(long millisUntilFinished) {toast.show();}
                    public void onFinish() {toast.cancel();}
                }.start();
               // Toast.makeText(context, iconsName.get(position), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return iconsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {

        CircleImageView circleImage;
        TextView facility;

        public ViewHolder(View itemView) {
            super(itemView);
            circleImage=itemView.findViewById(R.id.circleImage);


            //dataList.add(R.drawable.ic_launcher_background);
           // facility=itemView.findViewById(R.id.facility);
        }
    }
}
