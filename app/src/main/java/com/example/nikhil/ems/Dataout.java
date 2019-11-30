package com.example.nikhil.ems;

/**
 * Created by Nikhil on 11/30/2019.
 */

public class Dataout {

    String checkout;
    String userid;

    public Dataout(){

    }

    public Dataout(String userid, String checkout){
        this.userid = userid;
        this.checkout = checkout;
    }

    public String getUserid() {
        return userid;
    }
    public String getCheckout() {
        return checkout;
    }
}
