package com.anyidc.cloudpark.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.anyidc.cloudpark.R;

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

    }

    @Override
    protected void initView() {

    }
}
