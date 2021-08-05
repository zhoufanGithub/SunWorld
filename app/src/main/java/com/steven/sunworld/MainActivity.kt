package com.steven.sunworld

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.steven.baselibrary.demo.FileActivity
import com.steven.baselibrary.demo.NetWorkActivity
import com.steven.baselibrary.demo.PictureActivity
import com.steven.lbslibrary.LBSActivity
import com.steven.materialdesignlibrary.MaterialDesignerActivity
import com.steven.sunworld.data.GreenDaoActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initPermission()
    }

    private fun initPermission() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                0
            )
        }
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                1
            )
        }
    }

    fun getCurrentLBS(view: View) {
        startActivity(Intent(this@MainActivity, LBSActivity::class.java))
    }

    fun materialDesigner(view: View) {
        startActivity(Intent(this@MainActivity, MaterialDesignerActivity::class.java))
    }

    fun netWork(view: View) {
        startActivity(Intent(this@MainActivity, NetWorkActivity::class.java))
    }

    fun greenDaoClick(view: View) {
        startActivity(Intent(this@MainActivity, GreenDaoActivity::class.java))
    }

    fun fileClick(view: View) {
        startActivity(Intent(this@MainActivity, FileActivity::class.java))
    }

    fun pictureClick(view: View) {
        startActivity(Intent(this@MainActivity, PictureActivity::class.java))
    }
}
