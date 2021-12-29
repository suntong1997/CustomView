package com.example.customdecoration.customlayoutmanager;

import android.graphics.Rect;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class CustomLayoutManager extends RecyclerView.LayoutManager {
    private static final String TAG = CustomLayoutManager.class.getName();
    private int mSumDy = 0;
    private int mTotalheight = 0;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        //返回recyclerview的item布局参数
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() <= 0) {
            return dy;
        }
        int travel = dy;

        //如果向下滑动超出范围则移动到顶部
        if (mSumDy + dy < 0) {
            travel = -mSumDy;
            //滑动到底部
        } else if (mSumDy + dy > mTotalheight - getVericalSpace()) {
            travel = mTotalheight - getVericalSpace() - mSumDy;
        }

        mSumDy += travel;

        Rect visibleRect = getVisibleArea();

        //回收越界View
        for (int i = getChildCount() - 1; i >= 0; i--) {
            View child = getChildAt(i);
            int position = getPosition(child);
            Rect rect = mItemRects.get(position);

            if (!Rect.intersects(rect, visibleRect)) {
                removeAndRecycleView(child, recycler);
                mHasAttachedItems.put(position, false);
            } else {
                layoutDecoratedWithMargins(child, rect.left, rect.top - mSumDy,
                        rect.right, rect.bottom - mSumDy);
                child.setRotationY(child.getRotationY() + 1);
                mHasAttachedItems.put(position, true);
            }
        }

        View firstView = getChildAt(0);
        View lastView = getChildAt(getChildCount() - 1);
        if (travel >= 0) {
            int minPos = getPosition(firstView);
            for (int i = minPos; i < getItemCount(); i++) {
                insertView(i, visibleRect, recycler, false);
            }
        } else {
            int maxPos = getPosition(lastView);
            for (int i = maxPos; i >= 0; i--) {
                insertView(i, visibleRect, recycler, true);
            }
        }

        return travel;
    }

    private void insertView(int pos, Rect visibleRect, RecyclerView.Recycler recycler, boolean firstPos) {
        Rect rect = mItemRects.get(pos);
        if (Rect.intersects(visibleRect, rect) && !mHasAttachedItems.get(pos)) {
            View child = recycler.getViewForPosition(pos);
            if (firstPos) {
                addView(child, 0);
            } else {
                addView(child);
            }
            measureChildWithMargins(child, 0, 0);
            layoutDecoratedWithMargins(child, rect.left, rect.top - mSumDy,
                    rect.right, rect.bottom - mSumDy);

            child.setRotationY(child.getRotationY() + 1);
            mHasAttachedItems.put(pos, true);
        }
    }


    private int mItemWidth, mItemHeight;
    SparseArray<Rect> mItemRects = new SparseArray();
    private SparseBooleanArray mHasAttachedItems = new SparseBooleanArray();

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getItemCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        mHasAttachedItems.clear();
        mItemRects.clear();
        detachAndScrapAttachedViews(recycler);

        //储存item的位置
        View childView = recycler.getViewForPosition(0);
        measureChildWithMargins(childView, 0, 0);
        mItemWidth = getDecoratedMeasuredWidth(childView);
        mItemHeight = getDecoratedMeasuredHeight(childView);

        int visibleCount = getVericalSpace() / mItemHeight;
        int offsetY = 0;

        for (int i = 0; i < getItemCount(); i++) {
            Rect rect = new Rect(0, offsetY, mItemWidth, offsetY + mItemHeight);
            mItemRects.put(i, rect);
            offsetY += mItemHeight;
        }

        for (int i = 0; i < visibleCount; i++) {
            Rect rect = mItemRects.get(i);
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            layoutDecorated(view, rect.left, rect.top, rect.right, rect.bottom);
        }
        //判断item是否填满屏幕
        mTotalheight = Math.max(offsetY, getVericalSpace());
    }

    private Rect getVisibleArea() {
        Rect result = new Rect(getPaddingLeft(), getPaddingTop() + mSumDy,
                getWidth() + getPaddingRight(),
                getVericalSpace() + mSumDy);
        return result;
    }

    private int getVericalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }
}
