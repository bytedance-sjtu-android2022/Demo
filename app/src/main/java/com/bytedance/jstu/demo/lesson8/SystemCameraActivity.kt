package com.bytedance.jstu.demo.lesson8

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bytedance.jstu.demo.R
import androidx.core.content.ContextCompat
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import android.content.Intent
import android.provider.MediaStore
import android.os.Environment
import android.widget.Toast
import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.registerForActivityResult
import com.bytedance.jstu.demo.lesson8.SystemCameraActivity
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class SystemCameraActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var takeImagePath: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_system_camera)
        imageView = findViewById(R.id.iv_img)
    }

    fun takePhoto(view: View) {
        requestCameraPermission()
    }

    fun takePhotoUsePath(view: View) {
        requestCameraAndSDCardPermission()
    }

    private fun requestCameraAndSDCardPermission() {
        val hasCameraPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
        if (hasCameraPermission) {
            takePhotoUsePathHasPermission()
        } else {
            val permissions = arrayOf(Manifest.permission.CAMERA)
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CAMERA_PATH_CODE)
        }
    }

    private fun takePhotoUsePathHasPermission() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takeImagePath = outputMediaPath()
        intent.putExtra(MediaStore.EXTRA_OUTPUT, PathUtils.getUriForFile(this, takeImagePath))
        if (intent.resolveActivity(packageManager) != null) {
            startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO_PATH)
        }
    }

    private fun outputMediaPath():String{
            val mediaStorageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
            val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val mediaFile = File(mediaStorageDir, "IMG_$timeStamp.jpg")
            if (!mediaFile.exists()) {
                mediaFile.parentFile.mkdirs()
            }
            return mediaFile.absolutePath
        }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            val permissions = arrayOf(Manifest.permission.CAMERA)
            ActivityCompat.requestPermissions(this, permissions, PERMISSION_REQUEST_CAMERA_CODE)
        } else {
            takePhotoHasPermission()
        }
    }

    private fun takePhotoHasPermission() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CODE_TAKE_PHOTO)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CAMERA_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                takePhotoHasPermission()
            } else {
                Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show()
            }
        } else if (requestCode == PERMISSION_REQUEST_CAMERA_PATH_CODE) {
            var hasPermission = true
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    hasPermission = false
                    break
                }
            }
            if (hasPermission) {
                takePhotoUsePathHasPermission()
            } else {
                Toast.makeText(this, "权限获取失败", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_TAKE_PHOTO && resultCode == RESULT_OK) {
            val data = data?: return
            val extras = data.extras
            val bitmap = extras?.get("data") as? Bitmap
            bitmap?.let {
                imageView.setImageBitmap(it)
            }
        } else if (requestCode == REQUEST_CODE_TAKE_PHOTO_PATH && resultCode == RESULT_OK) {
            //获取ImageView控件宽高
            val targetWidth = imageView.width
            val targetHeight = imageView.height
            //创建Options,设置inJustDecodeBounds为true，只解码图片宽高信息
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(takeImagePath, options)
            val photoWidth = options.outWidth
            val photoHeight = options.outHeight
            //计算图片和控件的缩放比例，并设置给Options,然后inJustDecodeBounds置为false，解码真正的图片信息
            val scaleFactor = Math.min(photoWidth / targetWidth, photoHeight / targetHeight)
            options.inJustDecodeBounds = false
            options.inSampleSize = scaleFactor
            val bitmap = BitmapFactory.decodeFile(takeImagePath, options)
            val rotateBitmap = PathUtils.rotateImage(bitmap, takeImagePath)
            imageView.setImageBitmap(rotateBitmap)
        }
    }

    companion object {
        private const val REQUEST_CODE_TAKE_PHOTO = 1001
        private const val REQUEST_CODE_TAKE_PHOTO_PATH = 1002
        private const val PERMISSION_REQUEST_CAMERA_CODE = 1003
        private const val PERMISSION_REQUEST_CAMERA_PATH_CODE = 1004

        fun startUI(context: Context) {
            val intent = Intent(context, SystemCameraActivity::class.java)
            context.startActivity(intent)
        }
    }
}