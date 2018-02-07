package com.studiojozu.medicheck.resource.custom

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.TypedArray
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.annotation.StyleableRes
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout

abstract class ACustomView<out T>(private val mContext: Context, attrs: AttributeSet?) : LinearLayout(mContext, attrs) {

    companion object {
        internal const val RESOURCE_DEFAULT_INTEGER = Integer.MIN_VALUE
        internal const val RESOURCE_DEFAULT_FLOAT = java.lang.Float.MIN_VALUE
        internal const val RESOURCE_DEFAULT_STRING = ""
        internal const val UNKNOWN_RESOURCE_ID = -1
    }

    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var mCustomView: T
    private var mActivity: Activity? = null

    init {
        @Suppress("UNCHECKED_CAST")
        @Suppress("LeakingThis")
        mCustomView = mLayoutInflater.inflate(layoutResource(), this)!! as T
    }

    protected val currentView: T
        get() = mCustomView

    @LayoutRes
    protected abstract fun layoutResource(): Int

    @StyleableRes
    protected abstract fun styleableResources(): IntArray?

    @SuppressLint("Recycle")
    protected fun getStyledAttributes(attrs: AttributeSet?): TypedArray? {
        attrs ?: return null
        val styleableResources = styleableResources() ?: return null
        return mContext.obtainStyledAttributes(attrs, styleableResources)
    }

    protected fun getAttributeString(styledAttributes: TypedArray?, @StyleableRes styleableId: Int): String =
            styledAttributes?.getString(styleableId) ?: RESOURCE_DEFAULT_STRING

    protected fun getAttributeInteger(styledAttributes: TypedArray?, @StyleableRes styleableId: Int): Int =
            styledAttributes?.getInt(styleableId, RESOURCE_DEFAULT_INTEGER) ?: RESOURCE_DEFAULT_INTEGER

    protected fun getAttributeFloat(styledAttributes: TypedArray?, @StyleableRes styleableId: Int): Float =
            styledAttributes?.getFloat(styleableId, RESOURCE_DEFAULT_FLOAT) ?: RESOURCE_DEFAULT_FLOAT

    @DrawableRes
    protected fun getAttributeDrawableResourceId(styledAttributes: TypedArray?, @StyleableRes styleableId: Int): Int =
            styledAttributes?.getResourceId(styleableId, UNKNOWN_RESOURCE_ID) ?: UNKNOWN_RESOURCE_ID
}
