package com.steven.baselibrary.retrofit;


import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * author: zhoufan
 * data: 2021/7/27 14:45
 * content: 对Retrofit进行封装
 */
public class RetrofitClientUtil {

    // 网络请求的baseUrl
    private String mBaseUrl;
    // 设置数据解析器
    private Converter.Factory mBaseFactory;
    // 新增数据解析器
    private Converter.Factory mAddFactory;
    // 设置网络请求适配器
    private CallAdapter.Factory mCallFactory;
    // 设置OkHttpClient
    private OkHttpClient mOkHttpClient;

    private static volatile RetrofitClientUtil retrofitUtil;

    public static RetrofitClientUtil getRetrofitUtil() {
        if (retrofitUtil == null) {
            synchronized (RetrofitClientUtil.class) {
                if (retrofitUtil == null) {
                    retrofitUtil = new RetrofitClientUtil();
                }
            }
        }
        return retrofitUtil;
    }

    private RetrofitClientUtil() {
        mBaseUrl = HttpRequestConstants.BASE_URL;
        // 默认基础数据类型的解析
        mBaseFactory = ScalarsConverterFactory.create();
        // RxJava来处理Call返回值
        mCallFactory = RxJava3CallAdapterFactory.create();
    }

    // 设置BaseUrl
    public RetrofitClientUtil setBaseUrl(String baseUrl) {
        this.mBaseUrl = baseUrl;
        return this;
    }

    // 设置数据解析
    public RetrofitClientUtil addConverterFactory(Converter.Factory factory) {
        this.mAddFactory = factory;
        return this;
    }

    // 设置网络请求适配器
    public RetrofitClientUtil addCallAdapterFactory(CallAdapter.Factory factory) {
        this.mCallFactory = factory;
        return this;
    }

    // 设置写入服务器的超时时间
    public RetrofitClientUtil setOkHttpClient(OkHttpClient okHttpClient) {
        this.mOkHttpClient = okHttpClient;
        return this;
    }

    // 设置Build方法
    public Retrofit build() {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(mBaseUrl);
        builder.addConverterFactory(mBaseFactory);
        if (mAddFactory!=null){
            builder.addConverterFactory(mAddFactory);
        }
        builder.addCallAdapterFactory(mCallFactory);
        builder.client(mOkHttpClient);
        return builder.build();
    }
}
