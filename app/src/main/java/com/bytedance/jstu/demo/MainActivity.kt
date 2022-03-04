package com.bytedance.jstu.demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import com.bytedance.jstu.demo.lesson2.BasicUIDemoActivity
import com.bytedance.jstu.demo.lesson4.handler.LessonListActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addLesson("第一讲 Android简介", BasicUIDemoActivity::class.java)
        addLesson("第二讲 基本用户界面开发", BasicUIDemoActivity::class.java)
        addLesson("第三讲 UI开发进阶", BasicUIDemoActivity::class.java)
        addLesson("第四讲 复杂应用组件", LessonListActivity::class.java)
        addLesson("第五讲 网络", BasicUIDemoActivity::class.java)
        addLesson("第六讲 存储", BasicUIDemoActivity::class.java)
        addLesson("第七讲 多媒体基础", BasicUIDemoActivity::class.java)
        addLesson("第八讲 多媒体进阶", BasicUIDemoActivity::class.java)
        addLesson("第九讲 新技术趋势", BasicUIDemoActivity::class.java)
    }

    private fun addLesson(text: String, activityClass: Class<*>) {
        val btn = AppCompatButton(this)
        btn.text = text
        btn.isAllCaps = false
        findViewById<ViewGroup>(R.id.container).addView(btn)
        btn.setOnClickListener {
            startActivity(Intent().apply {
                setClass(this@MainActivity, activityClass)
            })
        }
    }
}