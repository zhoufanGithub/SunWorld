package com.steven.baselibrary.retrofit;

import android.content.Context;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * author: zhoufan
 * data: 2021/7/27 16:41
 * content: 对应用层暴露的调用类
 */
public class HttpRetrofitRequest extends HttpRetrofit implements IHttpRequest {

    private ApiService apiService;
    // 是否添加通用参数
    private boolean mIsAddCommonParams;

    private volatile static HttpRetrofitRequest INSTANCES;


    public static HttpRetrofitRequest getInstances() {
        if (INSTANCES == null) {
            synchronized (HttpRetrofitRequest.class) {
                if (INSTANCES == null) {
                    INSTANCES = new HttpRetrofitRequest();
                }
            }
        }
        return INSTANCES;
    }

    private HttpRetrofitRequest() {
        apiService = getApiManager();
    }

    /**
     * 设置通用参数
     */
    public void isAddCommonParams(boolean isAddCommonParams) {
        this.mIsAddCommonParams = isAddCommonParams;
    }

    // Get请求（使用Path形式）
    @Override
    public void mHttpGetPath(Context context, String url, int type, HttpRequestCallback callBack) {
        Observable<String> observable = apiService.getPath(url);
        observable.retryWhen(new RetryFunction()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxJavaObserver(context, type, callBack));
    }

    // GET请求(无参)
    @Override
    public void mHttpGet(Context context, String api, int type, HttpRequestCallback callBack) {
        Observable<String> observable = apiService.getData(api);
        observable.retryWhen(new RetryFunction()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxJavaObserver(context, type, callBack));
    }

    // Get请求(带参)
    @Override
    public void mHttpGet(Context context, String api, TreeMap map, int type, HttpRequestCallback callBack) {
        if (mIsAddCommonParams) {
            HttpTool.getTreeCrc(map);
        }
        Observable<String> observable = apiService.getData(api, map);
        observable.retryWhen(new RetryFunction()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxJavaObserver(context, type, callBack));
    }

    // Post请求(无参)
    @Override
    public void mHttpPost(Context context, String api, int type, HttpRequestCallback callBack) {
        Observable<String> observable = apiService.postData(api);
        observable.retryWhen(new RetryFunction()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxJavaObserver(context, type, callBack));
    }

    // Post请求(带参)
    // 以RequestBody方式提交
    @Override
    public void mHttpPost(Context context, String api, TreeMap map, int type, HttpRequestCallback callBack) {
        if (mIsAddCommonParams) {
            HttpTool.getTreeCrc(map);
        }
        Gson gson = new Gson();
        String param = gson.toJson(map);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), param);
        Observable<String> observable = apiService.postData(api, requestBody);
        observable.retryWhen(new RetryFunction()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxJavaObserver(context, type, callBack));
    }

    // Post请求(包含数组)
    @Override
    public void mHttpPost(Context context, String api, TreeMap map, String[] data, int type, HttpRequestCallback callBack) {
        if (mIsAddCommonParams) {
            HttpTool.getTreeCrc(map);
        }
        Observable<String> observable = apiService.postData(api, map, data);
        observable.retryWhen(new RetryFunction()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxJavaObserver(context, type, callBack));
    }

    // 单文件上传
    @Override
    public void mHttpFile(Context context, String api, File file, String fileKey, TreeMap map, int type, HttpRequestCallback callBack) {
        // 生成单个文件
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData(fileKey, file.getName(), requestFile);

        if (mIsAddCommonParams) {
            HttpTool.getTreeCrc(map);
        }
        Map<String, RequestBody> mapValue = new HashMap<>();
        for (Object key : map.keySet()) {
            mapValue.put(key.toString(), HttpTool.convertToRequestBody(map.get(key).toString()));
        }
        Observable<String> observable = apiService.upload(api, mapValue, body);
        observable.retryWhen(new RetryFunction()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxJavaObserver(context, type, callBack));
    }

    // 多文件上传
    @Override
    public void mHttpMultiFile(Context context, String api, List<File> list, List<String> listFileName, TreeMap map, int type, HttpRequestCallback callBack) {
        //生成多个文件并添加到集合中
        List<MultipartBody.Part> params = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), list.get(i));
            MultipartBody.Part body = MultipartBody.Part.createFormData(listFileName.get(i), list.get(i).getName(), requestFile);
            params.add(body);
        }
        if (mIsAddCommonParams) {
            HttpTool.getTreeCrc(map);
        }
        Map<String, RequestBody> mapValue = new HashMap<>();
        for (Object key : map.keySet()) {
            mapValue.put(key.toString(), HttpTool.convertToRequestBody(Objects.requireNonNull(map.get(key)).toString()));
        }
        // 发送异步请求
        Observable<String> observable = apiService.uploadMultipart(api, mapValue, params);
        observable.retryWhen(new RetryFunction()).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new RxJavaObserver(context, type, callBack));
    }
}
