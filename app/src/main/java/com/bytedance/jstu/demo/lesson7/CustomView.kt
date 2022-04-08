package com.bytedance.jstu.demo.lesson7

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.bytedance.jstu.demo.R

class CustomView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val iconbit = BitmapFactory.decodeResource(resources, R.drawable.image2)
        canvas.drawBitmap(iconbit, 20f, 20f, null)

    }
}