package com.steven.centerlibrary

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * 所有Activity的基类
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView()
        initView()
        initData()
    }


    // 加载布局
    abstract fun setContentView()

    // 初始化View
    abstract fun initView()

    // 初始化数据
    abstract fun initData()

}
