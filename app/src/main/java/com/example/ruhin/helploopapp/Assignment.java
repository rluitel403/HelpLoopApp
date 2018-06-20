package com.example.ruhin.helploopapp;

/**
 * Created by Rubin
 * version: 3
 * this is a class that represents the informations about an assignment
 */

public class Assignment {
    private String assignmentInfo;
    private String assignmentClass;
    private String date;
    private String details;

    public Assignment(){

    }
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Assignment(String assignmentInfo, String assignmentClass, String date, String details) {
        this.assignmentInfo = assignmentInfo;
        this.assignmentClass = assignmentClass;
        this.details = details;
        this.date = date;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getAssignmentInfo() {
        return assignmentInfo;
    }

    public void setAssignmentInfo(String assignmentInfo) {
        this.assignmentInfo = assignmentInfo;
    }

    public String getAssignmentClass() {
        return assignmentClass;
    }

    public void setAssignmentClass(String assignmentClass) {
        this.assignmentClass = assignmentClass;
    }

}
