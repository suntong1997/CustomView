package com.sun.chartview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

public class ChartView extends BaseView {
    public ChartView(Context context) {
        super(context);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void drawXAxis(Canvas canvas, Paint mPaint) {
        mPaint.setColor((int) mAxisTitleColor);
        mPaint.setStrokeWidth(3);
        canvas.drawLine(rawX, rawY, rawX + width, rawY, mPaint);
    }

    @Override
    protected void drawYAxis(Canvas canvas, Paint mPaint) {
        mPaint.setColor((int) mAxisTitleColor);
        mPaint.setStrokeWidth(3);
        canvas.drawLine(rawX, rawY, rawX, rawY - height, mPaint);
    }

    @Override
    protected void drawXAxisScale(Canvas canvas, Paint mPaint) {
        mPaint.setColor((int) mAxisTitleColor);
        mPaint.setStrokeWidth(2);
        float cellWidth = width / axisDivideSizeX;
        for (int i = 0; i < axisDivideSizeX - 1; i++) {
            canvas.drawLine(cellWidth * (i + 1) + rawX, rawY, cellWidth * (i + 1) + rawX, rawY - 10, mPaint);
        }
    }

    @Override
    protected void drawXAxisScaleValue(Canvas canvas, Paint mPaint) {
        mPaint.setColor((int) mAxisTitleColor);
        mPaint.setTextSize(20);
        float cellWidth = width / axisDivideSizeX;
        for (int i = 0; i < axisDivideSizeX - 1; i++) {
            canvas.drawText(String.valueOf(i + 1), cellWidth * (i + 1) + rawX, rawY + 30, mPaint);
        }
    }

    @Override
    protected void drawYAxisScale(Canvas canvas, Paint mPaint) {
        mPaint.setColor((int) mAxisTitleColor);
        mPaint.setStrokeWidth(2);
        float cellHeight = height / axisDivideSizeX;
        for (int i = 0; i < axisDivideSizeY - 1; i++) {
            canvas.drawLine(rawX, (rawY - cellHeight * (i + 1)),
                    rawX + 10, (rawY - cellHeight * (i + 1)), mPaint);
        }
    }

    @Override
    protected void drawYAxisScaleValue(Canvas canvas, Paint mPaint) {
        mPaint.setColor((int) mAxisTitleColor);
        mPaint.setTextSize(20);
        float cellHeight = height / axisDivideSizeY;
        for (int i = 0; i < axisDivideSizeY; i++) {
            canvas.drawText(String.valueOf(i), rawX - 30, rawY - cellHeight * i + 10, mPaint);
        }
    }

    @Override
    protected void drawColumn(Canvas canvas, Paint mPaint) {
        if (columInfo != null) {
            float cellWidth = width / axisDivideSizeX;
            for (int i = 0; i < columInfo.length; i++) {
                mPaint.setColor(columInfo[i][1]);
                float leftTopY = rawY - height * (columInfo[i][0]) / axisDivideSizeY;
                canvas.drawRect(rawX + cellWidth * (i + 1), leftTopY, rawX + cellWidth * (i + 2), rawY, mPaint);
            }
        }
    }

    @Override
    protected void drawColumnValue(Canvas canvas, Paint mPaint) {
        float cellWidth = width / axisDivideSizeX;
        if (columInfo != null) {
            mPaint.setColor(Color.parseColor("#4682B4"));
            for (int i = 0; i < columInfo.length; i++) {
                float leftTopY = rawY - height * (columInfo[i][0]) / axisDivideSizeY;
                canvas.drawText(columInfo[i][0] + "",
                        (rawX + cellWidth * (i + 1)) + cellWidth / 2, leftTopY - 10, mPaint);
            }
        }
    }
}
