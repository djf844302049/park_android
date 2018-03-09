package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.MessageBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/3/4.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    private Context mContext;
    private List<MessageBean.OrderBean> list;

    public MessageAdapter(Context mContext, List<MessageBean.OrderBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.message_item_layout, null, false);
        return new MessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        MessageBean.OrderBean orderBean = list.get(position);
        holder.tvMsgType.setText(orderBean.getCategory() == 0 ? "预约消息" : "系统消息");
        holder.tvMsgContent.setText(orderBean.getData());
        holder.tvTime.setText(new SimpleDateFormat("MM/dd").format(new Date(orderBean.getSend_time())));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public static class MessageHolder extends RecyclerView.ViewHolder {
        private TextView tvMsgType, tvTime, tvMsgContent;

        public MessageHolder(View itemView) {
            super(itemView);
            tvMsgType = itemView.findViewById(R.id.tv_message_type);
            tvTime = itemView.findViewById(R.id.tv_message_time);
            tvMsgContent = itemView.findViewById(R.id.tv_message_content);
        }
    }
}
