package com.anyidc.cloudpark.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/3/27.
 */

public class ConfirmCancelDialog extends BaseDialog implements View.OnClickListener{
    private TextView tvTitle,tvContent,tvConfirm,tvCancel;
    private String title,content,confirm,cancel;
    private ClickListener listener;
    public ConfirmCancelDialog(Context context) {
        super(context);
    }
    public ConfirmCancelDialog(Context context,String title,String content,String confirm,String cancel) {
        super(context);
        this.title = title;
        this.content = content;
        this.confirm = confirm;
        this.cancel = cancel;
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(mContext).inflate(R.layout.confirm_dialog_layout,null,false);
        setContentView(view);
        tvTitle = view.findViewById(R.id.tv_dialog_title);
        tvContent = view.findViewById(R.id.tv_dialog_content);
        tvConfirm = view.findViewById(R.id.tv_dialog_confirm);
        tvCancel = view.findViewById(R.id.tv_dialog_cancel);
        if(TextUtils.isEmpty(title)){
            tvTitle.setVisibility(View.GONE);
        }else{
            tvTitle.setVisibility(View.VISIBLE);
        }
        if(TextUtils.isEmpty(cancel)){
            tvCancel.setVisibility(View.GONE);
        }else{
            tvCancel.setVisibility(View.VISIBLE);
        }

        tvConfirm.setOnClickListener(this);
        tvCancel.setOnClickListener(this);

        tvTitle.setText(title);
        tvContent.setText(content);
        tvConfirm.setText(confirm);
        tvCancel.setText(cancel);
    }

    public void setContent(String content){
        this.content = content;
        if(tvContent != null){
            tvContent.setText(content);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_dialog_confirm:
                if(listener != null){
                    listener.confirm();
                }
                break;
            case R.id.tv_dialog_cancel:
                Log.e("confirmcancel","cancel click");
                if(listener != null){
                    Log.e("confirmcancel","listener != null");
                    listener.cancel();
                }
                break;
        }
    }

    public void setClickListener(ClickListener listener){
        this.listener = listener;
    }

    public interface ClickListener{
        void confirm();
        void cancel();
    }
}
