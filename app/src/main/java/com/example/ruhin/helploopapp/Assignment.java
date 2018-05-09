package com.example.ruhin.helploopapp;

/**
 * Created by Ruhin on 5/8/2018.
 */

public class Assignment {
    private String assignmentInfo;
    private String assignmentClass;
    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Assignment(String assignmentInfo, String assignmentClass, String date) {
        this.assignmentInfo = assignmentInfo;
        this.assignmentClass = assignmentClass;
        this.date = date;
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
