package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.ParkSearchBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/28.
 */

public class ParkListAdapter extends RecyclerView.Adapter<ParkListAdapter.ParkListViewHolder> {

    private List<ParkSearchBean.ParkBean> list;

    public ParkListAdapter(List<ParkSearchBean.ParkBean> list) {
        this.list = list;
    }

    @Override
    public ParkListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_park_list, parent, false);
        return new ParkListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ParkListViewHolder holder, int position) {
        ParkSearchBean.ParkBean parkBean = list.get(position);
        holder.tvParkName.setText(parkBean.getParking_name());
        holder.tvAddress.setText(parkBean.getAddress());
        holder.tvTotalNum.setText(String.valueOf(parkBean.getNum()));
        holder.tvRemainNum.setText(String.valueOf(parkBean.getAvailable_num()));
        holder.tvDistance.setText("距我" + parkBean.getDistance() + "km");
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class ParkListViewHolder extends RecyclerView.ViewHolder {

        TextView tvParkName, tvAddress, tvTotalNum, tvRemainNum, tvDistance;

        public ParkListViewHolder(View itemView) {
            super(itemView);
            tvParkName = itemView.findViewById(R.id.tv_park_name);
            tvAddress = itemView.findViewById(R.id.tv_address);
            tvTotalNum = itemView.findViewById(R.id.tv_total_num);
            tvRemainNum = itemView.findViewById(R.id.tv_remain_num);
            tvDistance = itemView.findViewById(R.id.tv_distance);
        }
    }
}
