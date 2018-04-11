package com.hostelmanager.hostelmaster;

/**
 * Created by dell on 1/31/2018.
 */

public class Get_no {



    String AMITY,GNIOT,GCET;

    public Get_no(String AMITY, String GNIOT, String GCET) {
        this.AMITY = AMITY;
        this.GNIOT = GNIOT;
        this.GCET = GCET;
    }

    public Get_no() {

    }

    public String getAMITY() {
        return AMITY;
    }

    public void setAMITY(String AMITY) {
        this.AMITY = AMITY;
    }

    public String getGNIOT() {
        return GNIOT;
    }

    public void setGNIOT(String GNIOT) {
        this.GNIOT = GNIOT;
    }

    public String getGCET() {
        return GCET;
    }

    public void setGCET(String GCET) {
        this.GCET = GCET;
    }
}
