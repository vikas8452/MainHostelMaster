package com.hostelmanager.hostelmasterr.Model;

/**
 * Created by sudha on 07-May-18.
 */

public class ConfStatus {
    String mobile;
    String stat;
    String room;
    String name;

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public ConfStatus(String mobile, String stat,String name,String room) {

        this.mobile = mobile;
        this.stat = stat;
        this.name = name;
        this.room = room;
    }

    public ConfStatus() {

    }
}
