package com.example.blokus;

public class Player {

    public String color;
    public int points;
    public boolean [] blocks = new boolean[8];
    public boolean finish;

    Player(String col){
        color = col;
        points = 0;
        finish = false;
        for(int i=0; i<8; i++) blocks[i] = true;
    }

    public void deleteBlock(int blockId){
        blocks[blockId] = false;
        switch(blockId){
            case 0: points += 1; break;
            case 1: points += 2; break;
            case 2: points += 3; break;
            case 3: points += 4; break;
            case 4: points += 4; break;
            case 5: points += 4; break;
            case 6: points += 5; break;
            case 7: points += 5; break;
        }
    }
}
