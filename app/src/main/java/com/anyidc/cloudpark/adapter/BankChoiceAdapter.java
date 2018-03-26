package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BankCardBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/16.
 */

public class BankChoiceAdapter extends RecyclerView.Adapter<BankChoiceAdapter.BankChoiceViewHolder> implements View.OnClickListener {
    private List<BankCardBean> list;
    private OnItemClickListener mItemClickListener;

    public BankChoiceAdapter(List<BankCardBean> list) {
        this.list = list;
    }

    @Override
    public BankChoiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bank_choice, parent, false);
        return new BankChoiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BankChoiceViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if (position == list.size() - 1) {
            holder.tvBank.setText("使用新卡提现");
            return;
        }
        BankCardBean bankCardBean = list.get(position);
        holder.tvBank.setText(bankCardBean.getCardInfo());
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

    class BankChoiceViewHolder extends RecyclerView.ViewHolder {
        private TextView tvBank;

        public BankChoiceViewHolder(View itemView) {
            super(itemView);
            tvBank = itemView.findViewById(R.id.tv_bank);
            itemView.setOnClickListener(BankChoiceAdapter.this);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener {
        void onClick(View view, int position);
    }
}
