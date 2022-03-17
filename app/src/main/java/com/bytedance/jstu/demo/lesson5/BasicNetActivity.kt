package com.bytedance.jstu.demo.lesson5

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R

class BasicNetActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_basic)
        findViewById<View>(R.id.btn_okhttp)?.setOnClickListener {
            startActivity(Intent(this@BasicNetActivity, OkHttpActivity::class.java))
        }
        findViewById<View>(R.id.btn_retrofit)?.setOnClickListener {
            startActivity(Intent(this@BasicNetActivity, RetrofitActivity::class.java))
        }
    }
}