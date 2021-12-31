package com.example.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.example.camera.animation.Rotate3DAnimation;


public class Rotate3DActivity extends AppCompatActivity {
    private View mContentRoot;

    private int duration = 600;
    private Rotate3DAnimation openAnimation;
    private Rotate3DAnimation closeAnimation;

    private boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rotate3_dactivity);
        mContentRoot = findViewById(R.id.content);
        initOpenAnim();
        initCloseAnim();
    }

    private void initCloseAnim() {
        closeAnimation = new Rotate3DAnimation(180, 90, true);
        closeAnimation.setDuration(duration);
        closeAnimation.setFillAfter(true);
        closeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.iv_logo).setVisibility(View.VISIBLE);
                findViewById(R.id.iv_logo_2).setVisibility(View.GONE);

                Rotate3DAnimation rotate3DAnimation = new Rotate3DAnimation(90, 0, false);
                rotate3DAnimation.setDuration(duration);
                rotate3DAnimation.setFillAfter(true);
                rotate3DAnimation.setInterpolator(new DecelerateInterpolator());
                mContentRoot.startAnimation(rotate3DAnimation);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void initOpenAnim() {
        openAnimation = new Rotate3DAnimation(0, 90, true);
        openAnimation.setDuration(duration);
        openAnimation.setFillAfter(true);
        openAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(R.id.iv_logo).setVisibility(View.GONE);
                findViewById(R.id.iv_logo_2).setVisibility(View.VISIBLE);

                Rotate3DAnimation rotate3DAnimation = new Rotate3DAnimation(90, 180, false);
                rotate3DAnimation.setDuration(duration);
                rotate3DAnimation.setFillAfter(true);
                rotate3DAnimation.setInterpolator(new DecelerateInterpolator());
                mContentRoot.startAnimation(rotate3DAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public void onClickView(View view) {
        if (openAnimation.hasStarted() && !openAnimation.hasEnded()) {
            return;
        }
        if (isOpen) {
            mContentRoot.startAnimation(closeAnimation);
        } else {
            mContentRoot.startAnimation(openAnimation);
        }
        isOpen = !isOpen;
    }
}