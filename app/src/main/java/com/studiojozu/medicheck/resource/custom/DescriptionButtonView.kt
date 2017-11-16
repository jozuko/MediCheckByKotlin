package com.studiojozu.medicheck.resource.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.studiojozu.medicheck.R

/**
 * 説明文がついたボタンView
 */
class DescriptionButtonView(context: Context, attrs: AttributeSet?) : ACustomView<DescriptionButtonView>(context, attrs) {

    private var mClientClickListener: View.OnClickListener? = null

    @BindView(R.id.description_button_layout)
    lateinit var layoutGroup: RelativeLayout

    @BindView(R.id.description_button_icon)
    lateinit var iconInstance: ImageView

    @BindView(R.id.description_button_text)
    lateinit var textInstance: TextView

    @BindView(R.id.description_button_message)
    lateinit var messageInstance: TextView

    init {
        ButterKnife.bind(this)

        val styledAttributes = getStyledAttributes(attrs)
        try {
            setButtonText(styledAttributes)
            setButtonIcon(styledAttributes)
            setDescriptionText(styledAttributes)
        } finally {
            styledAttributes?.recycle()
        }
    }

    private fun setButtonText(styledAttributes: TypedArray?) {
        textInstance.text = getAttributeString(styledAttributes, R.styleable.description_button_view_text)
    }

    private fun setButtonIcon(styledAttributes: TypedArray?) {
        val drawableLeftResourceId = getAttributeDrawableResourceId(styledAttributes, R.styleable.description_button_view_drawableLeft)
        if (drawableLeftResourceId == UNKNOWN_RESOURCE_ID) return

        iconInstance.setImageResource(drawableLeftResourceId)
    }

    private fun setDescriptionText(styledAttributes: TypedArray?) {
        messageInstance.text = getAttributeString(styledAttributes, R.styleable.description_button_view_description)
    }

    override fun layoutResource(): Int = R.layout.description_button

    override fun styleableResources(): IntArray? = R.styleable.description_button_view

    override fun setOnClickListener(listener: View.OnClickListener?) {
        mClientClickListener = listener
    }

    @OnClick(R.id.description_button_layout)
    fun onClickLayoutGroup() {
        mClientClickListener?.onClick(this)
    }
}
