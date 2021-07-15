package com.steven.networklibrary

import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import okhttp3.*
import okhttp3.FormBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import okio.BufferedSink
import java.io.File
import java.io.IOException


class NetWorkActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_work)
    }

    /**
     * 普通的Get请求
     */
    fun okHttpGet(view: View) {
        val url = "http://www.baidu.com"
        // 1.创建OkHttp实例
        val okHttpClient = OkHttpClient()
        // 2.构建请求参数 默认为get()请求，可以不写
        val request = Request.Builder().url(url).get().build()
        // 3.构建请求
        val call = okHttpClient.newCall(request)
        // 4.发送请求并获取回调（enqueue为异步请求，execute为同步请求（由于会阻塞线程，不建议使用））
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("okHttp", "请求失败${e.printStackTrace()}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(
                    "okHttp",
                    response.protocol.toString() + " " + response.code + " " + response.message
                )
                val headers = response.headers
                for (i in 0 until headers.size) {
                    Log.i("okHttp", headers.name(i) + ":" + headers.value(i))
                }
                Log.i("okHttp", "onResponse: " + response.body!!.string())
            }
        })
    }

    /**
     * Post方式提交String
     */
    fun okHttpPost(view: View) {
        val url = "http://www.5mins-sun.com:8081/user/direct_login"
        //1.构建OkHttp实例
        val okHttpClient = OkHttpClient()
        //2.构建请求参数
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val map = mutableMapOf<String, Any>()
        map["phone"] = "13701659446"
        val requestBody = Gson().toJson(map)
        val request = Request.Builder().url(url).post(requestBody.toRequestBody(mediaType)).build()
        //3.构建请求
        val call = okHttpClient.newCall(request)
        //4.发送请求
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("okHttp", "请求失败${e.printStackTrace()}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(
                    "okHttp",
                    response.protocol.toString() + " " + response.code + " " + response.message
                )
                val headers = response.headers
                for (i in 0 until headers.size) {
                    Log.i("okHttp", headers.name(i) + ":" + headers.value(i))
                }
                Log.i("okHttp", "onResponse: " + response.body!!.string())
            }
        })
    }

    /**
     * Post方式提交流
     */
    fun okHttpPostStream(view: View) {
        val url = "http://www.5mins-sun.com:8081/user/direct_login"
        //1.构建OkHttp实例
        val okHttpClient = OkHttpClient()
        //2.构建请求参数
        val requestBody = object : RequestBody() {
            override fun contentType(): MediaType? {
                return "application/json; charset=utf-8".toMediaTypeOrNull()
            }

            override fun writeTo(sink: BufferedSink) {
                val map = mutableMapOf<String, Any>()
                map["phone"] = "13701659446"
                val requestBody = Gson().toJson(map)
                sink.writeUtf8(requestBody)
            }

        }
        val request = Request.Builder().url(url).post(requestBody).build()
        //3.构建请求
        val call = okHttpClient.newCall(request)
        //4.发送请求
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("okHttp", "请求失败${e.printStackTrace()}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(
                    "okHttp",
                    response.protocol.toString() + " " + response.code + " " + response.message
                )
                val headers = response.headers
                for (i in 0 until headers.size) {
                    Log.i("okHttp", headers.name(i) + ":" + headers.value(i))
                }
                Log.i("okHttp", "onResponse: " + response.body!!.string())
            }
        })
    }

    /**
     * Post提交文件
     */
    fun okHttpPostFile(view: View) {
        val url = "http://www.5mins-sun.com:8081/manage/test_save_file_by_stream"
        //1.构建OkHttp实例
        val okHttpClient = OkHttpClient()
        //2.构建请求参数
        val file = File(Environment.getExternalStorageDirectory().absolutePath + "/zf.txt")
        val mediaType = "application/octet-stream".toMediaTypeOrNull()
        val request = Request.Builder().url(url).post(RequestBody.create(mediaType, file)).build()
        //3.构建请求
        val call = okHttpClient.newCall(request)
        //4.发送请求
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("okHttp", "请求失败${e.printStackTrace()}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(
                    "okHttp",
                    response.protocol.toString() + " " + response.code + " " + response.message
                )
                val headers = response.headers
                for (i in 0 until headers.size) {
                    Log.i("okHttp", headers.name(i) + ":" + headers.value(i))
                }
                Log.i("okHttp", "onResponse: " + response.body!!.string())
            }
        })
    }

    /**
     * Post提交表单
     */
    fun okHttpPostForm(view: View) {
        val url = "http://www.5mins-sun.com:8081/manage/test_save_file"
        //1.构建OkHttp实例
        val okHttpClient = OkHttpClient()
        //2.构建请求参数
        val file = File(Environment.getExternalStorageDirectory().absolutePath + "/zhoufn.txt")
        val mediaType = "application/octet-stream".toMediaTypeOrNull()
        val fileBody = RequestBody.create(mediaType, file)
        val requestBody = MultipartBody.Builder().setType(MultipartBody.FORM)
            .addFormDataPart("files", file.name, fileBody).build()
        val request = Request.Builder().url(url).post(requestBody).build()
        //3.构建请求
        val call = okHttpClient.newCall(request)
        //4.发送请求
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.i("okHttp", "请求失败${e.printStackTrace()}")
            }

            override fun onResponse(call: Call, response: Response) {
                Log.i(
                    "okHttp",
                    response.protocol.toString() + " " + response.code + " " + response.message
                )
                val headers = response.headers
                for (i in 0 until headers.size) {
                    Log.i("okHttp", headers.name(i) + ":" + headers.value(i))
                }
                Log.i("okHttp", "onResponse: " + response.body!!.string())
            }
        })
    }
}
