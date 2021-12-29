package com.example.customdecoration.decoartion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customdecoration.R;

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "DividerItemDecoration";

    private final Context mContext;
    private final int groupDividerHeight;
    private final int itemDividerHeight;
    private int dividerPaddingLeft;
    private int dividerPaddingRight;
    private Paint dividerPaint;
    private Paint textPaint;
    private Paint topDividerPaint;


    public MyItemDecoration(Context mContext, OnGroupListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        dividerPaddingLeft = dpToPx(20);
        groupDividerHeight = dpToPx(24);
        itemDividerHeight = dpToPx(1);
        initPaint();
    }

    private void initPaint() {
        dividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dividerPaint.setColor(mContext.getResources().getColor(R.color.purple_200));
        dividerPaint.setStyle(Paint.Style.FILL);


        topDividerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        topDividerPaint.setColor(Color.parseColor("#9924f715"));
        topDividerPaint.setStyle(Paint.Style.FILL);


        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(dpToPx(14));

    }

    private int dpToPx(int dps) {
        return Math.round(mContext.getResources().getDisplayMetrics().density * dps);

    }

    private final OnGroupListener listener;

    interface OnGroupListener {

        // 获取分组中第一个文字
        String getGroupName(int position);
    }


    public String getGroupName(int position) {
        if (listener != null) {
            return listener.getGroupName(position);
        }
        return null;
    }


    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int pos = parent.getChildAdapterPosition(view);
        String groupName = getGroupName(pos);
        if (TextUtils.isEmpty(groupName)) {
            return;
        } else if (pos == 0 || isFirstGroupItem(pos)) {
            outRect.top = groupDividerHeight;
        } else outRect.top = dpToPx(1);
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

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(canvas, parent, state);
        // getChildCount() 获取的是当前屏幕可见 item 数量，而不是 RecyclerView 所有的 item 数量
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = parent.getChildAt(i);
            // 获取当前itemview在adapter中的索引
            int childAdapterPosition = parent.getChildAdapterPosition(childView);
            /**
             * 由于分割线是绘制在每一个 itemview 的顶部，所以分割线矩形 rect.bottom = itemview.top,
             * rect.top = itemview.top - groupDividerHeight
             */
            int bottom = childView.getTop();
            int left = parent.getPaddingLeft();
            int right = parent.getPaddingRight();
            if (isFirstGroupItem(childAdapterPosition)) {   // 是分组第一个，则绘制分组分割线
                int top = bottom - groupDividerHeight;
                Log.d(TAG, "onDraw: top = " + top + ",bottom = " + bottom);
                // 绘制分组分割线矩形
                canvas.drawRect(left , top,
                        childView.getWidth() - right - dividerPaddingRight, bottom, dividerPaint);
                // 绘制分组分割线中的文字
                float baseLine = (top + bottom) / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
                canvas.drawText(getGroupName(childAdapterPosition), left + dpToPx(10),
                        baseLine, textPaint);
            } else {    // 不是分组中第一个，则绘制常规分割线
                int top = bottom - dpToPx(1);
                canvas.drawRect(left + dividerPaddingLeft, top,
                        childView.getWidth() - right - dividerPaddingRight, bottom, dividerPaint);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        View firstVisibleView = parent.getChildAt(0);
        int firstVisiblePosition = parent.getChildAdapterPosition(firstVisibleView);
        String groupName = getGroupName(firstVisiblePosition);
        int left = parent.getPaddingLeft();
        int right = firstVisibleView.getWidth() - parent.getPaddingRight();
        // 第一个itemview(firstVisibleView) 的 bottom 值小于分割线高度，分割线随着 recyclerview 滚动，
        // 分割线top固定不变，bottom=firstVisibleView.bottom
        if (firstVisibleView.getBottom() <= groupDividerHeight && isFirstGroupItem(firstVisiblePosition + 1)) {
            canvas.drawRect(left, 0, right, firstVisibleView.getBottom(), dividerPaint);
            float baseLine = firstVisibleView.getBottom() / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
            canvas.drawText(groupName, left + dpToPx(10),
                    baseLine, textPaint);
        } else {
            canvas.drawRect(left, 0, right, groupDividerHeight, dividerPaint);
            float baseLine = groupDividerHeight / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
            canvas.drawText(groupName, left + dpToPx(10), baseLine, textPaint);
        }
    }
}
