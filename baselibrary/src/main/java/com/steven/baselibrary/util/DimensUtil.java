package com.steven.baselibrary.util;

import android.util.TypedValue;

import com.steven.baselibrary.IApplication;

/**
 * author: zhoufan
 * data: 2021/7/12 8:44
 * content:sp,dp,px之间的互相转换
 */
public class DimensUtil {

    /**
     * sp转px
     */
    public static float sp2px(float value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,value, IApplication.getContext().getResources().getDisplayMetrics());
    }

    /**
     * dp/dip转px
     */
    public static float dp2px(float value){
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,value, IApplication.getContext().getResources().getDisplayMetrics());
    }
}
