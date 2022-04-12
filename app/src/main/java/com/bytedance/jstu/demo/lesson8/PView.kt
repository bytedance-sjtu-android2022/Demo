package com.bytedance.jstu.demo.lesson8

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View

internal class PView : View {
    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {}
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val mode = MeasureSpec.getMode(widthMeasureSpec)
        val i = MeasureSpec.UNSPECIFIED
        val i2 = MeasureSpec.AT_MOST
        val i3 = MeasureSpec.EXACTLY
    }
}