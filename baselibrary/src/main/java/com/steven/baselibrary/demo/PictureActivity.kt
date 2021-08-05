package com.steven.baselibrary.demo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.listener.OnResultCallbackListener
import com.steven.baselibrary.R
import com.steven.baselibrary.util.GlideEngine
import kotlinx.android.synthetic.main.activity_picture.*

class PictureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_picture)
    }

    /**
     * 打开拍照功能
     */
    fun openCamera(view: View) {
        PictureSelector.create(this)
            .openCamera(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .forResult(object : OnResultCallbackListener<LocalMedia?> {
                override fun onResult(result: List<LocalMedia?>) {
                    var userIcon = result[0]!!.realPath
                    if (userIcon == null) {
                        userIcon = result[0]!!.path
                    }
                    Glide.with(this@PictureActivity).load(userIcon).into(imageView)
                }

                override fun onCancel() {
                }
            })
    }

    /**
     * 打开相册功能
     */
    fun openPhoto(view: View) {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage())
            .imageEngine(GlideEngine.createGlideEngine())
            .forResult(object : OnResultCallbackListener<LocalMedia> {
                override fun onResult(result: MutableList<LocalMedia>?) {
                    var userIcon = result!![0].realPath
                    if (userIcon == null) {
                        userIcon = result[0].path
                    }
                    Glide.with(this@PictureActivity).load(userIcon).into(imageView)

                }

                override fun onCancel() {
                }
            })
    }
}
