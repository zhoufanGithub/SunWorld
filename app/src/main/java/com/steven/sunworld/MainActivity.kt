package com.steven.sunworld

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.pm.PermissionInfoCompat
import com.steven.lbslibrary.LBSActivity
import com.steven.materialdesignlibrary.MaterialDesignerActivity
import com.steven.networklibrary.NetWorkActivity
import kotlinx.android.synthetic.main.activity_main.*

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
}
