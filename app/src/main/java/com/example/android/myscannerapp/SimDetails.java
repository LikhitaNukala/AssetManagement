package com.example.android.myscannerapp;

/**
 * Created by Likhita on 04-07-2017.
 */

public class SimDetails {

    private String deviceId;
    private String carrierName;

    public String getCreatedTime() {
        return createdTime;
    }



    private String createdTime;

    private String dataNetworkType;
    private String simState;
    private String networkRoaming;

    public String getDeviceId() {
        return deviceId;
    }


    public String getCarrierName() {
        return carrierName;
    }

    public String getDataNetworkType() {
        return dataNetworkType;
    }

    public String getSimState() {
        return simState;
    }

    public String getNetworkRoaming() {
        return networkRoaming;
    }

    public void setDeviceId(String value){
        this.deviceId=value;

    }
    public void setCarrierName(String value){
        this.carrierName=value;
    }
    public void setDataNetworkType(String value){
        this.dataNetworkType=value;
    }
    public void setSimState(String value){
        this.simState=value;
    }
    public void setNetworkRoaming(String value){
        this.networkRoaming=value;
    }


}
