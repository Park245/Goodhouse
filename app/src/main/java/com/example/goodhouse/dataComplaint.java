package com.example.goodhouse;

import android.util.Log;

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

    public int getOther_room() {
        return other_room;
    }

    public void setOther_room() {
        this.other_room = other_room;
    }

    public String toString() {
        Log.d("abc","to-string"+other_room+time);
        return other_room+","+time+","+content+","+result;
    }

    dataComplaint(int other_room, String time, String content, int result) {
        this.other_room = other_room;
        this.time = time;
        this.content = content;
        this.result = result;
    }
}
