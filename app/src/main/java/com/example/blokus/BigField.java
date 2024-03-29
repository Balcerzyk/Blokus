package com.example.blokus;


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

    public void setEmpty(int x, int y, String playerColor){
        fields[x][y].isEmpty = false;
        fields[x][y].color = playerColor;
    }
    public boolean checkEmpty(int x, int y){
        if(fields[x][y] == null) return false;
        return fields[x][y].isEmpty;

    }
    public Field getFields(int x, int y){
        return fields[x][y];
    }
}
