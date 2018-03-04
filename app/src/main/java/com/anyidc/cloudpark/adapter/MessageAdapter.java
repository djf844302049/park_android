package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/4.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageHolder> {
    private Context mContext;
    public MessageAdapter(Context mContext){
        this.mContext = mContext;
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.message_item_layout,null,false);
        return new MessageHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MessageHolder extends RecyclerView.ViewHolder{
        private TextView tvMsgType,tvTime,tvMsgContent;
        public MessageHolder(View itemView){
            super(itemView);
            tvMsgType = itemView.findViewById(R.id.tv_message_type);
            tvTime = itemView.findViewById(R.id.tv_message_time);
            tvMsgContent = itemView.findViewById(R.id.tv_message_content);
        }
    }
}
