package com.anyidc.cloudpark;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.multidex.MultiDexApplication;

import com.anyidc.cloudpark.utils.SpUtils;
import com.bumptech.glide.Glide;
import com.squareup.leakcanary.LeakCanary;

/**
 * Created by Administrator on 2018/2/1.
 */

public class BaseApplication extends MultiDexApplication {
    public static Context appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        LeakCanary.install(this);
        appContext = this;
        String did = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        SpUtils.set(SpUtils.DID, did);
        PackageManager manager = this.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(this.getPackageName(), 0);
            int versionCode = info.versionCode;
            SpUtils.set(SpUtils.VERSIONCODE, versionCode);
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
