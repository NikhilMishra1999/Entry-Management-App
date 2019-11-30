package com.example.nikhil.ems;

/**
 * Created by Nikhil on 11/30/2019.
 */

public class Datain {

    String checkin;
    String userid;

    public Datain(){

    }

    public Datain(String userid,String checkin)
    {
        this.userid = userid;
        this.checkin = checkin;
    }

    public String getUserid() {
        return userid;
    }
    public String getCheckin() {
        return checkin;
    }
}
