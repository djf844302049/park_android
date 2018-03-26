package com.anyidc.cloudpark.fragment;

import com.anyidc.cloudpark.activity.BaseActivity;
import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.moduel.TimeBean;
import com.anyidc.cloudpark.network.Api;
import com.anyidc.cloudpark.network.RxObserver;
import com.anyidc.cloudpark.utils.SpUtils;
import com.trello.rxlifecycle2.components.RxFragment;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/2/1.
 */

public class BaseFragment<T> extends RxFragment {
    public BaseFragment(){

    }
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
                        observable.compose(BaseFragment.this.bindToLifecycle())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeWith(observer);
                    }
                });
    }
}
