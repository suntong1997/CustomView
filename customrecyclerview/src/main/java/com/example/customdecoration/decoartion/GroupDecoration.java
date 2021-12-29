package com.example.customdecoration.decoartion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customdecoration.R;

public class GroupDecoration extends RecyclerView.ItemDecoration {

    private int headerHeight;

    private Paint headPaint;
    private Paint headOverPaint;
    private Paint drawTextPaint;
    private Paint drawOverTextPaint;

    private Rect textRect;

    public GroupDecoration(Context context, OnGroupListener listener) {
        this.listener = listener;
        // 顶部吸顶栏的高度
        headerHeight = dp2px(context, 24);

        // 每一组的头部的Paint
        headPaint = new Paint();
        headPaint.setColor(Color.RED);

        headOverPaint = new Paint();
        headOverPaint.setColor(Color.YELLOW);

        drawTextPaint = new Paint();
        drawTextPaint.setTextSize(50);
        drawTextPaint.setColor(Color.BLACK);

        drawOverTextPaint = new Paint();
        drawOverTextPaint.setTextSize(50);
        drawOverTextPaint.setColor(Color.BLUE);

        textRect = new Rect();
    }

    // 都是绘制 -- 绘制后的效果 -- 分割线
    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDraw(c, parent, state);


            // 当前屏幕上的
            int count = parent.getChildCount();

            // 实现itemView的宽度和分割线的宽度一样
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            for (int i = 0; i < count; i++) {
                View view = parent.getChildAt(i);

                if (view.getTop() - headerHeight - parent.getPaddingTop() >= 0) {
                    // 当前Item的位置
                    int position = parent.getChildLayoutPosition(view);
                    // 如何判断 item 是头部
                    boolean isGroupHeader = isFirstGroupItem(position);
                    // 判断是否是头部
                    if (isGroupHeader) {
                        c.drawRect(left, view.getTop() - headerHeight, right, view.getTop(), headPaint);
                        String groupName = getGroupName(position);
                        drawTextPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                        // 绘制文字
                        c.drawText(groupName, left + 20,
                                view.getTop() - headerHeight / 2 + textRect.height() / 2, drawTextPaint);

                    } else { // 普通的itemView的分割线
                        c.drawRect(left, view.getTop() - 4, right, view.getTop(), drawTextPaint);
                    }
                }
            }

    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int top = parent.getPaddingTop();

            // 当前显示在界面的 第一个item
            int position = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
            View itemView = parent.findViewHolderForAdapterPosition(position).itemView;

            boolean isFirstItemOfGroup = isFirstGroupItem(position + 1);
            if (isFirstItemOfGroup) {
                int bottom = Math.min(top + headerHeight, itemView.getBottom());
                c.drawRect(left, top, right, bottom, headOverPaint);

                String groupName = getGroupName(position);
                drawOverTextPaint.getTextBounds(groupName, 0, groupName.length(), textRect);

                c.clipRect(left, top, right, bottom);

                c.drawText(groupName, left + 20,
                        bottom - headerHeight / 2 + textRect.height() / 2, drawOverTextPaint);
            } else {// 固定的
                c.drawRect(left, top, right, top + headerHeight, headOverPaint);

                String groupName = getGroupName(position);
                drawTextPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                // 绘制文字
                c.drawText(groupName, left + 20,
                        top + headerHeight / 2 + textRect.height() / 2, drawOverTextPaint);
            }
    }

    // 预留分割线的位置
    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view,
                               @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        // 当前Item的位置
        int position = parent.getChildLayoutPosition(view);

        // 如何判断 item 是头部
        boolean isGroupHeader = isFirstGroupItem(position);
        // 是第一个
        if (isGroupHeader) {
            outRect.set(0, headerHeight, 0, 0);
        } else {
            // padding，margin
            outRect.set(0, 4, 0, 0);
        }
    }


    private int dp2px(Context context, float dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue,
                context.getResources().getDisplayMetrics());
    }

    private final OnGroupListener listener;

interface OnGroupListener {

    // 获取分组中第一个文字
    String getGroupName(int position);

}

    private boolean isFirstGroupItem(int posititon) {
        if (posititon == 0) {
            return true;
        } else {
            String preGroupName = getGroupName(posititon - 1);
            String currGroupName = getGroupName(posititon);
            return !TextUtils.equals(preGroupName, currGroupName);
        }
    }

    public String getGroupName(int position) {
        if (listener != null) {
            return listener.getGroupName(position);
        }
        return null;
    }
}
