package com.anyidc.cloudpark.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.andview.refreshview.XRefreshView;
import com.anyidc.cloudpark.R;

/**
 * Created by linwenxiong on 2018/3/13.
 */

public class AppointmentCompleteFragment extends LazyBaseFragment {
    private XRefreshView xRefreshView;
    private RecyclerView recyclerView;

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
    }
}
