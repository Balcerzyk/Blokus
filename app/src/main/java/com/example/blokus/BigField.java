package com.example.blokus;

import android.util.Log;

public class BigField {
    private Field [][] fields = new Field[11][11];;

    BigField(){
        for(int i = 0; i < 11; i++){
            for(int j=0; j < 11; j++){
                fields[i][j] = new Field(i, j);
            }
        }
    }

    public Field searchField(float x, float y){
        for(int i = 0; i < 10; i++){
            if(x >= fields[i][0].getTopLeftX() && x < fields[i+1][0].getTopLeftX()){
                for(int j=0; j < 10; j++){
                    if(y >= fields[0][j].getTopLeftY() && y < fields[0][j+1].getTopLeftY()) return fields[i][j];
                }
            }
        }
        return null;
    }
}
