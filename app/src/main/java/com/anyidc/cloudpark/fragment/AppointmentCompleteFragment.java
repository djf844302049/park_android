package com.anyidc.cloudpark.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.anyidc.cloudpark.R;
import com.anyidc.cloudpark.adapter.AppointmentRecordAdapter;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.MyAppointmentBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

/**
 * Created by linwenxiong on 2018/3/13.
 */

public class AppointmentCompleteFragment extends LazyBaseFragment {
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private AppointmentRecordAdapter adapter;

    @Override
    protected void inflaterLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.appointment_complete_layout,null,false);
    }

    @Override
    protected void onLazyLoad() {

    }

    @Override
    protected void initView() {
        xRefreshView = layout.findViewById(R.id.xrv_appointment_record);
        recyclerView = layout.findViewById(R.id.rv_appointment_record);
        adapter = new AppointmentRecordAdapter(getActivity());
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
    }

    private void getMyAppointmentIng(){
        getTime(Api.getDefaultService().getAppointment("0","1","1")
                , new RxObserver<BaseEntity<MyAppointmentBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseEntity<MyAppointmentBean> appointmentBean) {
                        MyAppointmentBean data = appointmentBean.getData();
                        if (data != null && data.getList() != null && data.getList().size() > 0) {
                            MyAppointmentBean.AppointmentBean bean = data.getList().get(0);

                        }
                    }
                });
    }
}
