package com.example.android.myscannerapp;

import java.util.List;

/**
 * Created by Likhita on 04-07-2017.
 */

public class Sim {
    private String assetId;
    private List<SimDetails> simDetails;

    public String getAssetId() {
        return assetId;
    }

    public void setAssetId(String value){

        this.assetId=value;
    }

    public List<SimDetails> getSimDetailsList() {
        return simDetails;
    }

    public void setSimDetailsList(List<SimDetails> values){
        this.simDetails=values;

    }

}
