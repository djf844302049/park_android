package com.anyidc.cloudpark.utils;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import io.reactivex.annotations.NonNull;

public class ScreenFitUtil {
    private static float sNonCompatDensity;
    private static float sNonCompatScalaDensity;

    public static void setCustomDensity(@NonNull Activity activity, @NonNull final Application application) {
        final DisplayMetrics dm = application.getResources().getDisplayMetrics();
        if (sNonCompatDensity == 0) {
            sNonCompatDensity = dm.density;
            sNonCompatScalaDensity = dm.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(Configuration newConfig) {
                    if (newConfig != null && newConfig.fontScale > 0) {
                        sNonCompatScalaDensity = dm.scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }
        final float targetDensity = (float) (dm.widthPixels) / 360f;
        final float targetScalaDensity = targetDensity * (sNonCompatScalaDensity / sNonCompatDensity);
        final int targetDensityDpi = (int) (160 * targetDensity);
        dm.density = targetDensity;
        dm.scaledDensity = targetScalaDensity;
        dm.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDm = activity.getResources().getDisplayMetrics();
        activityDm.density = targetDensity;
        activityDm.scaledDensity = targetScalaDensity;
        activityDm.densityDpi = targetDensityDpi;
    }
}
