package com.example.blokus;

import android.content.res.Resources;
import android.support.constraint.ConstraintLayout;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;

public class BigField {
    private Field [][] fields = new Field[11][11];

    BigField(){
        for(int i = 0; i < 11; i++){
            for(int j=0; j < 11; j++){
                fields[i][j] = new Field(i, j);
            }
        }
    }

    public Field searchField(float x, float y){
        if(y > fields[10][10].getTopLeftY() + 200) return null;
        for(int i = 0; i <= 10; i++){
            if(i == 10 || (x >= fields[i][0].getTopLeftX() && x < fields[i+1][0].getTopLeftX())){
                for(int j=0; j <= 10; j++){
                    if(j == 10 || y >= fields[0][j].getTopLeftY() && y < fields[0][j+1].getTopLeftY()) return fields[i][j];
                }
            }
        }
        return null;
    }

    public void setEmpty(Field field){
        field.isEmpty = false;

    }
    public void setEmpty(int x, int y){
        fields[x][y].isEmpty = false;

    }
}
