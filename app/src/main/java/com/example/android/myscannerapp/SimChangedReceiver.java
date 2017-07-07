package com.example.android.myscannerapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class SimChangedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        if(intent.hasExtra("ss"))
            Toast.makeText(context,"Sim card changed",Toast.LENGTH_LONG).show();


        throw new UnsupportedOperationException("Not yet implemented");
    }
}
