package com.anyidc.cloudpark.network;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.anyidc.cloudpark.moduel.BaseEntity;

import java.io.EOFException;
import java.net.BindException;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by necer on 2017/6/28.
 */

public abstract class RxObserver<T extends BaseEntity> implements Observer<T> {

    private boolean isShowDialog;
    private Dialog mDialog;
    private Context mContext;


    public RxObserver(Context context, boolean isShowDialog) {
        this.mContext = context;
        this.isShowDialog = isShowDialog;
        mDialog = new ProgressDialog(context);
        mDialog.setTitle("请稍后");

    }


    @Override
    public final void onSubscribe(Disposable d) {
        if (isShowDialog) {
            mDialog.show();
        }
    }

    @Override
    public final void onNext(T value) {
        if (value.getStatus() == 1) {
            onSuccess(value);
        } else {
            onError(value.getMessage());
        }
    }

    @Override
    public final void onError(Throwable e) {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
        if (e instanceof EOFException || e instanceof ConnectException || e instanceof SocketException || e instanceof BindException || e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
            onError("网络异常，请稍后重试！");
        } else {
            onError("未知错误！");
        }
    }

    @Override
    public final void onComplete() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }

    public abstract void onSuccess( T t);

    public void onError(String errMsg) {
        Toast.makeText(mContext, errMsg, Toast.LENGTH_SHORT).show();
    }
}
