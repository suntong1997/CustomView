package com.sun.chartview;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ChartViewActivity extends AppCompatActivity {
    ChartView chartView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);
        chartView = findViewById(R.id.chartview);

        int[][] columnInfo = new int[][]{
                {6, Color.parseColor("#FFA500")},
                {2, Color.parseColor("#FFD700")},
                {8, Color.parseColor("#BC8F8F")},
                {6, Color.parseColor("#DB7093")},
                {7, Color.parseColor("#FFA500")},
                {9, Color.parseColor("#DAA520")},
                {10, Color.parseColor("#9932CC")},
        };

        chartView.setColumInfo(columnInfo);
        chartView.setAxisDivideSizeX(10);
        chartView.setAxisDivideSizeY(10);

    }
}