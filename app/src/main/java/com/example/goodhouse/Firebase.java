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

public class Firebase {
    static HashMap<String, Object> noise = new HashMap<>();
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

    public void Complaint(int other_room, String content) { //민원 접수 시
        int result = CheckComplaint(other_room); //상대방 집에서 소음 발생 여부 확인
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-dd-MM HH:mm:ss");
        String time = formatter.format(date);
        dataComplaint fcomplaint = new dataComplaint(other_room, time, content, result);

        databaseReference.child(Integer.toString(address)).child(Integer.toString(other_room)).child("fileComplaint").push().setValue(fcomplaint); //db에 민원 접수내역 저장
        if(result == 1) {
            result = 2;
            dataComplaint rcomplaint = new dataComplaint(room, time,content,result);
            databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).child("getComplaint").push().setValue(rcomplaint); //db에 상대방 집에 민원 저장
        }
    }

    public int CheckComplaint(int other_room) { //소음 발생 여부 확인
        return 1;
    }

    public void putNoise(int address, int room) { //list 가져온 후 값 추가하기
        HashMap noise = new HashMap<>();
        noise = NoiseSet(noise);
        databaseReference.child(Integer.toString(address)).child(Integer.toString(room)).child("noise").setValue(noise);
    }

    public HashMap NoiseSet(HashMap noise) { //db로부터 값 가져오기 (현재는 랜덤으로 입력)
        for(int i=1;i<13;i++) {
            noise.put(Integer.toString(i),Integer.toString((int)((Math.random()*10000)%100)));
        }
        return noise;
    }

    public void CalculateScore(int addresss, int room) { //매너지수 계산

    }
}
