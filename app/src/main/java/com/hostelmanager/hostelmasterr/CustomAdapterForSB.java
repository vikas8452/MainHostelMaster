package com.hostelmanager.hostelmasterr;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

//import com.tutorials.hp.firebasegridviewmulti_items.m_Model.Spacecraft;

import com.hostelmanager.hostelmasterr.Model.BuySellSubject;

import java.util.ArrayList;

/**
 * Created by Oclemy on 6/21/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 * 1. where WE INFLATE OUR MODEL LAYOUT INTO VIEW ITEM
 * 2. THEN BIND DATA
 */
public class CustomAdapterForSB extends BaseAdapter {
    Context c;
    ArrayList<BuySellSubject> spacecrafts;

    public CustomAdapterForSB(Context c, ArrayList<BuySellSubject> spacecrafts) {
        this.c = c;
        this.spacecrafts = spacecrafts;
    }

    @Override
    public int getCount() {
        return spacecrafts.size();
    }

    @Override
    public Object getItem(int position) {
        return spacecrafts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null)
        {
            convertView= LayoutInflater.from(c).inflate(R.layout.book_description_model,parent,false);
        }

       TextView nameTxt= (TextView) convertView.findViewById(R.id.bookNameInAdapter);
        TextView propTxt= (TextView) convertView.findViewById(R.id.authorNameInAdapter);
       // TextView descTxt= (TextView) convertView.findViewById(R.id.descTxt);

        final BuySellSubject s= (BuySellSubject) this.getItem(position);

        nameTxt.setText(s.getBookName());
        propTxt.setText(s.getAuthorName());
       // descTxt.setText(s.getDescription());

        //ONITECLICK
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(c,s.getBookName(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}
