package com.studiojozu.medicheck.resource.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.RelativeLayout

class ShrinkRelativeLayout(context: Context?, attrs: AttributeSet?) : RelativeLayout(context, attrs) {

    var pressedScale: Float = 0.95f
        set(value) {
            field = if (value == java.lang.Float.MIN_VALUE) 1f else value
        }

    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        pivotX = w / 2f
        pivotY = h / 2f
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        scaleX = if (isPressed) pressedScale else 1f
        scaleY = if (isPressed) pressedScale else 1f
    }
}