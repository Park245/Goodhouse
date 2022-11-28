package com.example.goodhouse;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Firebase {
    static HashMap<String, Object> noise = new HashMap<>();
    static HashMap<String, Object> week_noise = new HashMap<>();
    static HashMap<String, Object> month_noise = new HashMap<>();
    static List<Object> fileList = new ArrayList<>();
    static List<Object> getList = new ArrayList<>();
    static int score, address, room;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private final DatabaseReference databaseReference = database.getReference();
    public void getInfo() { //로그인 시 입력되는 주소와 호수 입력
        databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Information information = snapshot.getValue(Information.class);
                score = information.getScore();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        fileList.clear();
        databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).child("fileComplaint").addValueEventListener(new ValueEventListener() { //get fileComplaint from firebase
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    fileList.add(ds.getValue());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        getList.clear();
        databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).child("getComplaint").addValueEventListener(new ValueEventListener() { //get getComplaint from firebase
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    getList.add(ds.getValue());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).child("noise").addValueEventListener(new ValueEventListener() {  //get noise dB from firebase
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    noise.put(ds.getKey(),ds.getValue());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    public void SignIn(int log_address, int log_room) { //회원가입시, db로 데이터 전송
        address = log_address;
        room = log_room;
        databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).child("score").setValue(100);
    }

    public void LogIn(int log_address, int log_room) { //LogIn
        address = log_address;
        room = log_room;
    }

    public void Complaint(int other_room, String content) { //민원 접수 시
        int result = CheckComplaint(other_room); //상대방 집에서 소음 발생 여부 확인
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = formatter.format(date);
        dataComplaint fcomplaint = new dataComplaint(other_room, time, content, result);

        databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).child("fileComplaint").push().setValue(fcomplaint);
        if(result == 1) {
            dataComplaint gcomplaint = new dataComplaint(room, time,content, 2);
            databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).child("getComplaint").push().setValue(gcomplaint);
        }
    }

    public int CheckComplaint(int other_room) { //소음 발생 여부 확인
        return 1;
    }

    public void putNoise() { //list 가져온 후 값 추가하기
        noise = NoiseSet();
        week_noise = weekNoise();
        month_noise = monthNoise();
        databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).child("noise").setValue(noise);
        databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).child("week_noise").setValue(week_noise);
        databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).child("month_noise").setValue(month_noise);
    }

    public HashMap NoiseSet() { //db로부터 값 가져오기 (현재는 랜덤으로 입력)
        noise.clear();
        for(int i=1;i<13;i++) {
            noise.put(Integer.toString(i),Integer.toString((int)((Math.random()*10000)%100)));
        }
        return noise;
    }

    public HashMap weekNoise() { //db로부터 값 가져오기 (현재는 랜덤으로 입력)
        week_noise.clear();
        for(int i=1;i<8;i++) {
            week_noise.put(Integer.toString(i),Integer.toString((int)((Math.random()*10000)%100)));
        }
        return week_noise;
    }

    public HashMap monthNoise() { //db로부터 값 가져오기 (현재는 랜덤으로 입력)
        month_noise.clear();
        for(int i=1;i<31;i++) {
            month_noise.put(Integer.toString(i),Integer.toString((int)((Math.random()*10000)%100)));
        }
        return month_noise;
    }

    public void CalculateScore(int addresss, int room) { //매너지수 계산

    }
}
