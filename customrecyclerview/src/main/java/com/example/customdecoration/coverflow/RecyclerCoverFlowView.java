package com.example.customdecoration.coverflow;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customdecoration.layoutmanager.CustomLayoutManager;

public class RecyclerCoverFlowView extends RecyclerView {
    public RecyclerCoverFlowView(@NonNull Context context) {
        super(context);
        init();
    }

    public RecyclerCoverFlowView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RecyclerCoverFlowView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setChildrenDrawingOrderEnabled(true);
    }

    public CustomLayoutManager getCoverFlowLayout() {
        return (CustomLayoutManager) getLayoutManager();
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        int center = getCoverFlowLayout().getCenterPosition()
                - getCoverFlowLayout().getFirstVisiblePosition(); //计算正在显示的所有Item的中间位置
        int order;

        if (i == center) {
            order = childCount - 1;
        } else if (i > center) {
            order = center + childCount - 1 - i;
        } else {
            order = i;
        }
        return order;
    }

    @Override
    public boolean fling(int velocityX, int velocityY) {
        int flingX = (int) (velocityX * 0.4f);
        return super.fling(flingX, velocityY);
    }
}
