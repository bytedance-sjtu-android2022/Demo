package com.bytedance.jstu.demo.lesson2

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bytedance.jstu.demo.R

class BasicUIDemoActivity : LifecycleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_ui_demo)

        findViewById<View>(R.id.button1).setOnClickListener {
            val intent = Intent()
            intent.setClass(this, ActivityB::class.java)
            startActivity(intent)
        }

        findViewById<View>(R.id.button2).setOnClickListener {
            val intent = Intent()
            intent.setClass(this, RecyclerViewDemoActivity::class.java)
            startActivity(intent)
        }
    }
}