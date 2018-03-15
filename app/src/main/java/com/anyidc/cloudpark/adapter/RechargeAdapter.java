package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.RechargeBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/15.
 */

public class RechargeAdapter extends RecyclerView.Adapter<RechargeAdapter.RechargeViewHolder> implements View.OnClickListener {
    private List<RechargeBean> list;
    private ItemClickListener mItemClickListener;

    public RechargeAdapter(List<RechargeBean> list) {
        this.list = list;
    }

    @Override
    public RechargeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_recharge, parent, false);
        return new RechargeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RechargeViewHolder holder, int position) {
        holder.itemView.setTag(position);
        RechargeBean bean = list.get(position);
        holder.tv.setEnabled(bean.isCheck());
        holder.tv.setText(bean.getNum() + "元");
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v, position);
        }
    }

    class RechargeViewHolder extends RecyclerView.ViewHolder {

        private TextView tv;

        public RechargeViewHolder(View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.tv_recharge);
            itemView.setOnClickListener(RechargeAdapter.this);
        }
    }

    public void setOnItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
