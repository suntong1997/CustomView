package com.example.customview;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.camera.MiClockActivity;
import com.example.camera.Rotate3DActivity;
import com.example.customdecoration.activity.QQMessageItemActivity;
import com.example.customdecoration.coverflow.CoverFlowActivity;
import com.example.customdecoration.decoartion.RecyclerActivity;
import com.example.drawlayout.DragLayoutActivity;
import com.example.flowlayout.FlowLayoutActivity;
import com.example.motionlayout.MotionLayoutActivity;
import com.sun.verificationcodeview.VerificationCodeViewActivity;

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

        findViewById(R.id.bt_draglayout).setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), DragLayoutActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.bt_flowlayout).setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), FlowLayoutActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.bt_motion_layout).setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), MotionLayoutActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.bt_verification_code_view).setOnClickListener(v -> {
            intent = new Intent(getApplicationContext(), VerificationCodeViewActivity.class);
            startActivity(intent);
        });
    }
}
