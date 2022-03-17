package com.bytedance.jstu.demo.lesson5

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R
import com.bytedance.jstu.demo.lesson5.api.DoubanBean
import com.bytedance.jstu.demo.lesson5.interceptor.TimeConsumeInterceptor
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException

class OkHttpActivity : AppCompatActivity() {

    private var requestBtn: Button? = null
    private var showText: TextView? = null

    private val okhttpListener = object : EventListener() {
        @SuppressLint("SetTextI18n")
        override fun dnsStart(call: Call, domainName: String) {
            super.dnsStart(call, domainName)
            updateShowTextView("\nDns Search: $domainName")
        }

        @SuppressLint("SetTextI18n")
        override fun responseBodyStart(call: Call) {
            super.responseBodyStart(call)
            updateShowTextView("\nResponse Start")
        }
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(TimeConsumeInterceptor())
        .eventListener(okhttpListener)
        .build()

    private val gson = GsonBuilder().create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_okhttp)
        requestBtn = findViewById(R.id.send_request)
        showText = findViewById(R.id.show_text)

        requestBtn?.setOnClickListener {
            updateShowTextView("", false)
            click()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updateShowTextView(text: String, append: Boolean = true) {
        if (Looper.getMainLooper() !== Looper.myLooper()) {
            // 子线程，提交到主线程中去更新 UI.
            runOnUiThread {
                updateShowTextView(text, append)
            }
        } else {
            showText?.text = if (append) showText?.text.toString() + text else text
        }
    }

    private fun request(url: String, callback: Callback) {
        val request: Request = Request.Builder()
            .url(url)
            .header("User-Agent", "Sjtu-Android-OKHttp")
            .build()
        client.newCall(request).enqueue(callback)
    }

    private fun click() {
        val url = "https://movie.querydata.org/api?id=25845392"
        request(url, object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
                updateShowTextView(e.message.toString(), false)
            }

            @SuppressLint("SetTextI18n")
            override fun onResponse(call: Call, response: Response) {
                val respFormatText = if (response.isSuccessful) {
                    val bodyString = response.body?.string()
                    val doubanBean = gson.fromJson(bodyString, DoubanBean::class.java)
                    "\n\n\nOriginalname: ${doubanBean.originalName}\nAlias: ${doubanBean.alias}"
                } else {
                    "\n\n\nResponse fail: ${response.body?.string()}, http status code: ${response.code}."
                }
                updateShowTextView(respFormatText)
            }
        })
    }
}