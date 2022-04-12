package com.bytedance.jstu.demo.lesson8

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.widget.VideoView
import android.os.Bundle
import com.bytedance.jstu.demo.R
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.content.Intent
import android.provider.MediaStore
import android.os.Environment
import android.widget.Toast
import android.content.Context
import android.view.View
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SystemRecordActivity : AppCompatActivity() {
    private var mp4Path = ""
    private lateinit var videoView: VideoView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_record)
        videoView = findViewById(R.id.videoview)
    }

    fun record(view: View) {
        requestPermission()
    }

    private fun requestPermission() {
        val hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        val hasAudioPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED
        if (hasCameraPermission && hasAudioPermission) {
            recordVideo()
        } else {
            val permission: MutableList<String> = ArrayList()
            if (!hasCameraPermission) {
                permission.add(Manifest.permission.CAMERA)
            }
            if (!hasAudioPermission) {
                permission.add(Manifest.permission.RECORD_AUDIO)
            }
            ActivityCompat.requestPermissions(this, permission.toTypedArray(), PERMISSION_REQUEST_CODE)
        }
    }

    private fun recordVideo() {
        val intent = Intent(MediaStore.ACTION_VIDEO_CAPTURE)
        mp4Path = outputMediaPath
        intent.putExtra(MediaStore.EXTRA_OUTPUT, PathUtils.getUriForFile(this, mp4Path))
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1)
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_CODE_RECORD)
        }
    }

    private val outputMediaPath: String
        private get() {
            val mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val mediaFile = File(mediaStorageDir, "IMG_$timeStamp.mp4")
            if (!mediaFile.exists()) {
                mediaFile.parentFile.mkdirs()
            }
            return mediaFile.absolutePath
        }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        var hasPermission = true
        for (grantResult in grantResults) {
            if (grantResult != PackageManager.PERMISSION_GRANTED) {
                hasPermission = false
                break
            }
        }
        if (hasPermission) {
            recordVideo()
        } else {
            Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_RECORD && resultCode == RESULT_OK) {
            play()
        }
    }

    private fun play() {
        videoView.setVideoPath(mp4Path)
        videoView.start()
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1001
        private const val REQUEST_CODE_RECORD = 1002
        fun startUI(context: Context) {
            val intent = Intent(context, SystemRecordActivity::class.java)
            context.startActivity(intent)
        }
    }
}