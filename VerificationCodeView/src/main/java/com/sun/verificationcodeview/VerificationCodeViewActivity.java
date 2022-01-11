package com.sun.verificationcodeview;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VerificationCodeViewActivity extends AppCompatActivity implements VerificationCodeView.OnCodeFinishListener {
    VerificationCodeView codeView;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification_code_view);
        codeView = findViewById(R.id.verification_code_view);
        textView = findViewById(R.id.text);

        codeView.setOnCodeFinishListener(this);
    }

    @Override
    public void onTextChange(View view, String content) {
        if (view == codeView) {
            textView.setText("输入: " + content);
        }
    }

    @Override
    public void onComplete(View view, String content) {
        textView.setText("验证: " + content);
    }
}