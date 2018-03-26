package com.anyidc.cloudpark.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by linwenxiong on 2018/3/13.
 */

public abstract class LazyBaseFragment extends Fragment {
    protected boolean isPrepare = false;
    protected boolean isVisible = false;
    protected View layout;
    public LazyBaseFragment(){

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        inflaterLayout(inflater, container, savedInstanceState);
        initView();
        isPrepare = true;
        canLoad();
        return layout;
    }

    /**
     * 设置是否对用户可见
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        canLoad();
    }

    /**
     * 判断是否可以加载数据
     */
    protected void canLoad() {
        if (!isPrepare || !isVisible) {
            return;
        }
        isPrepare = false;
        onLazyLoad();
    }

    protected abstract void inflaterLayout(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected abstract void onLazyLoad();

    protected abstract void initView();

}
