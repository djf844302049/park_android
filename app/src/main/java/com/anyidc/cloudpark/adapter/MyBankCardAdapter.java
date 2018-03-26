package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BankCardBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class MyBankCardAdapter extends RecyclerView.Adapter<MyBankCardAdapter.MyBankCardViewHolder> {
    private List<BankCardBean> list;

    public MyBankCardAdapter(List<BankCardBean> list) {
        this.list = list;
    }

    @Override
    public MyBankCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_my_bank_card, parent, false);
        return new MyBankCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyBankCardViewHolder holder, int position) {
        BankCardBean bankCardBean = list.get(position);
        holder.tvBank.setText(bankCardBean.getCardBank());
        holder.tvBankType.setText(bankCardBean.getCardBankType());
        StringBuilder builder = new StringBuilder("**** **** **** ");
        builder.append(bankCardBean.getCardLastNums());
        holder.tvBankNum.setText(builder.toString());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyBankCardViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBankLogo;
        TextView tvBank, tvBankType, tvBankNum;

        public MyBankCardViewHolder(View itemView) {
            super(itemView);
            ivBankLogo = itemView.findViewById(R.id.iv_bank_logo);
            tvBank = itemView.findViewById(R.id.tv_bank);
            tvBankType = itemView.findViewById(R.id.tv_bank_type);
            tvBankNum = itemView.findViewById(R.id.tv_bank_num);
        }
    }
}
