package com.anyidc.cloudpark.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.anyidc.cloudpark.R;

/**
 * Created by Administrator on 2018/4/13.
 */

public class SelectUnitParkDialog extends BaseDialog {
    private Context context;
    private String unitNum,des,shareTime,appointmentTime,pFee;
    private TextView tvUnitNum,tvDes,tvShareTime,tvAppointmentTime,tvPFee,tvConfirm;
    private LinearLayout llShareTime,llAppointmentTime,llPFee;
    public SelectUnitParkDialog(Context context,String unitNum,String des,String shareTime,String appointmentTime,String pFee) {
        super(context);
        this.unitNum = unitNum;
        this.des = des;
        this.shareTime = shareTime;
        this.appointmentTime = appointmentTime;
        this.pFee = pFee;
        this.context = context;
        initView();
    }
    public SelectUnitParkDialog(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public void setText(String unitNum,String des,String shareTime,String appointmentTime,String pFee){
        this.unitNum = unitNum;
        this.des = des;
        this.shareTime = shareTime;
        this.appointmentTime = appointmentTime;
        this.pFee = pFee;
        updateView();
    }

    private void updateView(){

        tvUnitNum.setText(unitNum);
        tvDes.setText(des);

        if(TextUtils.isEmpty(shareTime)){
            llShareTime.setVisibility(View.GONE);
        }else{
            llShareTime.setVisibility(View.VISIBLE);
            tvShareTime.setText(shareTime);
        }
        if(TextUtils.isEmpty(appointmentTime)){
            llAppointmentTime.setVisibility(View.GONE);
        }else{
            llAppointmentTime.setVisibility(View.VISIBLE);
            tvAppointmentTime.setText(appointmentTime);
        }
        if(TextUtils.isEmpty(pFee)){
            llPFee.setVisibility(View.GONE);
        }else{
            llPFee.setVisibility(View.VISIBLE);
            tvPFee.setText(pFee);
        }
    }

    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_select_unit_park_layout, null, false);
        setContentView(view);
        tvUnitNum = view.findViewById(R.id.tv_unit_num);
        tvDes = view.findViewById(R.id.tv_unit_status_des);
        tvShareTime = view.findViewById(R.id.tv_share_time);
        tvAppointmentTime = view.findViewById(R.id.tv_appointment_time);
        tvPFee = view.findViewById(R.id.tv_p_fee);
        tvConfirm = view.findViewById(R.id.tv_confirm);
        llShareTime = view.findViewById(R.id.ll_share_time);
        llAppointmentTime = view.findViewById(R.id.ll_appointment_time);
        llPFee = view.findViewById(R.id.ll_p_fee);

        tvUnitNum.setText(unitNum);
        tvDes.setText(des);
        if(TextUtils.isEmpty(shareTime)){
            view.findViewById(R.id.ll_share_time).setVisibility(View.GONE);
        }else{
            view.findViewById(R.id.ll_share_time).setVisibility(View.VISIBLE);
            tvShareTime.setText(shareTime);
        }
        if(TextUtils.isEmpty(appointmentTime)){
            view.findViewById(R.id.ll_appointment_time).setVisibility(View.GONE);
        }else{
            view.findViewById(R.id.ll_appointment_time).setVisibility(View.VISIBLE);
            tvAppointmentTime.setText(appointmentTime);
        }
        if(TextUtils.isEmpty(pFee)){
            view.findViewById(R.id.ll_p_fee).setVisibility(View.GONE);
        }else{
            view.findViewById(R.id.ll_p_fee).setVisibility(View.VISIBLE);
            tvPFee.setText(pFee);
        }

        tvConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }


}
