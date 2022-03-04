package com.bytedance.jstu.demo.lesson4.handler

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R

/**
 *  author : neo
 *  time   : 2021/10/30
 *  desc   :
 */
class SplashActivity : AppCompatActivity() {

    private val handler = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val skipBtn = findViewById<TextView>(R.id.skip)
        val runnable = Runnable { //跳转到首页
            jumpToHomepage()
        }
        handler.postDelayed(runnable, 3000)
        skipBtn.setOnClickListener {
            handler.removeCallbacks(runnable)
            jumpToHomepage()
        }
    }

    private fun jumpToHomepage() {
        startActivity(Intent(this, HomePageActivity::class.java))
        finish()
    }

    override fun finish() {
        super.finish()
        handler.removeCallbacksAndMessages(null)
    }
}