package com.example.customdecoration.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customdecoration.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class QQMessageItemAdapter extends RecyclerView.Adapter<QQMessageItemAdapter.QQViewHolder> {
    private List<String> mDatas = new ArrayList();
    private Context mContext;

    private onBtnClickListener mOnBtnClickListener;

    public void setmOnBtnClickListener(onBtnClickListener listener) {
        this.mOnBtnClickListener = listener;
    }

    public QQMessageItemAdapter(List mDatas, Context mContext) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public QQViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new QQViewHolder(inflater.inflate(R.layout.qq_message_item_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull QQViewHolder holder, int position) {
        holder.mMessageTv.setText(mDatas.get(position));
        if (mOnBtnClickListener != null) {
            holder.mDeleteTv.setOnClickListener(v -> mOnBtnClickListener.onDelete(holder));
            holder.mRefreshTv.setOnClickListener(v -> mOnBtnClickListener.onRefresh(holder));
        }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class QQViewHolder extends RecyclerView.ViewHolder implements Extension {
        public TextView mRefreshTv;
        public TextView mDeleteTv;
        public TextView mMessageTv;
        public LinearLayout mActionRoot;

        public QQViewHolder(@NonNull View itemView) {
            super(itemView);
            mRefreshTv = itemView.findViewById(R.id.txt_refresh);
            mDeleteTv = itemView.findViewById(R.id.txt_delete);
            mMessageTv = itemView.findViewById(R.id.txt_message);
            mActionRoot = itemView.findViewById(R.id.ll);
        }

        @Override
        public float getActionWidth() {
            return mActionRoot.getWidth();
        }
    }

    public interface onBtnClickListener {
        void onDelete(QQViewHolder holder);

        void onRefresh(QQViewHolder holder);
    }

    public interface Extension {
        float getActionWidth();
    }
}
