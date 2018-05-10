package com.hostelmanager.hostelmasterr.Model;

/**
 * Created by sudha on 07-May-18.
 */

public class GetHostelByUID {

    String uid,hname;

    public GetHostelByUID(String uid, String hname) {
        this.uid = uid;
        this.hname = hname;
    }

    public GetHostelByUID() {
    }

    public String getUid() {

        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getHname() {
        return hname;
    }

    public void setHname(String hname) {
        this.hname = hname;
    }
}
