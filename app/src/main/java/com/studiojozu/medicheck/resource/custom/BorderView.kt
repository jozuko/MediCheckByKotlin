package com.studiojozu.medicheck.resource.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import com.studiojozu.medicheck.R

class BorderView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint()
    private val borderSize: Float = context.resources.getDimensionPixelSize(R.dimen.border_size).toFloat()

    init {
        initPaint()
    }

    override fun onDraw(canvas: Canvas) {
        val center = height * .5f
        val width = width.toFloat()
        var lineFrom = 0f
        var lineTo = borderSize

        while (lineTo <= width) {
            canvas.drawLine(lineFrom, center, lineTo, center, paint)
            if (lineTo == width) break

            lineFrom = lineTo + (borderSize * 2)
            lineTo = lineFrom + borderSize
            if (lineTo > width)
                lineTo = width
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        layoutParams.height = borderSize.toInt()
        (layoutParams as ViewGroup.MarginLayoutParams).topMargin = context.resources.getDimensionPixelSize(R.dimen.border_margin)
        (layoutParams as ViewGroup.MarginLayoutParams).bottomMargin = context.resources.getDimensionPixelSize(R.dimen.border_margin)
    }

    private fun initPaint() {
        paint.isAntiAlias = true
        paint.color = ContextCompat.getColor(context, R.color.line)
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderSize
    }
}