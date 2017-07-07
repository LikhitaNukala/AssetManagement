package com.example.android.myscannerapp;

/**
 * Created by Likhita on 06-07-2017.
 */

public class WifiList {
    private String time;
    private String wifiname;
    public WifiList(String time,String wifiname){
        this.time=time;
        this.wifiname=wifiname;
    }
    public String getTime(){return time;}
    public String getWifiName(){return wifiname;}

}
