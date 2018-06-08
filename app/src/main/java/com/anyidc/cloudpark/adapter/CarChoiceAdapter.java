package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.MyCarBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */

public class CarChoiceAdapter extends RecyclerView.Adapter<CarChoiceAdapter.CarChoiceViewHolder> implements View.OnClickListener {
    private List<MyCarBean> list;
    private OnItemClickListener mItemClickListener;

    public CarChoiceAdapter(List<MyCarBean> list) {
        this.list = list;
    }

    @Override
    public CarChoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bank_choice, parent, false);
        return new CarChoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarChoiceViewHolder holder, int position) {
        holder.itemView.setTag(position);
        MyCarBean carBean = list.get(position);
        holder.tvCarNum.setText(carBean.getCar_no());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (mItemClickListener != null)
            mItemClickListener.onClick(v, position);
    }

    class CarChoiceViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCarNum;

        public CarChoiceViewHolder(View itemView) {
            super(itemView);
            tvCarNum = itemView.findViewById(R.id.tv_bank);
            itemView.setOnClickListener(CarChoiceAdapter.this);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
