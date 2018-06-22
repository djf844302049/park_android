package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.ShareParkBean;

import java.util.List;

/**
 * Created by Administrator on 2018/4/11.
 */

public class ShareParkUnitAdapter extends RecyclerView.Adapter<ShareParkUnitAdapter.ShareParkUnitHolderView> implements View.OnClickListener {
    private Context context;
    private LayoutInflater mInflater;
    private List<ShareParkBean.FreeArrBean> sharelist;
    private ItemClickListener itemClickListener;
    private int selectPos = -1;
    private int type;

    public ShareParkUnitAdapter(Context context, List<ShareParkBean.FreeArrBean> dataList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.sharelist = dataList;
    }


    @Override
    public ShareParkUnitHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.share_park_unit_item_layout, null, false);
        return new ShareParkUnitHolderView(itemView);
    }

    @Override
    public void onBindViewHolder(ShareParkUnitHolderView holder, int position) {
        if (selectPos == -1) {
            holder.ivCheck.setImageResource(R.mipmap.img_cb_circle_normal);
        }
        ShareParkBean.FreeArrBean freeArrBean = sharelist.get(position);
        if (freeArrBean != null) {
            holder.itemView.setTag(position);
            holder.tvNum.setText(freeArrBean.getUnit_id());
            holder.tvShareTime.setText("共享时间段：" + freeArrBean.getShare_time());
            holder.tvFee.setText("停车费：¥" + freeArrBean.getHourfee());
            holder.itemView.setOnClickListener(this);
            holder.ivCheck.setOnClickListener(this);
            if (selectPos == position) {
                holder.ivCheck.setImageResource(R.mipmap.img_cb_circle_select);
            } else {
                holder.ivCheck.setImageResource(R.mipmap.img_cb_circle_normal);
            }
        }
    }

    @Override
    public int getItemCount() {
        return sharelist == null ? 0 : sharelist.size();
    }

    public void setSelectPos(int pos) {
        selectPos = pos;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public void onClick(View view) {
        int position = (Integer) view.getTag();
        if (selectPos == position) {
            selectPos = -1;
        } else {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(view, position);
            }
        }
        if (type != 0) {
            return;
        }
        selectPos = position;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public class ShareParkUnitHolderView extends RecyclerView.ViewHolder {
        private TextView tvNum, tvShareTime, tvFee;
        private ImageView ivCheck;
        private RelativeLayout rlParent;

        public ShareParkUnitHolderView(View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_unit_num);
            ivCheck = itemView.findViewById(R.id.iv_check);
            tvShareTime = itemView.findViewById(R.id.tv_share_time);
            tvFee = itemView.findViewById(R.id.tv_unit_fee);
        }
    }
}
