package com.steven.baselibrary.retrofit;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by zhoufan on 2018/1/3.
 * Retrofit的请求
 */

public interface ApiService {

    // baseUrl = http://www.5mins-sun.com:8081/

    // 例如：http://www.baidu.com不使用baseUrl
    @GET
    Observable<String> getPath(@Url String url);

    // GET请求(无参) api = news
    // http://www.5mins-sun.com:8081/news
    @GET("{api}")
    Observable<String> getData(@Path("api") String api);

    // GET请求(带参) api = news
    // http://www.5mins-sun.com:8081/news?name=admin&pwd=123456
    @GET("{api}")
    Observable<String> getData(@Path("api") String api, @QueryMap TreeMap<String, Object> map);

    // POST请求(无参)
    @POST("{api}")
    Observable<String> postData(@Path(value = "api", encoded = true) String api);

    // POST请求,以RequestBody方式提交  api = news
    // http://www.5mins-sun.com:8081/news
    // RequestBody:
    // {
    //    "albumID": 2,
    //    "sectionID": 16
    // }
    @POST("{api}")
    Observable<String> postData(@Path(value = "api", encoded = true) String api, @Body RequestBody requestBody);

    // Post请求,以表单方式提交
    // http://www.5mins-sun.com:8081/news
    // user=admin
    // pwd=123456
    @FormUrlEncoded
    @POST("{api}")
    Observable<ResponseBody> postData(@Path("api") String api, @FieldMap Map<String, String> maps);

    // Post请求（带数组）
    @FormUrlEncoded
    @POST("{api}")
    Observable<ResponseBody> postData(@Path("api") String api, @FieldMap Map<String, String> maps, @Query("meta[]") String... linked);

    // 上传单个文件
    @Multipart
    @POST("{api}/")
    Observable<String> upload(@Path("api") String api, @PartMap Map<String, RequestBody> maps, @Part MultipartBody.Part file);

    // 上传多个文件
    @Multipart
    @POST("{api}/")
    Observable<String> uploadMultipart(@Path("api") String api, @PartMap Map<String, RequestBody> maps, @Part List<MultipartBody.Part> params);

}
