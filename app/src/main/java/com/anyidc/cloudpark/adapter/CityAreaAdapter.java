package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.CityAreaBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class CityAreaAdapter extends RecyclerView.Adapter<CityAreaAdapter.CityAreaViewHolder> implements View.OnClickListener {
    private List<CityAreaBean> list;
    private OnItemClickListener onItemClickListener;

    public CityAreaAdapter(List<CityAreaBean> list) {
        this.list = list;
    }

    @Override
    public CityAreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bank_choice, parent, false);
        return new CityAreaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CityAreaViewHolder holder, int position) {
        holder.itemView.setTag(position);
        CityAreaBean cityAreaBean = list.get(position);
        holder.tvArea.setText(cityAreaBean.getName());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (onItemClickListener != null) {
            onItemClickListener.onClick(v, position);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class CityAreaViewHolder extends RecyclerView.ViewHolder {
        TextView tvArea;

        public CityAreaViewHolder(View itemView) {
            super(itemView);
            tvArea = itemView.findViewById(R.id.tv_bank);
            itemView.setOnClickListener(CityAreaAdapter.this);
        }
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
