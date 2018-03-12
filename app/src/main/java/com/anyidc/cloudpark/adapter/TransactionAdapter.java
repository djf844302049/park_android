package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/12.
 */

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private Context context;

    public TransactionAdapter(Context context) {
        this.context = context;
    }

    @Override
    public TransactionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TransactionViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
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
