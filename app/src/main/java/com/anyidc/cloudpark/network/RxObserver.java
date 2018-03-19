package com.anyidc.cloudpark.network;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.anyidc.cloudpark.moduel.BaseEntity;
import com.anyidc.cloudpark.utils.ToastUtil;

import java.io.EOFException;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
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
    private Reference<Context> reference;

    private RxObserver() {

    }

    public RxObserver(Context context, boolean isShowDialog) {
        reference = new WeakReference<>(context);
        this.isShowDialog = isShowDialog;
        mDialog = new ProgressDialog(reference.get());
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
        onSuccess(value);
    }

    @Override
    public final void onError(Throwable e) {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
        onError(e.getMessage());
        if (e instanceof EOFException || e instanceof ConnectException || e instanceof SocketException
                || e instanceof BindException || e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
            onError("网络异常，请稍后重试！");
        } else if (e instanceof ApiException) {
            onError(e.getMessage());
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

    public abstract void onSuccess(T t);

    public void onError(String errMsg) {
        ToastUtil.showToast(errMsg, Toast.LENGTH_SHORT);
    }
}
