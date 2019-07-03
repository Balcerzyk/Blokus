package com.example.blokus;

import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

public class Field {
    private int positionX;
    private int positionY;
    private float topLeftX;
    private float topLeftY;
    private float height;
    private float width;

    Field(int x, int y){
        positionX = x;
        positionY = y;

        height = 30;
        width = 30;

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        topLeftX = x * height * displayMetrics.density;
        topLeftY = y * width * displayMetrics.density;
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

    public float getHeight() {
        return height;
    }

    public float getWidth() {
        return width;
    }
}
