package com.steven.baselibrary.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.steven.baselibrary.R
import com.steven.baselibrary.retrofit.HttpRequestCallback
import com.steven.baselibrary.retrofit.HttpRetrofitRequest
import com.steven.baselibrary.retrofit.HttpUtils
import kotlinx.android.synthetic.main.activity_net_work.*
import java.util.*

/**
 * 网络测试类
 */
class NetWorkActivity : AppCompatActivity(), HttpRequestCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_work)
    }

    // Get请求（使用Path形式）
    fun requestOne(view: View) {
        HttpUtils.getInstance().executePathGet(this, "http://www.baidu.com/", 0, this)
    }

    // GET请求(无参)
    fun requestTwo(view: View) {
    }

    // Get请求(带参)
    fun requestThree(view: View) {}

    // Post请求(无参)
    fun requestFour(view: View) {
    }

    // Post请求(带参以RequestBody方式提交)
    fun requestFive(view: View) {
        val treeMap = TreeMap<String,Any>()
        HttpUtils.getInstance().executePost(this, "/app/get_version_info", treeMap,0, this)
    }

    // Post请求(包含数组)
    fun requestSix(view: View) {}

    // 单文件上传
    fun requestSeven(view: View) {}

    // 多文件上传
    fun requestEight(view: View) {}


    override fun onRequestNetFail(type: Int) {
    }

    override fun onRequestSuccess(result: String?, type: Int) {
        textView.text = result
    }

    override fun onRequestFail(value: String?, failCode: String?, type: Int) {
    }
}
