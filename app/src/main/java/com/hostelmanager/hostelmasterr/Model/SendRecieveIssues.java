package com.hostelmanager.hostelmasterr.Model;


public class SendRecieveIssues {

    String type;
    String description;
    String mobile;
    String status;
    String roomno;
    String uid;
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }



    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public SendRecieveIssues(){

    }

    public SendRecieveIssues(String type, String description, String mobile, String status,String roomno,String uid) {
        this.type = type;
        this.description = description;
        this.mobile = mobile;
        this.status = status;
        this.roomno = roomno;
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
