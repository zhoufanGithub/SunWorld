package com.steven.baselibrary.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * author: zhoufan
 * data: 2021/7/27 14:27
 * content: 对OkHttp进行封装
 */
public class OkHttpClientUtil {

    // 是否开启拦截，默认情况下开启
    private boolean mIsIntercept = true;
    // 设置数据读取超时时间
    private long mReadTimeOut = 20000;
    // 设置网络连接超时时间
    private long mConnectTimeOut = 20000;
    // 设置写入服务器的超时时间
    private long mWriteTimeOut = 20000;

    private static volatile OkHttpClientUtil okHttpUtil;

    static OkHttpClientUtil getOkHttpUtil() {
        if (okHttpUtil == null) {
            synchronized (OkHttpClientUtil.class) {
                if (okHttpUtil == null) {
                    okHttpUtil = new OkHttpClientUtil();
                }
            }
        }
        return okHttpUtil;
    }

    /**
     * 私有构造函数，保证全局唯一
     */
    private OkHttpClientUtil(){

    }

    // 设置数据读取超时时间
    OkHttpClientUtil setTimeOutTime(long timeout) {
        mReadTimeOut = timeout;
        return this;
    }

    // 设置网络连接超时时间
    OkHttpClientUtil setConnectTime(long timeout) {
        mConnectTimeOut = timeout;
        return this;
    }

    // 设置写入服务器的超时时间
    OkHttpClientUtil setWriteTime(long timeout) {
        mWriteTimeOut = timeout;
        return this;
    }

    // 设置拦截器
    OkHttpClientUtil setIntercept(boolean isIntercept) {
        this.mIsIntercept = isIntercept;
        return this;
    }

    // 设置Build方法
    public OkHttpClient build() {
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        okHttpClient.readTimeout(mReadTimeOut, TimeUnit.MILLISECONDS);
        okHttpClient.connectTimeout(mConnectTimeOut, TimeUnit.MILLISECONDS);
        okHttpClient.writeTimeout(mWriteTimeOut, TimeUnit.MILLISECONDS);
        // 默认开启请求的打印信息数据，在每次发布版本的时候可以手动关闭
        if (mIsIntercept) {
            okHttpClient.addInterceptor(new HttpRequestInterceptor());
        }
        return okHttpClient.build();
    }
}
