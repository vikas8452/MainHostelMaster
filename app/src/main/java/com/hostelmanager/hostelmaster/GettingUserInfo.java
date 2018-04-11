package com.hostelmanager.hostelmaster;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sudha on 26-Jan-18.
 */

public class GettingUserInfo extends ArrayAdapter {

    private Activity context;
    private List<UserInfo> userinfo;

    public GettingUserInfo(Activity context, List<UserInfo> userinfo){
        super(context, R.layout.fragment_profiler,userinfo);
        this.context = context;
        this.userinfo = userinfo;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View userinfor = inflater.inflate(R.layout.fragment_profiler,null,true);

        TextView name=userinfor.findViewById(R.id.profileUserName);
        TextView mob=userinfor.findViewById(R.id.profileUserMobile);

        return userinfor;
    }
}
