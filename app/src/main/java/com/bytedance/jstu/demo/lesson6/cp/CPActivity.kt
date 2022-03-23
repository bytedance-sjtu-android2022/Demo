package com.bytedance.jstu.demo.lesson6.cp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R

class CPActivity: AppCompatActivity() {
    private val readContacts: Button by lazy {
        findViewById(R.id.read_contacts)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cp_layout)
        readContacts.setOnClickListener {
            startActivity(Intent(this, ReadContactsActivity::class.java))
        }
    }
}