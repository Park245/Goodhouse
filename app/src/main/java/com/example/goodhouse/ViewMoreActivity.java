package com.example.goodhouse;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;


public class ViewMoreActivity extends AppCompatActivity {
    private Chart chart;
    private int[] dbArr;
    private Button aDayBtn, aWeekBtn, aMonthBtn;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_more);
        chart = findViewById(R.id.linechart);
        dbArr = new int[]{30, 49, 20, 10, 5, 0, 0, 10, 49, 58, 11, 40, 30, 58, 20, 49, 30, 49, 20, 10, 5, 0, 0, 10, 49, 58, 11, 40, 30, 58, 20, 49, 30, 49, 20, 10, 5, 0, 0, 10, 49, 58, 11, 40, 30, 58, 20, 49};
        aDayBtn = findViewById(R.id.oneDayBtn);
        aWeekBtn = findViewById(R.id.weekBtn);
        aMonthBtn = findViewById(R.id.amonthBtn);
        makeChart(12);

        aDayBtn.setOnClickListener(v -> {
            makeChart(12);
        });
        aWeekBtn.setOnClickListener(v -> makeChart(7));
        aMonthBtn.setOnClickListener(v -> makeChart(30));

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(layoutManager);
        ComplaintAdapter adapter = new ComplaintAdapter();
        //여기 코딩 필요
        adapter.setList(getList());
        recyclerView.setAdapter(adapter);




    }
    //여기 제작해야함
    static ArrayList<Complaint> getList(){
        return new ArrayList<Complaint>();
    }

    void makeChart(int n) {
        ArrayList<Entry> values = getVal(n);
        //여기 구현 필요
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


    ArrayList<Entry> getVal(int n) { //통신 미구현으로 인한 미구현
        ArrayList<Entry> values = new ArrayList<>();

        for (int i = 0; i <n; i++) {

            float val = (float) dbArr[i];
            values.add(new Entry(i, val));
        }
        return values;


    }
}