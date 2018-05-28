package com.hostelmanager.hostelmasterr.Model;

/**
 * Created by sudha on 07-May-18.
 */

public class GetHostelByUID {

    String uid,hname,luid,email;

    public GetHostelByUID(String uid, String hname,String luid,String email) {
        this.uid = uid;
        this.hname = hname;
        this.luid = luid;
        this.email = email;
    }

    public String getLuid() {
        return luid;
    }

    public void setLuid(String luid) {
        this.luid = luid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
