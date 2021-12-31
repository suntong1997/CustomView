package com.example.camera.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;

public class Rotate3DAnimation extends Animation {
    private final float mFromDegrees;
    private final float mEndDegrees;
    private final float mDepthZ = 400;
    private final boolean mReverse;
    private float mCenterX, mCenterY;
    private Camera mCamera;

    public Rotate3DAnimation(float fromDegrees, float endDegrees, boolean reverse) {
        mEndDegrees = endDegrees;
        mFromDegrees = fromDegrees;
        mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);

        mCenterX = width / 2;
        mCenterY = height / 2;
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degrees = mFromDegrees + ((mEndDegrees - mFromDegrees) * interpolatedTime);
        mCamera.save();

        float z;
        if (mReverse) {
            z = mDepthZ * interpolatedTime;
            mCamera.translate(0.0f, 0.0f, z);
        } else {
            z = mDepthZ * (1.0f - interpolatedTime);
            mCamera.translate(0.0f, 0.0f, z);
        }

        final Matrix matrix = t.getMatrix();
        mCamera.rotateY(degrees);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);

        super.applyTransformation(interpolatedTime, t);
    }
}
