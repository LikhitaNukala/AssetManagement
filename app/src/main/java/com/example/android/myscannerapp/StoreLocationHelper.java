package com.example.android.myscannerapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Likhita on 19-06-2017.
 */

public class StoreLocationHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="location.db";
    public static final int DATABASE_VERSION=1;

    public StoreLocationHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + LocationContract.LocationEntry.TABLE_NAME + "(" +
                LocationContract.LocationEntry.TIME + " TIME, " +
                LocationContract.LocationEntry.LATITUDE + " LONG, " +
                LocationContract.LocationEntry.LONGITUDE + " LONG)"         );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + LocationContract.LocationEntry.TABLE_NAME);
        onCreate(db);

    }
}
