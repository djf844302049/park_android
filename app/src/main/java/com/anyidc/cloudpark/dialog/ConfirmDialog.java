package com.anyidc.cloudpark.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.anyidc.cloudpark.R;


/**
 * Created by wangzhengshan on 2016/12/7.
 */
public class ConfirmDialog extends BaseDialog {

    private String title;
    private String content;
    private TextView tvTitle;
    private TextView tvContent;
    private TextView tvConfirm;

    private Context context;
    private ConfirmClickListener clickListener;


    public void setClickListener(ConfirmClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public ConfirmDialog(Context context, String title, String content) {
        super(context);
        this.context = context;
        this.title = title;
        this.content = content;
        initView();
    }

    private void initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.confirm_dialog_layout, null, false);
        setContentView(view);
        tvTitle = view.findViewById(R.id.tv_dialog_title);
        tvContent = view.findViewById(R.id.tv_dialog_content);
        tvConfirm = view.findViewById(R.id.tv_dialog_confirm);
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        } else {
            tvTitle.setText(title);
            tvTitle.setVisibility(View.VISIBLE);
        }
        tvContent.setText(content);
        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickListener != null) {
                    dismiss();
                    clickListener.click();
                }
            }
        });
    }

    public interface ConfirmClickListener {
        void click();
    }

}
