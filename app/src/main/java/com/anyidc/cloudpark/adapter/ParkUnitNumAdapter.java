package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.ParkUnitInfoBean;

import java.util.ArrayList;

/**
 * 选择车位，自营车场的车位编码迭代器
 * Created by Administrator on 2018/4/11.
 */

public class ParkUnitNumAdapter extends RecyclerView.Adapter<ParkUnitNumAdapter.UnitNumHolderView> implements View.OnClickListener{
    private Context context;
    private LayoutInflater mInflater;
    private ArrayList<ParkUnitInfoBean> dataList;
    private ItemClickListener itemClickListener;
    private int selectPos = -1;
    public ParkUnitNumAdapter(Context context,ArrayList<ParkUnitInfoBean> dataList){
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.dataList = dataList;
    }

    @Override
    public UnitNumHolderView onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.unit_num_item_layout,null,false);
        return new UnitNumHolderView(itemView);
    }

    @Override
    public void onBindViewHolder(UnitNumHolderView holder, int position) {
        ParkUnitInfoBean unitInfoBean = dataList.get(position);
        if(unitInfoBean != null) {
            holder.itemView.setTag(position);
            holder.tvNum.setText(unitInfoBean.getUnit_id());
            if(unitInfoBean.getStatus() == 1 && unitInfoBean.getFrozen_time() == 0){
                holder.tvNum.setBackgroundColor(ContextCompat.getColor(context,R.color.white));
            }else{
                holder.tvNum.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_line));
            }
            holder.itemView.setOnClickListener(this);
            if(selectPos == position){
                holder.rlParent.setBackgroundColor(ContextCompat.getColor(context,R.color.text_color_red));
            }else{
                holder.rlParent.setBackgroundColor(ContextCompat.getColor(context,R.color.gray_line));
            }
        }
    }

    @Override
    public int getItemCount() {
        return dataList == null?0:dataList.size();
    }

    @Override
    public void onClick(View view) {
        int position = (Integer)view.getTag();
        if(selectPos == position){
            selectPos = -1;
        }else{
            if(itemClickListener != null){
                itemClickListener.onItemClick(view,position);
            }
        }
        ParkUnitInfoBean unitInfoBean = dataList.get(position);
        if(unitInfoBean != null && unitInfoBean.getStatus() == 1 && unitInfoBean.getFrozen_time() == 0){
            selectPos = position;
        }
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }


    public class UnitNumHolderView extends RecyclerView.ViewHolder{
        private TextView tvNum;
        private RelativeLayout rlParent;
        public UnitNumHolderView(View itemView){
            super(itemView);
            tvNum = itemView.findViewById(R.id.tv_unit_num);
            rlParent = itemView.findViewById(R.id.rl_parent);
        }
    }

}
