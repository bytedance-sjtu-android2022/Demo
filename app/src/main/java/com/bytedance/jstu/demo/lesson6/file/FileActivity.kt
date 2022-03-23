package com.bytedance.jstu.demo.lesson6.file

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R
import java.io.*
import java.lang.StringBuilder

class FileActivity: AppCompatActivity() {
    private val inPutString: EditText by lazy {
        findViewById(R.id.input_text)
    }

    private val storeButton: Button by lazy {
        findViewById(R.id.store_text)
    }

    private val appendButton: Button by lazy {
        findViewById(R.id.append_text)
    }

    private val readButton: Button by lazy {
        findViewById(R.id.read_text)
    }

    private val fileContent: TextView by lazy {
        findViewById(R.id.file_content)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_layout)
        storeButton.setOnClickListener {
            storeText(inPutString.text.toString())
        }
        appendButton.setOnClickListener {
            storeAndAppendText(inPutString.text.toString())
        }
        readButton.setOnClickListener {
            val content = readTextFromFile()
            fileContent.text = content
        }
    }

    private fun storeText(inputString: String) {
        try {
            val output = openFileOutput("data", Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(inputString)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun storeAndAppendText(inputString: String) {
        try {
            val output = openFileOutput("data", Context.MODE_APPEND)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use {
                it.write(inputString)
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun readTextFromFile(): String {
        val content = StringBuilder()
        try {
            val input = openFileInput("data")
            val reader = BufferedReader(InputStreamReader(input))
            reader.use {
                reader.forEachLine {
                    content.append(it)
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return content.toString()
    }
}