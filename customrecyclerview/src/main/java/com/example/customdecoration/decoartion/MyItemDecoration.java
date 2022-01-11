package com.example.customdecoration.decoartion;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customdecoration.R;

public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private static final String TAG = "DividerItemDecoration";

    private final Context mContext;
    private final int groupDividerHeight;
    private int dividerPaddingLeft;
    private Paint dividerPaint;
    private Paint textPaint;
    private Paint topDividerPaint;


    public MyItemDecoration(Context mContext, OnGroupListener listener) {
        this.mContext = mContext;
        this.listener = listener;
        dividerPaddingLeft = dpToPx(20);
        groupDividerHeight = dpToPx(24);
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
        // 获取组名
        String getGroupName(int position);
    }


    public String getGroupName(int position) {
        if (listener != null) {
            try {
                return listener.getGroupName(position);
            } catch (ArrayIndexOutOfBoundsException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        try {
            int pos = parent.getChildAdapterPosition(view);
            String groupName = getGroupName(pos);
            if (TextUtils.isEmpty(groupName)) {
                return;
                //根据是否组内第一个item设置不同的高度
            } else if (pos == 0 || isFirstGroupItem(pos)) {
                outRect.top = groupDividerHeight;
            } else outRect.top = dpToPx(1);
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    //判断是否组内第一个item
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
        //获取屏幕可见的item数量
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);

            int childAdapterPosition = parent.getChildAdapterPosition(child);
            int bottom = child.getTop();
            int left = parent.getPaddingLeft();
            if (isFirstGroupItem(childAdapterPosition)) {
                int top = bottom - groupDividerHeight;

                canvas.drawRect(left, top,
                        child.getWidth() + left, bottom, dividerPaint);

                //设置字体基线
                float baseLine = (top + bottom) / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
                canvas.drawText(getGroupName(childAdapterPosition), left + dpToPx(10),
                        baseLine, textPaint);
            } else {
                int top = bottom - dpToPx(1);
                canvas.drawRect(left + dividerPaddingLeft, top,
                        child.getWidth() + left, bottom, dividerPaint);
            }
        }
    }

    @Override
    public void onDrawOver(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(canvas, parent, state);
        //获取第一个可见的子View
        View firstVisibleView = parent.getChildAt(0);
        int firstVisiblePosition = parent.getChildAdapterPosition(firstVisibleView);
        String groupName = getGroupName(firstVisiblePosition);
        int left = parent.getPaddingLeft();
        int right = firstVisibleView.getWidth() - parent.getPaddingRight();

        if (firstVisibleView.getBottom() <= groupDividerHeight && isFirstGroupItem(firstVisiblePosition + 1)) {
            canvas.drawRect(left, 0, right, firstVisibleView.getBottom(), dividerPaint);
            float baseLine = firstVisibleView.getBottom() / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
            canvas.drawText(groupName, left + dpToPx(10),
                    baseLine, textPaint);
            //固定group在顶部
        } else {
            canvas.drawRect(left, 0, right, groupDividerHeight, dividerPaint);
            float baseLine = groupDividerHeight / 2f - (textPaint.descent() + textPaint.ascent()) / 2f;
            canvas.drawText(groupName, left + dpToPx(10), baseLine, textPaint);
        }
    }
}
