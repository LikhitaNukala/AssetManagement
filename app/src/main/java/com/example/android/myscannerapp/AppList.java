package com.example.android.myscannerapp;

import android.graphics.drawable.Drawable;

/**
 * Created by Likhita on 21-06-2017.
 */

public class AppList {
    private String name;
    Drawable icon;

    public AppList(String name, Drawable icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public Drawable getIcon() {
        return icon;
    }
}
