package com.example.drawlayout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class DragLayoutActivity extends AppCompatActivity {
    private DragLayout mDragLayout;
    private TextView mMainTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_layout);

        LayoutInflater inflater = LayoutInflater.from(this);
        mDragLayout = findViewById(R.id.slide_menu_containter);
        View mainView = inflater.inflate(R.layout.slide_content_layout, null, false);
        FrameLayout.LayoutParams mainLayoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        int menuWidth = 800;
        FrameLayout.LayoutParams menuLayoutParams = new FrameLayout.LayoutParams(menuWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
        View menuView = inflater.inflate(R.layout.slide_menu_layout, null, false);
        mDragLayout.setView(mainView, mainLayoutParams, menuView, menuLayoutParams);
        mMainTv = mainView.findViewById(R.id.slide_main_view_text);
        menuView.findViewById(R.id.tv_item_1).setOnClickListener(v -> changemainViewText("1"));
        menuView.findViewById(R.id.tv_item_2).setOnClickListener(v -> changemainViewText("2"));
        menuView.findViewById(R.id.tv_item_3).setOnClickListener(v -> changemainViewText("3"));
        menuView.findViewById(R.id.tv_item_4).setOnClickListener(v -> changemainViewText("4"));
        menuView.findViewById(R.id.tv_item_5).setOnClickListener(v -> changemainViewText("5"));
        menuView.findViewById(R.id.tv_item_6).setOnClickListener(v -> changemainViewText("6"));
        menuView.findViewById(R.id.tv_item_7).setOnClickListener(v -> changemainViewText("7"));


    }

    private void changemainViewText(String text) {
        mMainTv.setText(text);
        mDragLayout.closeMenu();
    }
}