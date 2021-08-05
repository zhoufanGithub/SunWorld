package com.steven.baselibrary.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Toast
 * Created by zhoufan
 */
public class MyToast {

    private static Toast sToast;
    private static Handler handler = new Handler(Looper.getMainLooper());

    public static void showTextToast(final Context context, final String msg) {
        if (context == null) return;
        if (sToast == null) {
            if (Looper.getMainLooper() == Looper.myLooper()) {
                initToast(context, msg);
            } else {
                handler.post(() -> initToast(context, msg));
            }
        }
        //判断当前代码是否是主线程
        if (Looper.myLooper() == Looper.getMainLooper()) {
            sToast.setText(msg);
            sToast.show();
        } else {
            handler.post(() -> {
                sToast.setText(msg);
                sToast.show();
            });
        }
    }

    private static void initToast(Context context, String msg) {
        sToast = Toast.makeText(context.getApplicationContext(), msg, Toast.LENGTH_SHORT);
        sToast.setText(msg);
    }

    public static void showSortToast(Context context, String msg) {
        if (!TextUtils.isEmpty(msg)) {
            showTextToast(context, msg);
        }
    }

    public static void showCenterSortToast(Context context, String msg) {
        Toast toast = Toast.makeText(context, null, Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
