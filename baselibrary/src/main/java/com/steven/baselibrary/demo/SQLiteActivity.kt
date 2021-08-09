package com.steven.baselibrary.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.steven.baselibrary.R
import com.steven.baselibrary.datapersistence.sqlite.SQLiteUtil

class SQLiteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_s_q_lite)
        SQLiteUtil.getInstance()
    }

    fun addData(view: View) {
        SQLiteUtil.getInstance().insertData()
    }

    fun deleteData(view: View) {
        SQLiteUtil.getInstance().deleteData()
    }

    fun updateData(view: View) {
        SQLiteUtil.getInstance().updateData()
    }

    fun queryData(view: View) {
        SQLiteUtil.getInstance().queryData()
    }
}
