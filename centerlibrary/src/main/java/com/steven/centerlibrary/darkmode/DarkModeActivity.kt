package com.steven.centerlibrary.darkmode

import android.content.Intent
import android.view.View
import com.steven.baselibrary.datapersistence.IOFactoryUtil
import com.steven.centerlibrary.BaseActivity
import com.steven.centerlibrary.R


/**
 * 日夜间模式的切换
 */
class DarkModeActivity : BaseActivity() {


    override fun setContentView() {
        if (IOFactoryUtil.getIOFactoryUtil().userHandler.getBoolean("isNightMode", false)) {
            setTheme(R.style.NightTheme)
        }else{
            setTheme(R.style.DayTheme)
        }
        setContentView(R.layout.activity_dark_mode)
    }


    override fun initView() {
    }

    override fun initData() {
    }

    fun lightMode(view: View) {
        IOFactoryUtil.getIOFactoryUtil().userHandler.saveBoolean("isNightMode", false)
        startActivity(Intent(this, DarkModeActivity::class.java))
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        finish()
    }

    fun nightMode(view: View) {
        IOFactoryUtil.getIOFactoryUtil().userHandler.saveBoolean("isNightMode", true)
        startActivity(Intent(this, DarkModeActivity::class.java))
        overridePendingTransition(R.anim.activity_in, R.anim.activity_out)
        finish()
    }
}
