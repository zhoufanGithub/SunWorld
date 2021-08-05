package com.steven.baselibrary.retrofit;

import android.content.Context;

import com.steven.baselibrary.R;
import com.steven.baselibrary.util.MyToast;

import org.json.JSONException;
import org.json.JSONObject;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import retrofit2.HttpException;

/**
 * author: zhoufan
 * data: 2021/7/27 16:59
 * content: 处理接口返回的请求结果
 */
public class RxJavaObserver implements Observer<String> {

    private HttpRequestCallback mCallBack;
    private int mType;
    private Context mContext;

    RxJavaObserver(Context context, int type, HttpRequestCallback callBack) {
        this.mCallBack = callBack;
        this.mContext = context;
        this.mType = type;
    }

    @Override
    public void onSubscribe(@NonNull Disposable d) {

    }

    /**
     * 接口请求成功
     */
    @Override
    public void onNext(@NonNull String s) {
        try {
            if (HttpTool.isJsonObject(s)) {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.has("resultStatus")) {
                    String response = jsonObject.getString("resultStatus");
                    if (response.toUpperCase().equals("SUCCESS")) {
                        if (jsonObject.has("returnData")) {
                            mCallBack.onRequestSuccess(jsonObject.getString("returnData"), mType);
                        } else {
                            if (jsonObject.has("errCode") && jsonObject.has("errMsg")) {
                                String code = jsonObject.getString("errCode");
                                String errMsg = jsonObject.getString("errMsg");
                                mCallBack.onRequestSuccess(errMsg, mType);
                            }
                        }
                    } else {
                        String errMsg = jsonObject.getString("errMsg");
                        String code = jsonObject.getString("errCode");
                        mCallBack.onRequestFail(errMsg, code, mType);
                    }
                }
            } else {
                mCallBack.onRequestSuccess(s, mType);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * 接口请求失败
     */
    @Override
    public void onError(@NonNull Throwable e) {
        // 1.检查网络设置
        if (!HttpTool.hasNetwork(mContext)) {
            MyToast.showCenterSortToast(mContext, mContext.getResources().getString(R.string.connect_error));
            onComplete();
            mCallBack.onRequestNetFail(mType);
            return;
        }
        // 2.非网络错误，接口请求错误
        mCallBack.onRequestFail(e.getMessage(), "0000", mType);
    }

    /**
     * 接口请求完成
     */
    @Override
    public void onComplete() {

    }
}
