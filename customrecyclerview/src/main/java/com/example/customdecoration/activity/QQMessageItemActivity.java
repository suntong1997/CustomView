package com.example.customdecoration.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.example.customdecoration.R;
import com.example.customdecoration.adapter.QQMessageItemAdapter;
import com.example.customdecoration.callback.QQMessageTouchHelperCallBack;

import java.util.ArrayList;
import java.util.List;

public class QQMessageItemActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private List mDatas = new ArrayList();
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qq_message_item);

        mRecyclerView = findViewById(R.id.recyclerview);
        generateDatas();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        QQMessageItemAdapter adapter = new QQMessageItemAdapter(mDatas, this);

        adapter.setmOnBtnClickListener(new QQMessageItemAdapter.onBtnClickListener() {
            @Override
            public void onDelete(QQMessageItemAdapter.QQViewHolder holder) {
                Toast.makeText(getApplicationContext(), "Delete", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRefresh(QQMessageItemAdapter.QQViewHolder holder) {
                Toast.makeText(getApplicationContext(), "Refresh", Toast.LENGTH_SHORT).show();

            }
        });
        mRecyclerView.setAdapter(adapter);
        mItemTouchHelper = new ItemTouchHelper(new QQMessageTouchHelperCallBack(mDatas, adapter));
        mItemTouchHelper.attachToRecyclerView(mRecyclerView);

    }

    private void generateDatas() {
        for (int i = 0; i < 100; i++) {
            mDatas.add("Item " + i);
        }
    }

}