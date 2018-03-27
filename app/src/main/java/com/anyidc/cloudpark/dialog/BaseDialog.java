package com.anyidc.cloudpark.dialog;

import android.app.Dialog;
import android.content.Context;

import com.anyidc.cloudpark.R;


/**
 * Created by wangzhengshan on 2016/12/7.
 */
public class BaseDialog extends Dialog {
    protected Context mContext;
    public BaseDialog(Context context) {
        super(context, R.style.my_dialog_styles);
        mContext = context;
    }
}
