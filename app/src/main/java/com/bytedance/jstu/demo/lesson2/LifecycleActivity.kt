package com.bytedance.jstu.demo.lesson2

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

/**
 * Created by shenjun on 2/19/22.
 */
open class LifecycleActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Log.d("ddd", "$javaClass onCreate")
    }

    override fun onStart() {
        super.onStart()
        Log.d("ddd", "$javaClass onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ddd", "$javaClass onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("ddd", "$javaClass onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("ddd", "$javaClass onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("ddd", "$javaClass onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d("ddd", "$javaClass onRestart")
    }
}