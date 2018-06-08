package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.OptReasonBean;

import java.util.List;

public class ReasonsAdapter extends RecyclerView.Adapter<ReasonsAdapter.ReasonViewHolder> implements View.OnClickListener {
    private List<OptReasonBean> reasons;
    private ItemClickListener mItemClickListener;

    public ReasonsAdapter(List<OptReasonBean> reasons) {
        this.reasons = reasons;
    }

    @Override
    public ReasonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bottom_choice, parent, false);
        return new ReasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReasonViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.tvReason.setText(reasons.get(position).getReason_name());
    }

    @Override
    public int getItemCount() {
        return reasons == null ? 0 : reasons.size();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(v, position);
        }
    }

    class ReasonViewHolder extends RecyclerView.ViewHolder {
        TextView tvReason;

        public ReasonViewHolder(View itemView) {
            super(itemView);
            tvReason = itemView.findViewById(R.id.tv_bottom_choice);
            itemView.setOnClickListener(ReasonsAdapter.this);
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void setOnItemClickListener(ItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }
}
