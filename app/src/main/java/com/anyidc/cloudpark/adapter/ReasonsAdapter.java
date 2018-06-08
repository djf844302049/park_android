package com.anyidc.cloudpark.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

import java.util.List;

public class ReasonsAdapter extends RecyclerView.Adapter<ReasonsAdapter.ReasonViewHolder> {
    private List<String> reasons;

    public ReasonsAdapter(List<String> reasons) {
        this.reasons = reasons;
    }

    @Override
    public ReasonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_bottom_choice, parent, false);
        return new ReasonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReasonViewHolder holder, int position) {
        holder.tvReason.setText(reasons.get(position));
    }

    @Override
    public int getItemCount() {
        return reasons == null ? 0 : reasons.size();
    }

    class ReasonViewHolder extends RecyclerView.ViewHolder {
        TextView tvReason;

        public ReasonViewHolder(View itemView) {
            super(itemView);
            tvReason = itemView.findViewById(R.id.tv_bottom_choice);
        }
    }
}
