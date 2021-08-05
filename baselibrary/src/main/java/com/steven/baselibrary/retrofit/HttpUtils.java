package com.steven.baselibrary.retrofit;

import android.content.Context;

import java.io.File;
import java.util.List;
import java.util.TreeMap;

/**
 * author: zhoufan
 * data: 2021/7/29 9:57
 * content: 最终对外暴露的类
 */
public class HttpUtils {

    private static IHttpRequest mHttpRequest;

    /**
     * 生成唯一实例
     */
    private static volatile HttpUtils mInstance;

    public static HttpUtils getInstance(){
        if (mInstance==null){
            synchronized (HttpUtils.class){
                if (mInstance==null){
                    mInstance = new HttpUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 初始化决定使用哪一套网络请求框架
     * @param httpRequest  默认为OkHttp + retrofit + rxJava
     */
    public static void setInitHttpRequest(IHttpRequest httpRequest){
        mHttpRequest = httpRequest;
    }

    /**
     * 更换使用的框架
     * @param httpRequest  重新设置的网络框架
     * @return
     */
    public HttpUtils httpRequest(IHttpRequest httpRequest){
        mHttpRequest = httpRequest;
        return this;
    }

    /**
     * Get请求（使用Path形式）
     * @param context   上下文
     * @param url       请求的url地址，不使用baseUrl
     * @param type      请求的标识
     * @param callBack  结果返回接口
     */
    public void executePathGet(Context context, String url, int type, HttpRequestCallback callBack){
        mHttpRequest.mHttpGetPath(context,url,type,callBack);
    }

    /**
     * GET请求(无参)
     * @param context     上下文
     * @param api         请求的api
     * @param type        请求的标识
     * @param callBack    结果返回接口
     */
    public void executeGet(Context context, String api, int type, HttpRequestCallback callBack){
        mHttpRequest.mHttpGet(context,api,type,callBack);
    }

    /**
     * Get请求(带参)
     * @param context    上下文
     * @param api        请求的api
     * @param map        请求的参数
     * @param type       请求的标识
     * @param callBack   结果返回接口
     */
    public void executeGet(Context context, String api, TreeMap map, int type, HttpRequestCallback callBack){
        mHttpRequest.mHttpGet(context,api,map,type,callBack);
    }

    /**
     * Post请求(无参)
     * @param context    上下文
     * @param api        请求的api
     * @param type       请求的标识
     * @param callBack   结果返回接口
     */
    public void executePost(Context context, String api, int type, HttpRequestCallback callBack){
        mHttpRequest.mHttpPost(context,api,type,callBack);
    }

    /**
     * Post请求(带参)
     * 以RequestBody方式提交
     * @param context         上下文
     * @param api             请求的api
     * @param map             请求的参数
     * @param type            请求的标识
     * @param callBack        结果返回接口
     */
    public void executePost(Context context, String api, TreeMap map, int type, HttpRequestCallback callBack){
        mHttpRequest.mHttpPost(context,api,map,type,callBack);
    }

    /**
     * Post请求(包含数组)
     * @param context    上下文
     * @param api        请求的api
     * @param treeMap    请求的参数
     * @param data       请求的数组
     * @param type       请求的标识
     * @param callBack   结果返回接口
     */
    public void executePost(Context context, String api, TreeMap treeMap, String[] data, int type, HttpRequestCallback callBack){
        mHttpRequest.mHttpPost(context,api,treeMap,data,type,callBack);
    }

    /**
     * 单文件上传
     * @param context    上下文
     * @param api        请求的api
     * @param file       上传的文件
     * @param fileKey    上传的文件对应的key
     * @param map        请求的参数
     * @param type       请求的标识
     * @param callBack   结果返回接口
     */
    public void executeFile(Context context, String api, File file, String fileKey, TreeMap map, int type, HttpRequestCallback callBack){
        mHttpRequest.mHttpFile(context, api, file, fileKey, map, type, callBack);
    }

    /**
     * 多文件上传
     * @param context     上下文
     * @param api         请求的api
     * @param list        上传的文件集合
     * @param fileList    上传的文件集合对应的key
     * @param map         请求的参数
     * @param type        请求的标识
     * @param callBack    结果返回接口
     */
    public void executeMultiFile(Context context, String api, List<File> list, List<String> fileList, TreeMap map, int type, HttpRequestCallback callBack){
        mHttpRequest.mHttpMultiFile(context, api, list, fileList, map, type, callBack);
    }
}
