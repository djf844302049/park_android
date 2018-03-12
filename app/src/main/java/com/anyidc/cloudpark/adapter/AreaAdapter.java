package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

import java.util.List;

/**
 * Created by Administrator on 2018/2/23.
 */

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.AreaHolder> implements View.OnClickListener {
    private List<String> list;
    private ItemClickListener mItemClickListener;

    public AreaAdapter(List<String> list) {
        this.list = list;
    }

    @Override
    public AreaHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_area, null);
        return new AreaHolder(view);
    }

    @Override
    public void onBindViewHolder(AreaHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.tvArea.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(view, position);
        }
    }

    class AreaHolder extends RecyclerView.ViewHolder {
        TextView tvArea;

        public AreaHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tv_area);
            itemView.setOnClickListener(AreaAdapter.this);
        }
    }

    public void setOnItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    /**
     * 创建一个回调接口
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
