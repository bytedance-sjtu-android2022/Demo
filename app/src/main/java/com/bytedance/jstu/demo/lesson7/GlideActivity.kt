package com.bytedance.jstu.demo.lesson7

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bytedance.jstu.demo.R
import java.util.ArrayList

class GlideActivity : AppCompatActivity() {
    private val pages: MutableList<View> = ArrayList()
    lateinit var viewPager: ViewPager
    @SuppressLint("SdCardPath")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        viewPager = findViewById(R.id.media_view_pager)

        addImage(R.drawable.drawableimage)
        addImage(R.drawable.ic_markunread)

        addImage("/sdcard/fileimage.jpg")
        addImage("file:///android_asset/assetsimage.jpg")
        addImage(R.raw.rawimage)

        addImage("https://t7.baidu.com/it/u=4162611394,4275913936&fm=193&f=GIF")

        val adapter = ViewAdapter()
        adapter.setDatas(pages)
        viewPager.adapter = adapter
    }

    private fun addImage(resId: Int) {
        val imageView =
            layoutInflater.inflate(R.layout.activity_base_multimedia_image_item, null) as ImageView
        Glide.with(this)
            .load(resId)
            .error(R.drawable.error)
            .into(imageView)
        pages.add(imageView)
    }

    private fun addImage(path: String) {
        val imageView =
            layoutInflater.inflate(R.layout.activity_base_multimedia_image_item, null) as ImageView
        Glide.with(this)
            .load(path)
            .apply(RequestOptions().circleCrop().diskCacheStrategy(DiskCacheStrategy.ALL))
            .error(R.drawable.error)
            .into(imageView)
        pages.add(imageView)
    }
}