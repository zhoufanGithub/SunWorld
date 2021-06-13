package com.steven.materialdesignlibrary

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_fruit.*
import java.lang.StringBuilder

class FruitActivity : AppCompatActivity() {

    val FRUIT_NAME = "fruit_name"
    val FRUIT_IMAGE_ID = "fruit_image_id"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fruit)
        val intent = intent
        val fruitName = intent.getStringExtra(FRUIT_NAME)
        val fruitImageId = intent.getIntExtra(FRUIT_IMAGE_ID,0)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        collapsing_toolbar.title = fruitName
        Glide.with(this).load(fruitImageId).into(fruit_image_view)
        val fruitContent = generateFruitContent(fruitName)
        fruit_content_text.text = fruitContent
    }

    private fun generateFruitContent(fruitName: String?): String {
        val stringBuilder = StringBuilder()
        for (i in 0..500) {
            stringBuilder.append(fruitName)
        }
        return stringBuilder.toString()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
