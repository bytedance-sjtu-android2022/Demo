package com.bytedance.jstu.demo.lesson7

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R

class BaseMultimediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_multimedia_main)
        initButton()
    }

    private fun initButton() {
        open(R.id.glideImage, GlideActivity::class.java)
        open(R.id.frescoImage, FrescoImageActivity::class.java)
        open(R.id.mediaPlayer, MediaPlayerActivity::class.java)
        open(R.id.videoView, VideoActivity::class.java)
        open(R.id.canvas, CanvasActivity::class.java)
        open(R.id.image, ImageActivity::class.java)
    }


    private fun open(buttonId: Int, clz: Class<*>) {
        findViewById<View>(buttonId).setOnClickListener {
            startActivity(
                Intent(
                    this@BaseMultimediaActivity,
                    clz
                )
            )
        }
    }
}