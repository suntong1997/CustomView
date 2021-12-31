package com.example.customdecoration.coverflow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.customdecoration.R;

import java.util.List;

public class CoverFlowAdapter extends RecyclerView.Adapter<CoverFlowAdapter.CoverFLowViewHolder> {
    private Context mContext;
    private List<String> mDatas;
    private int mCreatedHolder = 0;
    private int[] mPics = {R.mipmap.a17a60, R.mipmap.a18689, R.mipmap.a3efbe, R.mipmap.a85495, R.mipmap.ab0bee, R.mipmap.abbedf, R.mipmap.ac1515};

    public CoverFlowAdapter(Context mContext, List<String> mDatas) {
        this.mContext = mContext;
        this.mDatas = mDatas;
    }

    @NonNull
    @Override
    public CoverFLowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mCreatedHolder++;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        return new CoverFLowViewHolder(inflater.inflate(R.layout.item_coverflow, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CoverFLowViewHolder holder, int position) {
        holder.mTv.setText(mDatas.get(position));
        holder.mImg.setImageDrawable(mContext.getResources().getDrawable(mPics[position % mPics.length]));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class CoverFLowViewHolder extends RecyclerView.ViewHolder {
        public TextView mTv;
        public ImageView mImg;

        public CoverFLowViewHolder(@NonNull View itemView) {
            super(itemView);

            mTv = itemView.findViewById(R.id.text);
            mTv.setOnClickListener(v -> Toast.makeText(mContext, mTv.getText(), Toast.LENGTH_SHORT).show());
            mImg = itemView.findViewById(R.id.img);
            mImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, mTv.getText(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
