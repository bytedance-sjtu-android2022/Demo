package com.bytedance.jstu.demo.lesson6

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.bytedance.jstu.demo.R
import com.bytedance.jstu.demo.lesson6.cp.CPActivity
import com.bytedance.jstu.demo.lesson6.db.DBActivity
import com.bytedance.jstu.demo.lesson6.file.FileActivity
import com.bytedance.jstu.demo.lesson6.sp.SPActivity

class StorageActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addLesson("文件存储", FileActivity::class.java)
        addLesson("SharedPreference存储", SPActivity::class.java)
        addLesson("数据库存储", DBActivity::class.java)
        addLesson("内容提供器", CPActivity::class.java)
    }

    private fun addLesson(text: String, activityClass: Class<*>) {
        val btn = AppCompatButton(this)
        btn.text = text
        btn.isAllCaps = false
        findViewById<ViewGroup>(R.id.container).addView(btn)
        btn.setOnClickListener {
            startActivity(Intent().apply {
                setClass(this@StorageActivity, activityClass)
            })
        }
    }
}