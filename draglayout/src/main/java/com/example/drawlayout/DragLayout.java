package com.example.drawlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

public class DragLayout extends FrameLayout {
    private ViewDragHelper mDragger;
    private View mMainView;
    private View mMenuView;
    private int mMenuViewWidth = 800;

    public DragLayout(Context context) {
        super(context);
        init();
    }

    public DragLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DragLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return mDragger.shouldInterceptTouchEvent(event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragger.processTouchEvent(event);
        return true;
    }

    public void setView(View mainView, LayoutParams mainLayoutParams) {
        mMainView = mainView;
        addView(mMainView, mainLayoutParams);
    }

    public void setView(View mainView, LayoutParams mainLayoutParams,
                        View menuView, LayoutParams menuLayoutParams) {
        mMenuView = menuView;
        addView(menuView, menuLayoutParams);
        mMenuViewWidth = menuLayoutParams.width;
        mMainView = mainView;
        addView(mainView, mainLayoutParams);

    }

    private void init() {
        mDragger = ViewDragHelper.create(this, 1.0f, new ViewDragHelper.Callback() {
            @Override
            public boolean tryCaptureView(@NonNull View child, int pointerId) {
                return child == mMainView;
            }

            @Override
            public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
                if (left > 0) {
                    return Math.min(left, mMenuViewWidth);
                }
                return 0;
            }

            @Override
            public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
                super.onViewReleased(releasedChild, xvel, yvel);
                if (mMainView.getLeft() < mMenuViewWidth / 2) {
                    mDragger.smoothSlideViewTo(mMainView, 0, 0);
                } else {
                    mDragger.smoothSlideViewTo(mMainView, mMenuViewWidth, 0);
                }
                invalidate();
            }
        });
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mDragger != null && mDragger.continueSettling(true)) {
            invalidate();
        }
    }

    public void closeMenu() {
        mDragger.smoothSlideViewTo(mMainView, 0, 0);
        ViewCompat.postInvalidateOnAnimation(this);
    }
}
