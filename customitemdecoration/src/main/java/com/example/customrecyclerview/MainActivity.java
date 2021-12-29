package com.example.customrecyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SimpleAdapter;

import com.example.customrecyclerview.adapter.BookAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List books = new ArrayList();
        books.add("三国演义");
        books.add("水浒传");
        books.add("西游记");
        books.add("红楼梦");
        books.add("史记");
        books.add("孙子兵法");
        books.add("周易");
        mRecyclerView = findViewById(R.id.recyclerview);
        BookAdapter adapter = new BookAdapter(this, books);
        mRecyclerView.setAdapter(adapter);

    }
}