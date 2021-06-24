package com.steven.baselibrary.util;

import android.app.Activity;
import android.app.Application;
import android.content.ComponentCallbacks;
import android.content.res.Configuration;
import android.util.DisplayMetrics;

import androidx.annotation.NonNull;

/**
 * author: zhoufan
 * data: 2021/6/24 14:35
 * content: 采用今日头条的理念进行屏幕适配
 */
public class ScreenAdaptationUtil {

    private static float sComponentDensity;
    private static float sComponentScaleDensity;

    public static void setCustomDensity(Activity activity, Application application, int defaultDP) {
        // 获取测量屏幕尺寸的DisplayMetrics
        final DisplayMetrics appDisplayMetrics = application.getResources().getDisplayMetrics();
        if (sComponentDensity == 0) {
            sComponentDensity = appDisplayMetrics.density;
            sComponentScaleDensity = appDisplayMetrics.scaledDensity;
            application.registerComponentCallbacks(new ComponentCallbacks() {
                @Override
                public void onConfigurationChanged(@NonNull Configuration newConfig) {
                    if (newConfig.fontScale > 0) {
                        sComponentScaleDensity = application.getResources().getDisplayMetrics().scaledDensity;
                    }
                }

                @Override
                public void onLowMemory() {

                }
            });
        }


        // 获取屏幕的总px，然后除以我们美工设计的总dp，得到我们的目标density
        final float targetDensity = appDisplayMetrics.widthPixels / defaultDP;
        final float targetScaledDensity = targetDensity * (sComponentScaleDensity / sComponentDensity);
        // 得到我们的dpi
        final int targetDensityDpi = (int) (160 * targetDensity);
        // 重新设置density和densityDpi
        appDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        appDisplayMetrics.densityDpi = targetDensityDpi;

        final DisplayMetrics activityDisplayMetrics = activity.getResources().getDisplayMetrics();
        activityDisplayMetrics.density = targetDensity;
        appDisplayMetrics.scaledDensity = targetScaledDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }
}
