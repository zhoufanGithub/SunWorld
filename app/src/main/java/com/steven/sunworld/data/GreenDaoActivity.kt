package com.steven.sunworld.data

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.steven.sunworld.R
import kotlinx.android.synthetic.main.activity_green_dao.*

class GreenDaoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_green_dao)
    }

    fun addData(view: View) {
        val personInfo = PersonInfo(null, 1, "时间简史", "大卫", "一部好书", "www.baidu.com")
        val personInfoTwo = PersonInfo(null, 2, "历史简史", "贝壳蚝姆", "一部好书", "www.baidu.com")
        GreenDaoController.getInstance(this).insertOrReplace(personInfo)
        GreenDaoController.getInstance(this).insertOrReplace(personInfoTwo)
    }

    fun queryData(view: View) {
        val list = GreenDaoController.getInstance(this).searchAll()
        val builder:StringBuilder = StringBuilder()
        for (personInfo in list) {
            builder.append(personInfo.bookName + personInfo.authorName + "\n")
        }
        textView.text = builder.toString()
    }

    fun updateData(view: View) {
        val personInfo = PersonInfo(null, 1, "墨菲定律", "大卫", "一部好书", "www.baidu.com")
        GreenDaoController.getInstance(this).update(personInfo)
        val list = GreenDaoController.getInstance(this).searchAll()
        val builder:StringBuilder = StringBuilder()
        for (personInfo in list) {
            builder.append(personInfo.bookName + personInfo.authorName + "\n")
        }
        textView.text = builder.toString()
    }

    fun deleteData(view: View) {
        GreenDaoController.getInstance(this).delete(1)
        val list = GreenDaoController.getInstance(this).searchAll()
        val builder:StringBuilder = StringBuilder()
        for (personInfo in list) {
            builder.append(personInfo.bookName + personInfo.authorName + "\n")
        }
        textView.text = builder.toString()
    }
}
