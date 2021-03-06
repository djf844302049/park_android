package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.ParkDetailBean;

import java.util.List;

/**
 * 选择车位，自营车场的车位编码迭代器
 * Created by Administrator on 2018/4/11.
 */

public class ParkUnitNumAdapter extends RecyclerView.Adapter<ParkUnitNumAdapter.UnitNumHolderView> implements View.OnClickListener {
    private Context context;
    private LayoutInflater mInflater;
    private List<ParkDetailBean.UseArrBean> dataList;
    private ItemClickListener itemClickListener;
    private int selectPos = -1;
    private int type;

    public ParkUnitNumAdapter(Context context, List<ParkDetailBean.UseArrBean> dataList) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public UnitNumHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.unit_num_item_layout, parent, false);
        return new UnitNumHolderView(itemView);
    }

    @Override
    public void onBindViewHolder(UnitNumHolderView holder, int position) {
        ParkDetailBean.UseArrBean unitInfoBean = dataList.get(position);
        if (unitInfoBean != null) {
            holder.itemView.setTag(position);
            holder.tvNum.setText(unitInfoBean.getUnit_id());
            if (type != 0) {
                holder.tvNum.setBackgroundColor(ContextCompat.getColor(context, R.color.gray));
                holder.tvNum.setSelected(false);
                return;
            }
            if (selectPos == -1) {
                holder.tvNum.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
                holder.tvNum.setSelected(false);
            }
            holder.itemView.setOnClickListener(this);
            if (selectPos == position) {
                holder.tvNum.setSelected(true);
                holder.tvNum.setBackgroundColor(ContextCompat.getColor(context, R.color.bg_blue));
            } else {
                holder.tvNum.setSelected(false);
                holder.tvNum.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
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

    public void setSelectPos(int pos) {
        selectPos = pos;
    }

    public void setType(int type) {
        this.type = type;
    }


    public class UnitNumHolderView extends RecyclerView.ViewHolder {
        private TextView tvNum;

        public UnitNumHolderView(View itemView) {
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_unit_num);
        }
    }

}
