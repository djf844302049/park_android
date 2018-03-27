package com.anyidc.cloudpark.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.TimeBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.SpUtils;
import com.trello.rxlifecycle2.components.support.RxFragment;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by linwenxiong on 2018/3/13.
 */

public abstract class LazyBaseFragment<T> extends RxFragment {
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

    public void getTime(Observable<BaseEntity<T>> observable, RxObserver<BaseEntity<T>> observer) {
        Api.getDefaultService()
                .getTime()
                .compose(this.bindToLifecycle())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new RxObserver<BaseEntity<TimeBean>>(getActivity(), false) {

                    @Override
                    public void onSuccess(BaseEntity<TimeBean> timeBeanBaseEntity) {
                        SpUtils.set(SpUtils.TIME, timeBeanBaseEntity.getData().getTime());
                        observable.compose(LazyBaseFragment.this.bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(observer);
                    }
                });
    }

}
