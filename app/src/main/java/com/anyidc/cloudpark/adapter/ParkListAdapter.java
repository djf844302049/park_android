package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.ParkSearchBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class ParkListAdapter extends RecyclerView.Adapter<ParkListAdapter.ParkListViewHolder> implements View.OnClickListener {

    private List<ParkSearchBean.ParkBean> list;
    private ItemClickListener mItemClickListener;

    public ParkListAdapter(List<ParkSearchBean.ParkBean> list) {
        this.list = list;
    }

    public ParkSearchBean.ParkBean getPark(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public ParkListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_park_list, parent, false);
        return new ParkListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParkListViewHolder holder, int position) {
        ParkSearchBean.ParkBean parkBean = list.get(position);
        if (parkBean.getType() == 2) {
            holder.ivLogo.setVisibility(View.VISIBLE);
        } else {
            holder.ivLogo.setVisibility(View.GONE);
        }
        holder.tvParkName.setText(parkBean.getParking_name());
        holder.tvAddress.setText(parkBean.getAddress());
        holder.tvTotalNum.setText(String.valueOf(parkBean.getNum()));
        holder.tvRemainNum.setText(String.valueOf(parkBean.getAvailable_num()));
        holder.tvDistance.setText("距我" + parkBean.getDistance() + "km");
        holder.itemView.setTag(position);
    }

    public void notifyDataChanged() {
        try {
            for (ParkSearchBean.ParkBean bean : list) {
                if (bean.getType() == 0) {
                    list.remove(bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ParkListViewHolder extends RecyclerView.ViewHolder {

        TextView tvParkName, tvAddress, tvTotalNum, tvRemainNum, tvDistance;
        ImageView ivLogo;

        public ParkListViewHolder(View itemView) {
            super(itemView);
            tvParkName = itemView.findViewById(R.id.tv_park_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvTotalNum = itemView.findViewById(R.id.tv_total_num);
            tvRemainNum = itemView.findViewById(R.id.tv_remain_num);
            tvDistance = itemView.findViewById(R.id.tv_distance);
            ivLogo = itemView.findViewById(R.id.iv_share_logo);
            itemView.setOnClickListener(ParkListAdapter.this);
        }
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(view, position);
        }
    }

    public void setOnItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    /**
     * 创建一个回调接口
     */
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
