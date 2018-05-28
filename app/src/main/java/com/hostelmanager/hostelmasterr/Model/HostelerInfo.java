package com.hostelmanager.hostelmasterr.Model;

/**
 * Created by sudha on 06-May-18.
 */

public class HostelerInfo {

    String name,hostel,roomno,college,mobile,year,luid;

    public String getLuid() {
        return luid;
    }

    public void setLuid(String luid) {
        this.luid = luid;
    }

    public HostelerInfo() {
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public HostelerInfo(String name, String hostel, String roomno, String college, String mobile, String year,String luid) {
        this.name = name;
        this.hostel = hostel;
        this.roomno = roomno;
        this.college = college;
        this.mobile = mobile;
        this.year = year;
        this.luid = luid;
    }

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHostel() {
        return hostel;
    }

    public void setHostel(String hostel) {
        this.hostel = hostel;
    }

    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
