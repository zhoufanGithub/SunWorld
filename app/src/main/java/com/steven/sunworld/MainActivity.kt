package com.steven.sunworld

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.steven.lbslibrary.LBSActivity

class MainActivity : AppCompatActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    /**
     * 百度定位及地图
     */
    fun getCurrentLBS(view: View) {
        startActivity(Intent(this@MainActivity,LBSActivity::class.java))
    }

}
