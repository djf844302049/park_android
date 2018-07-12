package com.anyidc.cloudpark.adapter;

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
    private List<MessageBean.OrderBean> list;

    public MessageAdapter(List<MessageBean.OrderBean> list) {
        this.list = list;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item_layout, null, false);
        return new MessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        MessageBean.OrderBean orderBean = list.get(position);
        switch (orderBean.getCategory()) {
            case 0:
                holder.tvMsgType.setText("预约消息");
                break;
            case 1:
                holder.tvMsgType.setText("车位更新");
                break;
            case 2:
                holder.tvMsgType.setText("系统消息");
                break;
            case 3:
                holder.tvMsgType.setText("注册认证");
                break;
            case 4:
                holder.tvMsgType.setText("押金充值");
                break;
            case 5:
                holder.tvMsgType.setText("维护公告");
                break;
        }
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
