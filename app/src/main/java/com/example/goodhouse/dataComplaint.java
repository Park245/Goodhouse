package com.example.goodhouse;

public class dataComplaint {
    String time, content;
    int result, other_room;

    public dataComplaint() {}

    public String getTime() {
        return time;
    }

    public void setTime() {
        this.time = time;
    }

    public String getContent(){
        return content;
    }

    public void setContent() {
        this.content = content;
    }

    public int getResult() {
        return result;
    }

    public void setResult() {
        this.result = result;
    }

    dataComplaint(int other_room, String time, String content, int result) {
        this.other_room = other_room;
        this.time = time;
        this.content = content;
        this.result = result;
    }
}
