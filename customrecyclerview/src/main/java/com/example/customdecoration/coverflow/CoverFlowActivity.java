package com.example.customdecoration.coverflow;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customdecoration.R;
import com.example.customdecoration.layoutmanager.CustomLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class CoverFlowActivity extends AppCompatActivity {
    private RecyclerView mRecyclerview;
    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flow_cover);
        mRecyclerview = findViewById(R.id.recyclerview);
        initData();
        CoverFlowAdapter adapter = new CoverFlowAdapter(this, mDatas);
        mRecyclerview.setAdapter(adapter);
        mRecyclerview.setLayoutManager(new CustomLayoutManager());
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add("a");
        mDatas.add("b");
        mDatas.add("c");
        mDatas.add("d");
        mDatas.add("e");
        mDatas.add("f");
        mDatas.add("g");
    }
}