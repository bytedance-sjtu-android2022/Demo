package com.bytedance.jstu.demo.lesson6.sp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R
import com.bytedance.jstu.demo.lesson6.StorageActivity

class SPActivity: AppCompatActivity() {

    private val spStoreButton: Button by lazy {
        findViewById(R.id.sp_store_text)
    }

    private val spReadButton: Button by lazy {
        findViewById(R.id.sp_read_text)
    }

    private val spFileContent: TextView by lazy {
        findViewById(R.id.sp_file_content)
    }

    private val account: EditText by lazy {
        findViewById(R.id.account)
    }

    private val password: EditText by lazy {
        findViewById(R.id.password)
    }

    private val remember: CheckBox by lazy {
        findViewById(R.id.remember)
    }

    private val login: Button by lazy {
        findViewById(R.id.login)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_layout)
        spStoreButton.setOnClickListener {
            val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
            editor.putString("name", "Peter")
            editor.putInt("age", 20)
            editor.putBoolean("married", false)
            editor.apply()
        }
        spReadButton.setOnClickListener {
            val sp = getSharedPreferences("data", Context.MODE_PRIVATE)
            val name = sp.getString("name", "")
            val age = sp.getInt("age", 0)
            val married = sp.getBoolean("married", false)
            val content = "name:$name\nage:$age\nmarried:$married"
            spFileContent.text = content
        }

        val pref = getPreferences(MODE_PRIVATE)
        val isRemember = pref.getBoolean("remember_pw", false)
        if (isRemember) {
            account.setText(pref.getString("account", ""))
            password.setText(pref.getString("password", ""))
            remember.isChecked = true
        }

        login.setOnClickListener {
            if (account.text.isEmpty() || password.text.isEmpty()) {
                Toast.makeText(this, "account or password can not be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val editor = pref.edit()
            if ("admin" == account.text.toString() && "123456" == password.text.toString()) {
                if (remember.isChecked) {
                    editor.putBoolean("remember_pw", true)
                    editor.putString("account", account.text.toString())
                    editor.putString("password", password.text.toString())
                } else {
                    editor.putBoolean("remember_pw", false)
                }
                editor.apply()
                startActivity(Intent(this, StorageActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "account or password is not correct, please try again", Toast.LENGTH_SHORT).show()
            }
        }

    }
}