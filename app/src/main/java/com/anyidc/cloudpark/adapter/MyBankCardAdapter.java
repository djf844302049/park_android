package com.anyidc.cloudpark.adapter;

import android.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.activity.BaseActivity;
import com.anyidc.cloudpark.moduel.BankCardBean;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.ToastUtil;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by Administrator on 2018/3/26.
 */

public class MyBankCardAdapter extends RecyclerView.Adapter<MyBankCardAdapter.MyBankCardViewHolder> {
    private List<BankCardBean> list;
    private BaseActivity activity;
    private SwipeLayout preLayout = null;

    public MyBankCardAdapter(List<BankCardBean> list, BaseActivity activity) {
        this.list = list;
        WeakReference reference = new WeakReference(activity);
        this.activity = (BaseActivity) reference.get();
    }

    @Override
    public MyBankCardViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_my_bank_card, parent, false);
        return new MyBankCardViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyBankCardViewHolder holder, int position) {
        BankCardBean bankCardBean = list.get(position);
        holder.tvBank.setText(bankCardBean.getCardBank());
        holder.tvBankType.setText(bankCardBean.getCardBankType());
        StringBuilder builder = new StringBuilder("**** **** **** ");
        builder.append(bankCardBean.getCardLastNums());
        holder.tvBankNum.setText(builder.toString());
        holder.tvUnbindCard.setOnClickListener(view ->
                new AlertDialog.Builder(this.activity)
                        .setTitle("提示")
                        .setMessage("您确定要解绑该银行卡吗？")
                        .setPositiveButton("确定", (dialogInterface, i) -> unBindCard(bankCardBean))
                        .setNegativeButton("取消", null)
                        .create()
                        .show()
        );
        holder.swipeLayout.addSwipeListener(new SimpleSwipeListener() {

            @Override
            public void onStartOpen(SwipeLayout layout) {
                super.onStartOpen(layout);
                if (preLayout != null && preLayout != layout) {
                    preLayout.close();
                }
                preLayout = layout;
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    class MyBankCardViewHolder extends RecyclerView.ViewHolder {
        ImageView ivBankLogo;
        TextView tvBank, tvBankType, tvBankNum, tvUnbindCard;
        SwipeLayout swipeLayout;

        public MyBankCardViewHolder(View itemView) {
            super(itemView);
            ivBankLogo = itemView.findViewById(R.id.iv_bank_logo);
            tvBank = itemView.findViewById(R.id.tv_bank);
            tvBankType = itemView.findViewById(R.id.tv_bank_type);
            tvBankNum = itemView.findViewById(R.id.tv_bank_num);
            tvUnbindCard = itemView.findViewById(R.id.tv_unbind_bank_card);
            swipeLayout = itemView.findViewById(R.id.swipe_view);
        }
    }

    private void unBindCard(BankCardBean bean) {
        activity.getTime(Api.getDefaultService().unBindCard(bean.getBank_id())
                , new RxObserver<BaseEntity>(activity, true) {
                    @Override
                    public void onSuccess(BaseEntity baseEntity) {
                        ToastUtil.showToast(baseEntity.getMessage(), Toast.LENGTH_SHORT);
                        list.remove(bean);
                        notifyDataSetChanged();
                    }
                });
    }
}
