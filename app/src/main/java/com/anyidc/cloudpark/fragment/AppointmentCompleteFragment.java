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
import com.anyidc.cloudpark.moduel.MessageBean;
import com.anyidc.cloudpark.moduel.MyAppointmentBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by linwenxiong on 2018/3/13.
 */

public class AppointmentCompleteFragment extends LazyBaseFragment {
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;
    private AppointmentRecordAdapter adapter;
    private int page = 1;

    @Override
    protected void inflaterLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.appointment_complete_layout,null,false);
    }

    @Override
    protected void onLazyLoad() {
        xRefreshView.startRefresh();
    }

    @Override
    protected void initView() {
        xRefreshView = layout.findViewById(R.id.xrv_appointment_record);
        recyclerView = layout.findViewById(R.id.rv_appointment_record);
        adapter = new AppointmentRecordAdapter(getActivity());
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(lm);
        recyclerView.setAdapter(adapter);
        initXRefreshView();
    }

    private void initXRefreshView() {
        xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh(boolean isPullDown) {
                page = 1;
                xRefreshView.setPullLoadEnable(true);
                getMyAppointmentIng();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                getMyAppointmentIng();
            }
        });
    }

    private void getMyAppointmentIng(){
        getTime(Api.getDefaultService().getAppointment("0",String.valueOf(page),"10")
                , new RxObserver<BaseEntity<MyAppointmentBean>>(getActivity(), true) {
                    @Override
                    public void onSuccess(BaseEntity<MyAppointmentBean> appointmentBean) {
                        MyAppointmentBean data = appointmentBean.getData();
                        if (data != null && data.getList() != null) {
                            if (page == 1) {
                                xRefreshView.stopRefresh();
                                adapter.clear();
                            } else {
                                xRefreshView.stopLoadMore();
                            }
                            if (data.getTotal() < 10) {
                                xRefreshView.setPullLoadEnable(false);
                            }
                            page = data.getPage_num() + 1;
                            List<MyAppointmentBean.AppointmentBean> order = data.getList();
                            adapter.addList(order);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(String errMsg) {
                        super.onError(errMsg);
                        if (page == 1) {
                            xRefreshView.stopRefresh();
                        } else {
                            xRefreshView.stopLoadMore();
                        }
                    }
                });
    }
}
