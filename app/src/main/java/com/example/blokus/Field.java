package com.example.blokus;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

public class Field {
    private int positionX;
    private int positionY;
    private float topLeftX;
    private float topLeftY;

    Field(int x, int y){
        positionX = x;
        positionY = y;

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        topLeftX = x * 30 * displayMetrics.density;
        topLeftY = y * 30 * displayMetrics.density;
    }

    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public float getTopLeftX() {
        return topLeftX;
    }

    public float getTopLeftY() {
        return topLeftY;
    }
}
