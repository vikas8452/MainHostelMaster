package com.hostelmanager.hostelmasterr.Model;

/**
 * Created by sudha on 07-May-18.
 */

public class ConfStatus {
    String mobile;
    String stat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public ConfStatus(String mobile, String stat,String name) {

        this.mobile = mobile;
        this.stat = stat;
        this.name = name;
    }

    public ConfStatus() {

    }
}
