package com.bytedance.jstu.demo.lesson4.handler

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bytedance.jstu.demo.R
import com.bytedance.jstu.demo.lesson4.homework.ClockActivity
import com.bytedance.jstu.demo.lesson4.thread.ThreadActivity
import com.bytedance.jstu.demo.lesson4.view.CustomViewActivity

class LessonListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addLesson("Thread使用", ThreadActivity::class.java)
        addLesson("开屏广告", SplashActivity::class.java)
        addLesson("文件下载", DownloadVideoActivity::class.java)
        addLesson("自定义View", CustomViewActivity::class.java)

        addLesson("模拟时钟", ClockActivity::class.java)
    }

    private fun addLesson(text: String, activityClass: Class<*>) {
        val btn = AppCompatButton(this)
        btn.text = text
        btn.isAllCaps = false
        findViewById<ViewGroup>(R.id.container).addView(btn)
        btn.setOnClickListener {
            startActivity(Intent().apply {
                setClass(this@LessonListActivity, activityClass)
            })
        }
    }
}