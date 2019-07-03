package com.example.blokus;

public class Player {

    public String color;
    private Block []playerBlocks;

    Player(String col){
        color = col;
        playerBlocks[0] = new Block("block0", 1, "00");
    }
}
