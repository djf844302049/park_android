package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.MyCarBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/1.
 */

public class CarListAdapter extends RecyclerView.Adapter<CarListAdapter.CarListViewHolder>
        implements View.OnClickListener {

    private Context context;
    private List<MyCarBean> list;
    private ItemClickListener mItemClickListener;

    public CarListAdapter(Context context, List<MyCarBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public CarListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_my_car, parent, false);
        return new CarListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CarListViewHolder holder, int position) {
        holder.itemView.setTag(position);
        MyCarBean myCarBean = list.get(position);
        holder.tvCarNum.setText(myCarBean.getCar_no());
        switch (myCarBean.getStatus()) {
            case 0:
                holder.tvCarState.setText("未认证");
                break;
            case 1:
                holder.tvCarState.setText("认证中");
                break;
            case 2:
                holder.tvCarState.setText("已认证");
                break;
            case 3:
                holder.tvCarState.setText("认证失败");
                break;
        }
        if (myCarBean.isEdit()) {
            holder.ivDelete.setVisibility(View.VISIBLE);
        } else {
            holder.ivDelete.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onClick(View view) {
        int position = (int) view.getTag();
        if (mItemClickListener != null)
            mItemClickListener.onItemClick(view, position);
    }

    class CarListViewHolder extends RecyclerView.ViewHolder {
        private TextView tvCarNum;
        private TextView tvCarState;
        private ImageView ivDelete;

        public CarListViewHolder(View itemView) {
            super(itemView);
            tvCarNum = itemView.findViewById(R.id.tv_car_num);
            tvCarState = itemView.findViewById(R.id.tv_car_state);
            ivDelete = itemView.findViewById(R.id.iv_delete_car);
            itemView.setOnClickListener(CarListAdapter.this);
        }
    }

    public void setOnItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
