package com.bytedance.jstu.demo.lesson2

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bytedance.jstu.demo.R

/**
 * Created by shenjun on 2/19/22.
 */
class ActivityB : LifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        findViewById<View>(R.id.button2).setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("title")
                .setMessage("message")
                .setPositiveButton("ok") { _: DialogInterface, _: Int ->
                    Log.d("ddd", "click ok")
                }
                .setNegativeButton("cancel") { _: DialogInterface, _: Int ->
                    Log.d("ddd", "click cancel")
                }
                .show()
        }
    }
}