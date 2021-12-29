package com.example.customdecoration.callback;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customdecoration.adapter.QQMessageItemAdapter;

import java.util.List;

public class QQMessageTouchHelperCallBack extends ItemTouchHelper.Callback {
    private List<String> mDatas;
    private RecyclerView.Adapter mAdapter;

    public QQMessageTouchHelperCallBack(List<String> mDatas, RecyclerView.Adapter mAdapter) {
        this.mDatas = mDatas;
        this.mAdapter = mAdapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = ItemTouchHelper.LEFT;
        int flags = makeMovementFlags(dragFlags, swipeFlags);
        return flags;
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    public void closeOpenid(){}

    private int mMaxWidth = 500;

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//        getDefaultUIUtil().onDraw(c, recyclerView, ((QQMessageItemAdapter.QQViewHolder) viewHolder).mMessageTv,
//                dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            if (dX < -mMaxWidth) {
                dX = -mMaxWidth;
            }
            ((QQMessageItemAdapter.QQViewHolder) viewHolder).mMessageTv.setTranslationX(dX);
        }
    }
}
