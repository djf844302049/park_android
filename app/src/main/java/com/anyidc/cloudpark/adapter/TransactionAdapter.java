package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.TransactionBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/12.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {
    private List<TransactionBean.ListBean> list;

    public TransactionAdapter(List<TransactionBean.ListBean> list) {
        this.list = list;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {
        TransactionBean.ListBean listBean = list.get(position);
        StringBuilder nameAndWay = new StringBuilder(listBean.getDesc());
        nameAndWay.append(" | ");
        switch (listBean.getPay_type()) {
            case 1:
                nameAndWay.append("支付宝");
                break;
            case 2:
                nameAndWay.append("微信");
                break;
            case 3:
                nameAndWay.append("银联");
                break;
            case 4:
                nameAndWay.append("余额");
                break;
        }
        holder.tvNameWay.setText(nameAndWay.toString());
        switch (listBean.getType()) {
            case 1://支出
                holder.tvNum.setText("-￥" + listBean.getAmount());
                break;
            case 2://收入
                holder.tvNum.setText("+￥" + listBean.getAmount());
                break;
        }
        Date date = new Date(listBean.getPaid_time());
        String format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
        holder.tvTime.setText(format);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class TransactionViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameWay, tvNum, tvTime;

        public TransactionViewHolder(View itemView) {
            super(itemView);
            tvNameWay = itemView.findViewById(R.id.tv_name_way);
            tvNum = itemView.findViewById(R.id.tv_num);
            tvTime = itemView.findViewById(R.id.tv_time);
        }
    }
}
