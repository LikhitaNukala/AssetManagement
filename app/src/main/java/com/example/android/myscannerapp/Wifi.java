package com.example.android.myscannerapp;

import java.util.List;

/**
 * Created by Likhita on 06-07-2017.
 */

public class Wifi {
    private String assetId;
    private List<WifiDetails> wifiDetailsList;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String assetId) {
        this.assetId = assetId;
    }

    public List<WifiDetails> getWifiDetailsList() {
        return wifiDetailsList;
    }

    public void setWifiDetailsList(List<WifiDetails> wifiDetailsList) {
        this.wifiDetailsList = wifiDetailsList;
    }
}
