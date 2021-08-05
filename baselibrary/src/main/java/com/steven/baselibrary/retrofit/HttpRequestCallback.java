package com.steven.baselibrary.retrofit;

/**
 * author: zhoufan
 * data: 2021/7/27 16:30
 * content: 请求接口回调
 */
public interface HttpRequestCallback {

    void onRequestNetFail(int type);

    void onRequestSuccess(String result, int type);

    void onRequestFail(String value, String failCode, int type);

}
