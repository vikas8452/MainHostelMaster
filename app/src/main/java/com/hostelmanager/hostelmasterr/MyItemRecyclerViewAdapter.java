package com.hostelmanager.hostelmasterr;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hostelmanager.hostelmasterr.Model.SendRecieveIssues;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a and makes a call to the
 * TODO: Replace the implementation with code for your data type.
 */
public class MyItemRecyclerViewAdapter extends BaseAdapter {

    private final List<SendRecieveIssues> sendRecieveIssues;
    private final Context context;


    public MyItemRecyclerViewAdapter(Context contex, ArrayList<SendRecieveIssues> items) {
        sendRecieveIssues = items;
        context = contex;
    }

    @Override
    public int getCount() {
        return sendRecieveIssues.size();
    }

    @Override
    public Object getItem(int position) {
        return sendRecieveIssues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Log.d("dfdad","Enetred in getView");

        if(view==null)
            view=LayoutInflater.from(context).inflate(R.layout.issues_list_layout,parent,false);

        TextView tv2 = view.findViewById(R.id.issuetype);
        TextView tv3 = view.findViewById(R.id.issuedescription);
        ImageView iv = view.findViewById(R.id.status);
        LinearLayout linearLayout = view.findViewById(R.id.linearLayout);

        SendRecieveIssues sendRecieveIssues = (SendRecieveIssues) getItem(position);

        String msg ="";
        if(sendRecieveIssues.getStatus().equals("0")) {
            iv.setImageResource(R.drawable.excla);
            msg = "Issue sent Successfully";
        }
        if(sendRecieveIssues.getStatus().equals("1")) {
            iv.setImageResource(R.drawable.sintic);
            msg = "Problem will be resolved soon";
        }
        if(sendRecieveIssues.getStatus().equals("2")) {
            iv.setImageResource(R.drawable.seen);
            msg = "Problem has been solved";
        }
        tv2.setText(sendRecieveIssues.getType());
        tv3.setText(sendRecieveIssues.getDescription());

        final String finalMsg = msg;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, finalMsg,Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    /*public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tfroomno,tfissuetype,tfissuedesc;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tfroomno = (TextView) view.findViewById(R.id.roomno);
            tfissuetype = (TextView) view.findViewById(R.id.issuetype);
            tfissuedesc = (TextView) view.findViewById(R.id.issuedescription);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tfroomno.getText() + "'"+ " '" + tfissuetype.getText() + "'"+ " '" + tfissuedesc.getText() + "'";
        }
    }*/
}
