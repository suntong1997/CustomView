package com.example.customview;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customdecoration.activity.QQMessageItemActivity;
import com.example.customdecoration.decoartion.RecyclerActivity;

public class MainActivity extends AppCompatActivity {
    private Button customDecorBtn;
    private Intent intent;
    private Button qqDeleteBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customDecorBtn = findViewById(R.id.custom_decoration);
        qqDeleteBtn=findViewById(R.id.qq_delete);

        customDecorBtn.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), RecyclerActivity.class);
            startActivity(intent);
        });

        qqDeleteBtn.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), QQMessageItemActivity.class);
            startActivity(intent);
        });
    }
}
