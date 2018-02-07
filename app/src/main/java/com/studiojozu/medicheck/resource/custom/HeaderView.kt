package com.studiojozu.medicheck.resource.custom

import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.studiojozu.medicheck.R

class HeaderView(context: Context, attrs: AttributeSet?) : ACustomView<HeaderView>(context, attrs) {
    interface OnClickBackListener {
        fun onClickedHeaderBack()
    }

    private var mClientOnClickBackListener: OnClickBackListener? = null

    private var parentActivity: Activity? = null

    @BindView(R.id.header_icon)
    lateinit var headerIconInstance: ImageView

    @BindView(R.id.header_text)
    lateinit var headerTitleInstance: TextView

    init {
        ButterKnife.bind(this, currentView)
        val styledAttributes = getStyledAttributes(attrs)
        try {
            setHeaderIcon(styledAttributes)
            setHeaderTitle(styledAttributes)
        } finally {
            styledAttributes?.recycle()
        }
    }

    override fun layoutResource(): Int = R.layout.template_header

    override fun styleableResources(): IntArray? = R.styleable.template_header_view

    private fun setHeaderIcon(typedArray: TypedArray?) {
        val drawableResourceId = getAttributeDrawableResourceId(typedArray, R.styleable.template_header_view_header_icon)
        if (drawableResourceId == ACustomView.UNKNOWN_RESOURCE_ID) return

        headerIconInstance.setImageResource(drawableResourceId)
    }

    private fun setHeaderTitle(typedArray: TypedArray?) {
        headerTitleInstance.text = getAttributeString(typedArray, R.styleable.template_header_view_header_title)
    }

    @Suppress("unused")
    fun setOnClickBackListener(listener: OnClickBackListener?) {
        mClientOnClickBackListener = listener
    }

    fun setParentActivity(activity: Activity) {
        parentActivity = activity
    }

    /**
     * 設定ボタン Clickイベント処理
     */
    @OnClick(R.id.button_back)
    fun onClickMenuButton() {
        mClientOnClickBackListener?.onClickedHeaderBack()
    }
}
