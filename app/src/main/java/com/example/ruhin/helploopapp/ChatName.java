package com.example.ruhin.helploopapp;

/**
 * Created by jatin
 * version 3
 * this class represents a chatname, firebase takes in objects so we made it a class
 */

public class ChatName {
    private String name;
    public ChatName(){

    }
    public ChatName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
