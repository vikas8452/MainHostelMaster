package com.hostelmanager.hostelmaster;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

/**
 * Created by sudha on 01-Feb-18.
 */

public class IssueList extends ArrayAdapter<UserInfo>{
    private Activity context;
    private List<UserInfo> UserInfo;

    public IssueList(Activity context, List<UserInfo> UserInfo){
        super(context,R.layout.listlayout,UserInfo);
        this.context = context;
        this.UserInfo = UserInfo;
    }
}
