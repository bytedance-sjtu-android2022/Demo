package com.bytedance.jstu.demo.lesson7

import android.graphics.PixelFormat
import android.media.MediaPlayer
import android.os.Build
import android.os.Bundle
import android.view.SurfaceHolder
import android.view.SurfaceView
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bytedance.jstu.demo.R
import java.io.IOException

class MediaPlayerActivity : AppCompatActivity() {
    private val player: MediaPlayer by lazy {
        MediaPlayer()
    }
    private var holder: SurfaceHolder? = null

    private lateinit var surfaceView: SurfaceView
    private lateinit var buttonPlay: Button
    private lateinit var buttonPause: Button

    @RequiresApi(api = Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "MediaPlayer"
        setContentView(R.layout.layout_media_player)

        surfaceView = findViewById(R.id.surfaceView)
        buttonPlay = findViewById(R.id.buttonPlay)
        buttonPause = findViewById(R.id.buttonPause)

        try {
            player.setDataSource(resources.openRawResourceFd(R.raw.big_buck_bunny))
            holder = surfaceView.holder
            holder?.setFormat(PixelFormat.TRANSPARENT)
            holder?.addCallback(PlayerCallBack())
            player.prepare()
            player.setOnPreparedListener { // 自动播放
                player.start()
                player.isLooping = true
            }
            player.setOnBufferingUpdateListener { mp, percent -> println(percent) }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        buttonPlay.setOnClickListener { player.start() }
        buttonPause.setOnClickListener { player.pause() }
    }

    override fun onPause() {
        super.onPause()
        player.stop()
        player.release()
    }

    private inner class PlayerCallBack : SurfaceHolder.Callback {
        override fun surfaceCreated(holder: SurfaceHolder) {
            player.setDisplay(holder)
        }

        override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {}
        override fun surfaceDestroyed(holder: SurfaceHolder) {}
    }
}