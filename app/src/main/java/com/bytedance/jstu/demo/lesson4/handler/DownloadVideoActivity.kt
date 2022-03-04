package com.bytedance.jstu.demo.lesson4.handler

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R

class DownloadVideoActivity : AppCompatActivity() {

    companion object {

        const val STATUS_START_DOWNLOAD = 0
        const val STATUS_DOWNLOADING = 1
        const val STATUS_FINISH_DOWNLOAD = 2
        const val KEY_PROGRESS = "progress"
    }

    var downloadTextView: TextView? = null

    val handler: Handler = Handler(Looper.getMainLooper()) { msg ->
        when (msg.what) {
            STATUS_DOWNLOADING -> {
                downloadTextView?.text = "正在下载 ${msg.data[KEY_PROGRESS]}%"
            }
            STATUS_START_DOWNLOAD -> {
                downloadTextView?.text = "开始下载..."
            }
            STATUS_FINISH_DOWNLOAD -> {
                downloadTextView?.text = "下载完成!!!"
            }
        }
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_download_video)
        findViewById<Button>(R.id.bt_download).setOnClickListener {
            val downloadThread = DownloadVideoThread()
            downloadThread.start()
        }
        downloadTextView = findViewById(R.id.tv_progress)
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacksAndMessages(null)
    }

    inner class DownloadVideoThread : Thread() {

        override fun run() {
            super.run()
            startDownload()
            sleep(500)
            downloadVideo()
            finishDownload()
        }

        private fun downloadVideo() {
            var count = 0
            while (count < 100) {
                sleep(30)
                count ++
                updateDownloadProgress(count)
            }
        }

        private fun startDownload() {
            val msg = Message.obtain()
            msg.what = STATUS_START_DOWNLOAD
            handler.sendMessage(msg)
        }

        private fun finishDownload() {
            val msg = Message.obtain()
            msg.what = STATUS_FINISH_DOWNLOAD
            handler.sendMessage(msg)
        }

        private fun updateDownloadProgress(progress: Int) {
            val msg = Message.obtain()
            msg.what = STATUS_DOWNLOADING
            msg.data = Bundle().apply {
                putInt(KEY_PROGRESS, progress)
            }
            handler.sendMessage(msg)
        }
    }
}