package com.example.goodhouse;

public class Complaint {
    //System.currenttime형식으로 받기때문에 long
    String time;
    String complaintKind;

    Complaint(String time, String complaintKind) {
        this.time = time;
        this.complaintKind = complaintKind;
    }
}