package com.example.blokus;

public class Block {

    private String name;
    private int elements;
    private Field []fields;
    private String fieldsString;

    Block(String blockname, int number, String fi ){
        name = blockname;
        elements = number;
        fieldsString = fi;
        fields  = null;
    }
}
