package com.example.android.myscannerapp;

import android.provider.BaseColumns;

/**
 * Created by Likhita on 19-06-2017.
 */

public class LocationContract {
    public static final class LocationEntry implements BaseColumns{
        public static final String TABLE_NAME="locations";
        public static final String TIME="time";
        public static final String LATITUDE="latitude";
        public static final String LONGITUDE="longitude";
    }
}
