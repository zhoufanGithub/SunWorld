package com.steven.sunworld

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.steven.baselibrary.demo.FileActivity
import com.steven.baselibrary.demo.NetWorkActivity
import com.steven.baselibrary.demo.PictureActivity
import com.steven.baselibrary.demo.SQLiteActivity
import com.steven.baselibrary.update.UpdateVersion
import com.steven.baselibrary.util.Util
import com.steven.centerlibrary.darkmode.DarkModeActivity
import com.steven.lbslibrary.LBSActivity
import com.steven.materialdesignlibrary.MaterialDesignerActivity
import com.steven.sunworld.data.GreenDaoActivity
import org.json.JSONObject

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

    fun sqLiteClick(view: View) {
        startActivity(Intent(this@MainActivity, SQLiteActivity::class.java))
    }

    fun modeClick(view: View) {
        startActivity(Intent(this@MainActivity, DarkModeActivity::class.java))
    }

    fun updateVersion(view: View) {
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            Util.openNotification(this)
        } else {
            val value =
                "{\"versionName\":\"1.13\",\"versionCode\":\"7\",\"apkUrl\":\"https://xysx-voice.oss-cn-shanghai.aliyuncs.com/system/wormhole-say-latest.apk\",\"desc\":\"1.通知功能UI界面优化\\n2.原乌托邦板块更新成为虫洞说\\n3.话题和专题内容更新\\n4.播放功能等bug修复\"}"
            val jsonObject = JSONObject(value)
            val versionName = jsonObject.getString("versionName")
            val versionCode = jsonObject.getString("versionCode")
            val apkUrl = jsonObject.getString("apkUrl")
            val desc = jsonObject.getString("desc")
            UpdateVersion(
                this,
                versionCode.toInt(),
                versionName,
                desc,
                apkUrl,
                1,
                true
            ).showUpdateDialog()
        }
    }
}
