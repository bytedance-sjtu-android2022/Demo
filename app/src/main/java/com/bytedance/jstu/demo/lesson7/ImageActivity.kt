package com.bytedance.jstu.demo.lesson7

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.AsyncTask
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.DrawableCompat
import androidx.viewpager.widget.ViewPager
import com.bytedance.jstu.demo.R
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.util.*

class ImageActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager

    private val pages: MutableList<View> = ArrayList()

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        viewPager = findViewById(R.id.media_view_pager)

        viewPager.post {
            //1
            addImage(
                decodeBitmapFromResource(
                    resources,
                    R.drawable.large,
                    200,
                    400
                )
            )
            //2
            decodeBitmapFromVectorResource(R.drawable.ic_markunread)?.let { addImage(it) }

            //3
            // ReadFileTask(viewPager.width, viewPager.height).execute("/sdcard/fileimage.jpg")

            //4
            ReadAssetsTask(viewPager.width, viewPager.height).execute("assetsimage.jpg")

            //5
            ReadRawTask(viewPager.width, viewPager.height).execute(R.raw.rawimage)

            //6
            loadNetImage(viewPager.width, viewPager.height)

            val adapter = ViewAdapter()
            adapter.setDatas(pages)
            viewPager.adapter = adapter
        }
    }

    private fun loadNetImage(width: Int, height: Int) {
        val imageView = layoutInflater.inflate(R.layout.activity_image_item, null) as ImageView
        pages.add(imageView)
        Thread {
            val bitmap = decodeBitmapFromNet(
                "https://t7.baidu.com/it/u=4162611394,4275913936&fm=193&f=GIF",
                width,
                height
            )
            runOnUiThread { addImageAsyn(imageView, bitmap) }
        }.start()
    }

    private fun decodeBitmapFromNet(url: String, reqWidth: Int, reqHeight: Int): Bitmap? {
        var input: InputStream? = null
        var data: ByteArray? = null
        try {
            val imgUrl = URL(url)
            val conn = imgUrl.openConnection() as HttpURLConnection
            conn.doInput = true
            conn.connect()
            input = conn.inputStream
            data = inputStreamToByteArray(input)
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            return null
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            try {
                input?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
        return if (data != null) {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeByteArray(data, 0, data.size, options)
            options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
            options.inJustDecodeBounds = false
            BitmapFactory.decodeByteArray(data, 0, data.size, options)
        } else {
            null
        }
    }

    companion object {
        fun inputStreamToByteArray(input: InputStream?): ByteArray {
            val outputStream = ByteArrayOutputStream()
            input ?: return outputStream.toByteArray()
            val buffer = ByteArray(1024)
            var len: Int
            try {
                while (input.read(buffer).also { len = it } != -1) {
                    outputStream.write(buffer, 0, len)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                try {
                    input.close()
                    outputStream.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }
            return outputStream.toByteArray()
        }
    }

    private fun addImage(bitmap: Bitmap) {
        val imageView = layoutInflater.inflate(R.layout.activity_image_item, null) as ImageView
        imageView.setImageBitmap(bitmap)
        pages.add(imageView)
    }

    private fun decodeBitmapFromResource(
        res: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, resId, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeResource(res, resId, options)
    }

    private fun calculateInSampleSize(
        options: BitmapFactory.Options,
        reqWidth: Int,
        reqHeight: Int
    ): Int {
        /**
         * todo calculate sampleSize
         */
        return 2
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun decodeBitmapFromVectorResource(resId: Int): Bitmap? {
        var drawable = getDrawable(resId)
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            drawable = drawable?.let { DrawableCompat.wrap(it).mutate() }
        }
        val bitmap = drawable?.let {
            Bitmap.createBitmap(
                it.intrinsicWidth,
                drawable.intrinsicHeight, Bitmap.Config.ARGB_8888
            )
        }
        val canvas = bitmap?.let { Canvas(it) }
        if (canvas != null) {
            drawable?.setBounds(0, 0, canvas.width, canvas.height)
        }
        if (canvas != null) {
            drawable?.draw(canvas)
        }
        return bitmap
    }

    @SuppressLint("StaticFieldLeak")
    private inner class ReadFileTask(val width: Int, val height: Int) :
        AsyncTask<String?, Void?, Bitmap>() {
        private val imageView: ImageView =
            layoutInflater.inflate(R.layout.activity_image_item, null) as ImageView

        override fun doInBackground(vararg strings: String?): Bitmap? {
            return strings.firstOrNull()?.let {
                decodeBitmapFromFile(
                    it,
                    width,
                    height
                )
            }
        }

        override fun onPostExecute(bitmap: Bitmap) {
            addImageAsyn(imageView, bitmap)
        }

        init {
            pages.add(imageView)
        }
    }

    private fun decodeBitmapFromFile(path: String, reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(path, options)
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(path, options)
    }

    private fun addImageAsyn(imageView: ImageView, bitmap: Bitmap?) {
        imageView.setImageBitmap(bitmap)
    }

    private inner class ReadAssetsTask(val width: Int, val height: Int) :
        AsyncTask<String?, Void?, Bitmap?>() {

        private val imageView: ImageView =
            layoutInflater.inflate(R.layout.activity_image_item, null) as ImageView

        override fun doInBackground(vararg strings: String?): Bitmap? {
            return strings.firstOrNull()?.let {
                decodeBitmapFromAssets(
                    this@ImageActivity,
                    it,
                    width,
                    height
                )
            }
        }

        override fun onPostExecute(bitmap: Bitmap?) {
            addImageAsyn(imageView, bitmap)
        }

        init {
            pages.add(imageView)
        }
    }

    private fun decodeBitmapFromAssets(
        context: Context,
        fileName: String,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap? {
        val asset = context.assets
        val input = try {
            asset.open(fileName)
        } catch (e: IOException) {
            return null
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(input, null, options)
        try {
            input.reset()
        } catch (e: IOException) {
            return null
        }
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        val bitmap = BitmapFactory.decodeStream(input, null, options)
        try {
            input.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

    private inner class ReadRawTask(val width: Int, val height: Int) :
        AsyncTask<Int, Void?, Bitmap?>() {
        private val imageView: ImageView =
            layoutInflater.inflate(R.layout.activity_image_item, null) as ImageView

        override fun onPostExecute(bitmap: Bitmap?) {
            addImageAsyn(imageView, bitmap)
        }

        init {
            pages.add(imageView)
        }

        override fun doInBackground(vararg params: Int?): Bitmap? {
            return params.firstOrNull()?.let {
                decodeBitmapFromRaw(this@ImageActivity.resources, it, width, height)
            }
        }
    }

    private fun decodeBitmapFromRaw(
        res: Resources,
        resId: Int,
        reqWidth: Int,
        reqHeight: Int
    ): Bitmap? {
        val input = res.openRawResource(resId)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeStream(input, null, options)
        try {
            input.reset()
        } catch (e: IOException) {
            return null
        }
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight)
        options.inJustDecodeBounds = false
        val bitmap = BitmapFactory.decodeStream(input, null, options)
        try {
            input.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bitmap
    }

}