package com.anyidc.cloudpark;

import android.app.Activity;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.multidex.MultiDexApplication;

import com.anyidc.cloudpark.utils.CacheData;
import com.anyidc.cloudpark.utils.LoginUtil;
import com.anyidc.cloudpark.utils.SpUtils;
import com.bumptech.glide.Glide;
import com.squareup.leakcanary.LeakCanary;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2018/2/1.
 */

public class BaseApplication extends MultiDexApplication {
    public static Context appContext;
    private static BaseApplication instance;

    public static BaseApplication getInstance() {
        return instance;
    }

    private ArrayList<Activity> activityList = new ArrayList<>();

    /**
     * 添加一个activity
     *
     * @param act
     */
    public void addActivity(Activity act) {
        activityList.add(act);
    }

    /**
     * 去掉一个activity
     *
     * @param act
     */
    public void removeActivity(Activity act) {
        activityList.remove(act);
    }

    /**
     * 退出应用程序
     */
    public void exitApp() {
        int size = activityList.size();
        for (int i = 0; i < size; i++) {
            Activity activity = activityList.get(i);
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
            }
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (instance == null) {
            instance = this;
        }
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        appContext = this;
        JPushInterface.init(this);
        if (LoginUtil.isLogin()) {
            if (CacheData.getIsManager() == 1) {
                JPushInterface.setAlias(this, 0, String.valueOf(CacheData.getInfoBean().getUser_id()));
            } else {
                JPushInterface.setAlias(this, 0, CacheData.getInfoBean().getMobile());
            }
        }
        //获取设备号
        String did = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        SpUtils.set(SpUtils.DID, did);
        //获取软件版本号与版本名
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            int versionCode = info.versionCode;
            String versionName = info.versionName;
            SpUtils.set(SpUtils.VERSION_CODE, versionCode);
            SpUtils.set(SpUtils.VERSION_NAME, versionName);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            Glide.get(this).clearMemory();
        }
        Glide.get(this).trimMemory(level);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Glide.get(this).clearMemory();
    }
}
