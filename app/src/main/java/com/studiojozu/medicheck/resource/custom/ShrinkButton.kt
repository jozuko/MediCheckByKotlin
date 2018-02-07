package com.studiojozu.medicheck.resource.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.Button

class ShrinkButton(context: Context?, attrs: AttributeSet?) : Button(context, attrs) {
    override fun onSizeChanged(w: Int, h: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(w, h, oldWidth, oldHeight)
        pivotX = w / 2f
        pivotY = h / 2f
    }

    override fun drawableStateChanged() {
        super.drawableStateChanged()
        scaleX = if (isPressed) 0.95f else 1f
        scaleY = if (isPressed) 0.95f else 1f
    }
}
