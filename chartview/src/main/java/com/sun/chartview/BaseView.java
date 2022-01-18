package com.sun.chartview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public abstract class BaseView extends View {
    public int width;
    public int height;
    public int rawX = 80;
    public int rawY = 1000;
    public int axisDivideSizeX;
    public int axisDivideSizeY;
    public int[][] columInfo;
    public String mGraphTitle;
    public String mXAxisTitle;
    public String mYAxisTitle;
    public float mAxisTitleSize;
    public float mAxisTitleColor;
    private Context mContext;
    private Paint mPaint;

    public BaseView(Context context) {
        this(context, null);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public BaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ChartStyle);
        mGraphTitle = a.getString(R.styleable.ChartStyle_graphTitle);
        mXAxisTitle = a.getString(R.styleable.ChartStyle_xAxisTitle);
        mYAxisTitle = a.getString(R.styleable.ChartStyle_yAxisTitle);
        mAxisTitleColor = a.getColor(R.styleable.ChartStyle_axisTitleColor,
                context.getResources().getColor(android.R.color.black));
        mAxisTitleSize = a.getDimension(R.styleable.ChartStyle_axisTitleSize, 12);

        a.recycle();
        initPaint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        width = getWidth() - rawX - 80;
        height = (rawY > getHeight() ? getHeight() : rawY) - 100;

        drawXAxis(canvas, mPaint);
        drawYAxis(canvas, mPaint);
        drawTitle(canvas, mPaint);
        drawXAxisScale(canvas, mPaint);
        drawXAxisScaleValue(canvas, mPaint);
        drawYAxisScale(canvas, mPaint);
        drawYAxisScaleValue(canvas, mPaint);
        drawXAxisArrow(canvas, mPaint);
        drawYAxisArrow(canvas, mPaint);
        drawColumn(canvas, mPaint);
        drawColumnValue(canvas, mPaint);
    }

    protected abstract void drawXAxis(Canvas canvas, Paint mPaint);

    protected abstract void drawYAxis(Canvas canvas, Paint mPaint);

    private void drawTitle(Canvas canvas, Paint mPaint) {
        if (!TextUtils.isEmpty(mGraphTitle)) {
            mPaint.setTextSize(mAxisTitleSize);
            mPaint.setColor((int) mAxisTitleColor);

            canvas.drawText(mGraphTitle,
                    (getWidth() / 2) - (mPaint.measureText(mGraphTitle)) / 2,
                    rawY + 100, mPaint);
        }
    }

    protected abstract void drawXAxisScale(Canvas canvas, Paint mPaint);

    protected abstract void drawXAxisScaleValue(Canvas canvas, Paint mPaint);

    protected abstract void drawYAxisScale(Canvas canvas, Paint mPaint);

    protected abstract void drawYAxisScaleValue(Canvas canvas, Paint mPaint);

    private void drawXAxisArrow(Canvas canvas, Paint mPaint) {
        Path mPathX = new Path();

        mPathX.moveTo(rawX + width + 30, rawY);
        mPathX.lineTo(rawX + width, rawY + 10);
        mPathX.lineTo(rawX + width, rawY - 10);

        mPathX.close();
        mPaint.setTextSize(30);
        mPaint.setColor((int) mAxisTitleColor);
        canvas.drawPath(mPathX, mPaint);
        canvas.drawText(mXAxisTitle, rawX + width, rawY + 50, mPaint);
    }

    private void drawYAxisArrow(Canvas canvas, Paint mPaint) {
        Path mPathY = new Path();

        mPathY.moveTo(rawX, rawY - height - 30);
        mPathY.lineTo(rawX - 10, rawY - height);
        mPathY.lineTo(rawX + 10, rawY - height);

        mPathY.close();
        mPaint.setTextSize(30);
        mPaint.setColor((int) mAxisTitleColor);
        canvas.drawPath(mPathY, mPaint);
        canvas.drawText(mYAxisTitle, rawX - 50, rawY - height - 30, mPaint);
    }

    protected abstract void drawColumn(Canvas canvas, Paint mPaint);

    protected abstract void drawColumnValue(Canvas canvas, Paint mPaint);

    private void initPaint() {
        if (mPaint == null) {
            mPaint = new Paint();
            mPaint.setDither(true);
            mPaint.setAntiAlias(true);
        }
    }

    public void setAxisDivideSizeX(int axisDivideSizeX) {
        this.axisDivideSizeX = axisDivideSizeX;
    }

    public void setAxisDivideSizeY(int axisDivideSizeY) {
        this.axisDivideSizeY = axisDivideSizeY;
    }

    public void setColumInfo(int[][] columInfo) {
        this.columInfo = columInfo;
    }


}
