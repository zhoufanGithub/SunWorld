package com.steven.baselibrary.retrofit;

import retrofit2.Converter;

/**
 * author: zhoufan
 * data: 2021/7/27 15:09
 * content: 对外暴露方法供外面调用
 */
public class HttpRetrofit {

    // 设置数据读取超时时间
    public HttpRetrofit setTimeOutTime(long timeout) {
        OkHttpClientUtil.getOkHttpUtil().setTimeOutTime(timeout);
        return this;
    }

    // 设置网络连接超时时间
    public HttpRetrofit setConnectTime(long timeout) {
        OkHttpClientUtil.getOkHttpUtil().setConnectTime(timeout);
        return this;
    }

    // 设置写入服务器的超时时间
    public HttpRetrofit setWriteTime(long timeout) {
        OkHttpClientUtil.getOkHttpUtil().setWriteTime(timeout);
        return this;
    }

    // 设置拦截器
    public HttpRetrofit setIntercept(boolean isIntercept) {
        OkHttpClientUtil.getOkHttpUtil().setIntercept(isIntercept);
        return this;
    }

    // 设置BaseUrl
    public HttpRetrofit setBaseUrl(String baseUrl) {
        RetrofitClientUtil.getRetrofitUtil().setBaseUrl(baseUrl);
        return this;
    }

    // 设置数据解析
    public HttpRetrofit addConverterFactory(Converter.Factory factory) {
        RetrofitClientUtil.getRetrofitUtil().addConverterFactory(factory);
        return this;
    }

    /**
     * 生成请求接口的实例
     * @return ApiService
     */
    public ApiService getApiManager(){
        return RetrofitClientUtil.getRetrofitUtil().setOkHttpClient(OkHttpClientUtil.getOkHttpUtil().build()).build().create(ApiService.class);
    }
}
