package com.studiojozu.medicheck.resource.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import com.studiojozu.medicheck.R

/**
 * 説明文がついたボタンView
 */
class DescriptionButtonView(context: Context, attrs: AttributeSet?) : ACustomView<DescriptionButtonView>(context, attrs), View.OnClickListener {

    private var mClientClickListener: View.OnClickListener? = null

    private val layoutGroup: ViewGroup
        get() = currentView.findViewById<View>(R.id.description_button_layout) as ViewGroup

    private val iconInstance: ImageView
        get() = currentView.findViewById<View>(R.id.description_button_icon) as ImageView

    private val textInstance: TextView
        get() = currentView.findViewById<View>(R.id.description_button_text) as TextView

    private val messageInstance: TextView
        get() = currentView.findViewById<View>(R.id.description_button_message) as TextView

    init {

        val styledAttributes = getStyledAttributes(attrs)
        try {
            setButtonText(styledAttributes)
            setButtonIcon(styledAttributes)
            setDescriptionText(styledAttributes)
        } finally {
            styledAttributes?.recycle()
        }

        layoutGroup.setOnClickListener(this)
    }

    override fun layoutResource(): Int = R.layout.description_button

    override fun styleableResources(): IntArray? = R.styleable.description_button_view

    override fun setOnClickListener(listener: View.OnClickListener?) {
        mClientClickListener = listener
    }

    private fun setButtonText(typedArray: TypedArray?) {
        val text = getAttributeString(typedArray, R.styleable.description_button_view_text)
        textInstance.text = text
    }

    private fun setButtonIcon(typedArray: TypedArray?) {
        val drawableLeftResourceId = getAttributeDrawableResourceId(typedArray, R.styleable.description_button_view_drawableLeft)
        if (drawableLeftResourceId == UNKNOWN_RESOURCE_ID) return

        iconInstance.setImageResource(drawableLeftResourceId)
    }

    private fun setDescriptionText(typedArray: TypedArray?) {
        val text = getAttributeString(typedArray, R.styleable.description_button_view_description)
        messageInstance.text = text
    }

    override fun onClick(view: View) {
        if (mClientClickListener != null)
            mClientClickListener!!.onClick(this)
    }
}
