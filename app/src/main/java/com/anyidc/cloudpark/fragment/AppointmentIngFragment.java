package com.anyidc.cloudpark.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.MyAppointmentBean;
import com.anyidc.cloudpark.moduel.MyShareBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

/**
 * Created by linwenxiong on 2018/3/13.
 */

public class AppointmentIngFragment extends LazyBaseFragment {
    @Override
    protected void inflaterLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.appointment_ing_layout,null,false);
    }

    @Override
    protected void onLazyLoad() {
        getMyAppointmentIng();
    }

    private void getMyAppointmentIng(){
//        getTime(Api.getDefaultService().getMyshare()
//                , new RxObserver<BaseEntity<MyAppointmentBean>>(getActivity(), true) {
//                    @Override
//                    public void onSuccess(BaseEntity<MyAppointmentBean> appointmentBean) {
//                        MyAppointmentBean data = appointmentBean.getData();
//                        if (data != null) {
////                            adapter.updateList(data.getList());
////                            adapter.notifyDataSetChanged();
//                        }
//                    }
//                });
    }

    @Override
    protected void initView() {

    }
}
