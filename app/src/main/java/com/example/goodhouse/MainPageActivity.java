package com.example.goodhouse;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class

MainPageActivity extends AppCompatActivity {
    Firebase firebase = new Firebase();
    private LineChart chart;
    private Button complaintButton, viewmoreBtn;
    private Dialog dialog;
    private TextView welcome;
    private int[] dbArr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbArr = getDB();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        chart = findViewById(R.id.linechart);
        viewmoreBtn = findViewById(R.id.viewMore);
        complaintButton = findViewById(R.id.complaintBtn);
        dialog = new Dialog(MainPageActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.complaintdialog);

        viewmoreBtn.setOnClickListener(v->{
            Intent intent = new Intent(getApplicationContext(), ViewMoreActivity.class);
            startActivity(intent);
        });


        complaintButton.setOnClickListener(view ->{
            showDialog();
        } );

        ArrayList<Entry> values = getVal();

        getInforms();

        LineDataSet set1;
        set1 = new LineDataSet(values, "dB");

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1); // add the data sets

        // create a data object with the data sets
        LineData data = new LineData(dataSets);

        // black lines and points
        set1.setDrawCircleHole(false);
        set1.setDrawCircles(false);
        set1.setColor(Color.BLACK);
        //set1.setCircleColor(Color.BLACK);
        set1.setDrawFilled(true); // 차트 아래 fill(채우기) 설정
        set1.setFillColor(Color.BLACK); // 차트 아래 채우기 색 설정

        // set data
        chart.setData(data);
    }
    ArrayList<Entry> getVal() {
        ArrayList<Entry> values = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            float val = (float) dbArr[i];
            values.add(new Entry(i, val));
        }
        return values;
    }

    @SuppressLint("SetTextI18n")
    public void getInforms() {
        TextView scoreText = (TextView) findViewById(R.id.score);
        TextView complText = (TextView) findViewById(R.id.complain);

        Log.d("aaa",""+Firebase.score+":"+Firebase.getList.size());
        scoreText.setText(Firebase.score +"\n/100");
        complText.setText(Firebase.getList.size()+"\n/월");
    }

    public void showDialog(){
        dialog.show();

        Button btnComplaint = dialog.findViewById(R.id.complaintButtonInDialog);
        btnComplaint.setOnClickListener(v->{
            //통신 부분 미구현
            if(sendComplaint()) {
                Toast complaintToast = Toast.makeText(this.getApplicationContext(),
                        "신고가 완료되었습니다.", Toast.LENGTH_SHORT);
                complaintToast.show();
            }
            dialog.dismiss();
        });
    }
    public boolean sendComplaint(){
        return true;
    }
    public int[] getDB(){
        //이 함수로 데이터 받기(시간 단위)
        int [] list = new int[12];
        for(int i=0;i<12;i++) {
            list[i] = Integer.parseInt(Firebase.noise.get(Integer.toString(i+1)).toString());
        }
        Log.d("aaa",""+list);
        return list;
    }
}
