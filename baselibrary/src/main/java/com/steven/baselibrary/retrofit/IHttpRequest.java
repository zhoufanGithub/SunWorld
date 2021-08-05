package com.steven.baselibrary.retrofit;

import android.content.Context;

import java.io.File;
import java.util.List;
import java.util.TreeMap;


/**
 * author: zhoufan
 * data: 2021/7/27 16:29
 * content: 请求接口的方式
 */
public interface IHttpRequest {

    // Get请求（使用Path形式）
    void mHttpGetPath(Context context, String url, int type, HttpRequestCallback mCallBack);

    // GET请求(无参)
    void mHttpGet(Context context, String api, int type, HttpRequestCallback mCallBack);

    // Get请求(带参)
    void mHttpGet(Context context, String api, TreeMap map, int type, HttpRequestCallback mCallBack);

    // Post请求(无参)
    void mHttpPost(Context context, String api, int type, HttpRequestCallback mCallBack);

    // Post请求(带参)
    // 以RequestBody方式提交
    void mHttpPost(Context context, String api, TreeMap map, int type, HttpRequestCallback mCallBack);

    // Post请求(包含数组)
    void mHttpPost(Context context, String api, TreeMap treeMap, String[] data, int type, HttpRequestCallback mCallBack);

    // 单文件上传
    void mHttpFile(Context context, String api, File file, String fileKey, TreeMap map, int type, HttpRequestCallback mCallBack);

    // 多文件上传
    void mHttpMultiFile(Context context, String api, List<File> list, List<String> fileList, TreeMap map, int type, HttpRequestCallback mCallBack);
}
