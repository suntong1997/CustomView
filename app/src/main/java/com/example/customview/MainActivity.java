package com.example.customview;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.camera.MiClockActivity;
import com.example.camera.Rotate3DActivity;
import com.example.camera.TranslateActivity;
import com.example.customdecoration.activity.QQMessageItemActivity;
import com.example.customdecoration.coverflow.CoverFlowActivity;
import com.example.customdecoration.decoartion.RecyclerActivity;
import com.example.drawlayout.DragLayoutActivity;

public class MainActivity extends AppCompatActivity {
    private Button customDecorBtn;
    private Intent intent;
    private Button qqDeleteBtn;
    private Button cameraBtn;
    private Button miClockBtn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        customDecorBtn = findViewById(R.id.custom_decoration);
        qqDeleteBtn = findViewById(R.id.qq_delete);
        cameraBtn = findViewById(R.id.bt_camera);
        miClockBtn = findViewById(R.id.bt_mi_clock);

        customDecorBtn.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), RecyclerActivity.class);
            startActivity(intent);
        });

        qqDeleteBtn.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), QQMessageItemActivity.class);
            startActivity(intent);
        });

        cameraBtn.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), Rotate3DActivity.class);
            startActivity(intent);
        });

        miClockBtn.setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), MiClockActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.bt_coverflow).setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), CoverFlowActivity.class);
            startActivity(intent);
        });
        findViewById(R.id.bt_draglayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(getApplicationContext(), DragLayoutActivity.class);
                startActivity(intent);
            }
        });
    }
}
