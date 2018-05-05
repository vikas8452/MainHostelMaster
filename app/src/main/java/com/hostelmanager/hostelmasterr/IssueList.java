package com.hostelmanager.hostelmasterr;

import android.app.Activity;
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
