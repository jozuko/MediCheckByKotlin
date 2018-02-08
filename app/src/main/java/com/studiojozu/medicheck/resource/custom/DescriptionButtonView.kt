package com.studiojozu.medicheck.resource.custom

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.studiojozu.medicheck.R

/**
 * 説明文がついたボタンView
 */
class DescriptionButtonView(context: Context, attrs: AttributeSet?) : ACustomView<DescriptionButtonView>(context, attrs) {

    private var clientClickListener: OnClickListener? = null

    private var unBinder: Unbinder = ButterKnife.bind(this, currentView)

    @BindView(R.id.description_button_layout)
    lateinit var layoutGroup: ShrinkRelativeLayout

    @BindView(R.id.description_button_icon)
    lateinit var iconInstance: ImageView

    @BindView(R.id.description_button_text)
    lateinit var textInstance: TextView

    @BindView(R.id.description_button_message)
    lateinit var messageInstance: TextView

    init {
        val styledAttributes = getStyledAttributes(attrs)
        try {
            setButtonText(styledAttributes)
            setButtonIcon(styledAttributes)
            setDescriptionText(styledAttributes)
        } finally {
            styledAttributes?.recycle()
        }

        // ButterKnifeでonClickをbindすると、同時に2つのViewが発火したと判定されて、結果、発火しないことになるので、自力で作成する
        layoutGroup.setOnClickListener {
            clientClickListener?.onClick(this)
        }

        // Pressの際の縮小値を設定
        setPressedScale()
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

    private fun setPressedScale() {
        layoutGroup.pressedScale = 0.98f
    }

    override fun layoutResource(): Int = R.layout.description_button

    override fun styleableResources(): IntArray? = R.styleable.description_button_view

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        unBinder.unbind()
    }

    override fun setOnClickListener(listener: OnClickListener?) {
        clientClickListener = listener
    }
}
