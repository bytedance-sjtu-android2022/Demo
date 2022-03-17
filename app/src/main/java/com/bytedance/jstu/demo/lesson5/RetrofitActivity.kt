package com.bytedance.jstu.demo.lesson5

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Looper
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R
import com.bytedance.jstu.demo.lesson5.api.DoubanBean
import com.bytedance.jstu.demo.lesson5.api.DoubanService
import com.bytedance.jstu.demo.lesson5.interceptor.TimeConsumeInterceptor
import okhttp3.*
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitActivity : AppCompatActivity() {

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

    private var retrofit = Retrofit.Builder()
        .baseUrl("https://movie.querydata.org/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_net_retrofit)
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

    private fun request(callback: Callback<DoubanBean>) {
        try {
            val doubanService = retrofit.create(DoubanService::class.java)
            doubanService.getMovieInfo(25845392).enqueue(callback)
        } catch (error: Throwable) {
            updateShowTextView("request err: ${error.message}", false)
            error.printStackTrace()
        }
    }

    private fun click() {
        request(object : Callback<DoubanBean> {
            @SuppressLint("SetTextI18n")
            override fun onResponse(
                call: retrofit2.Call<DoubanBean>,
                response: Response<DoubanBean>
            ) {
                val respFormatText = if (response.isSuccessful) {
                    val doubanBean = response.body()
                    "\n\n\nOriginalname: ${doubanBean?.originalName}\nAlias: ${doubanBean?.alias}"
                } else {
                    "\n\n\nResponse fail: ${response.errorBody()?.string()}, http status code: ${response.code()}."
                }
                updateShowTextView(respFormatText)
            }

            override fun onFailure(call: retrofit2.Call<DoubanBean>, t: Throwable) {
                updateShowTextView(t.message.toString(), false)
            }
        })
    }
}