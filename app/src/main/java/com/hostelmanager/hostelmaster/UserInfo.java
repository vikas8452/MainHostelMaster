package com.hostelmanager.hostelmaster;

/**
 * Created by sudha on 09-Jan-18.
 */

public class UserInfo {

    String issuetype , desc;
    String fname,mob,email,pass;
    String name;
    String dob,country;
    String gender;
    String mobile1;
    String mobile2;
    String address;
    String father;
    String mother;
    String college;
    String guardian;
    String guardianmobile;

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public UserInfo(){

    }


    public UserInfo(String name, String dob, String gender, String country, String mobile1, String mobile2, String address, String father, String mother, String college, String guardian, String guardianmobile) {
        this.name = name;
        this.dob = dob;
        this.country = country;
        this.gender = gender;
        this.mobile1 = mobile1;
        this.mobile2 = mobile2;
        this.address = address;
        this.father = father;
        this.mother = mother;
        this.college = college;
        this.guardian = guardian;
        this.guardianmobile = guardianmobile;
    }

    public UserInfo(String fname, String mobile1, String email, String pass ) {
        this.fname = fname;
        this.mob = mobile1;
        this.email = email;
        this.pass = pass;
    }

    public UserInfo(String issuetype,String desc){
        this.issuetype = issuetype;
        this.desc = desc;
    }



    public String getIssuetype() {
        return issuetype;
    }

    public void setIssuetype(String issuetype) {
        this.issuetype = issuetype;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
