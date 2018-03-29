package com.anyidc.cloudpark.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.UsualQuestionBean;

import java.util.List;

/**
 * Created by Administrator on 2018/3/29.
 */

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.QuestionViewHolder> implements View.OnClickListener {
    private List<UsualQuestionBean> list;
    private OnItemClickListener itemClickListener;

    public QuestionAdapter(List<UsualQuestionBean> list) {
        this.list = list;
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View v = LayoutInflater.from(context).inflate(R.layout.layout_item_question_list, parent, false);
        return new QuestionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        holder.itemView.setTag(position);
        UsualQuestionBean usualQuestionBean = list.get(position);
        holder.tvTitle.setText(usualQuestionBean.getTitle());
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public void onClick(View v) {
        if (itemClickListener != null) {
            itemClickListener.onClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        itemClickListener = onItemClickListener;
    }

    class QuestionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        public QuestionViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_ques_title);
            itemView.setOnClickListener(QuestionAdapter.this);
        }
    }

    public interface OnItemClickListener {
        void onClick(View v, int position);
    }
}
