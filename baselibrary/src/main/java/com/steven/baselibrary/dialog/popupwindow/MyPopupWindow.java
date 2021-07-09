package com.steven.baselibrary.dialog.popupwindow;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.PopupWindow;

/**
 * author: zhoufan
 * data: 2021/7/8 11:33
 * content: 打造通用的PopupWindow
 */
public class MyPopupWindow extends PopupWindow {


    @Override
    public void showAsDropDown(View anchor) {
        handlerHeight(anchor);
        super.showAsDropDown(anchor);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff) {
        handlerHeight(anchor);
        super.showAsDropDown(anchor, xoff, yoff);
    }

    @Override
    public void showAsDropDown(View anchor, int xoff, int yoff, int gravity) {
        handlerHeight(anchor);
        super.showAsDropDown(anchor, xoff, yoff, gravity);
    }

    /**
     * 解决高度无法自适应的问题
     */
    private void handlerHeight(View anchor) {
        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int heightPixels = anchor.getResources().getDisplayMetrics().heightPixels;
            int h = heightPixels - rect.bottom + getStatusHeight(anchor.getContext());
            setHeight(h);
        }
    }

    /**
     * 获取状态栏的高度
     */
    private int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }
}
