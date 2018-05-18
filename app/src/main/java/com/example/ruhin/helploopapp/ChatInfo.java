package com.example.ruhin.helploopapp;

/**
 * Created by rubin
 * version 2
 * this class represents the chat messages
 */

public class ChatInfo {
    private String name;
    private String mssg;
    private String timeStamp;
    public ChatInfo(){

    }
    public ChatInfo(String mssg, String name, String timeStamp){
        this.mssg = mssg;
        this.name = name;
        this.timeStamp = timeStamp;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMssg() {
        return mssg;
    }

    public void setMssg(String mssg) {
        this.mssg = mssg;
    }
}
