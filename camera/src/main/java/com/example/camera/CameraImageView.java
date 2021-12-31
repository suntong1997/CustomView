package com.example.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class CameraImageView extends androidx.appcompat.widget.AppCompatImageView {
    private Bitmap mBitmap;
    private Paint mPaint;
    private Camera camera = new Camera();
    private Matrix matrix = new Matrix();
    private int mProgress;

    public CameraImageView(Context context) {
        super(context);
        init();
    }

    public CameraImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CameraImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.xxxxxx);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
    }

    public void setProgress(int progress) {
        mProgress = progress;
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        camera.save();
        canvas.save();

        mPaint.setAlpha(100);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);

        camera.rotateZ(mProgress);

        int centerX = getWidth() / 2/72;
        int centerY = getHeight() / 2/72;

        camera.setLocation(centerX, -centerY, camera.getLocationZ());
        camera.getMatrix(matrix);
        canvas.setMatrix(matrix);

        camera.restore();
        //绘制图片在旋转后的画布上
        super.onDraw(canvas);
        canvas.restore();
    }
}
