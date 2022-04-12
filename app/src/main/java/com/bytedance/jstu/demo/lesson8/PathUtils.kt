package com.bytedance.jstu.demo.lesson8

import android.content.Context
import android.os.Build
import androidx.core.content.FileProvider
import android.graphics.Bitmap
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import java.io.File
import java.io.IOException

internal object PathUtils {
    fun getUriForFile(context: Context, path: String): Uri {
        return if (Build.VERSION.SDK_INT >= 24) {
            FileProvider.getUriForFile(context.applicationContext, context.applicationContext.packageName + ".fileprovider", File(path))
        } else {
            Uri.fromFile(File(path))
        }
    }

    @JvmStatic
    fun rotateImage(bitmap: Bitmap, path: String): Bitmap? {
        try {
            val srcExif = ExifInterface(path)
            val matrix = Matrix()
            var angle = 0
            val orientation = srcExif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    angle = 90
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    angle = 180
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    angle = 270
                }
                else -> {}
            }
            matrix.postRotate(angle.toFloat())
            return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }
}