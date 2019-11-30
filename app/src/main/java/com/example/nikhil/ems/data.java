package com.example.nikhil.ems;

/**
 * Created by Nikhil on 11/15/2019.
 */

public class data {

    String nam;
    String em;
    String pass;
    String pn;

    public data(){

    }

    public data(String nam, String em, String pass, String pn){
        this.nam = nam;
        this.em = em;
        this.pass = pass;
        this.pn = pn;
    }

    public String getNam() {
        return nam;
    }

    public String getEm() {
        return em;
    }

    public String getPass() {
        return pass;
    }

    public String getPn() {
        return pn;
    }
}
