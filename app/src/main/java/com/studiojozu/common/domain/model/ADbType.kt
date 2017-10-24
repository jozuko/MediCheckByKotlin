package com.studiojozu.common.domain.model

import android.content.ContentValues

import java.io.Serializable

abstract class ADbType<out T, out C : ADbType<T, C>> : Serializable, Cloneable {

    companion object {
        const val serialVersionUID = -7199799314817003653L
    }

    /**
     * データベースで保持する値を返却する
     *
     * @return データベース値
     */
    abstract val dbValue: T

    /**
     * 表示値
     * @return 表示値
     */
    abstract val displayValue: String

    /**
     * Where句で使用するための、[.getDbValue]のString値
     *
     * @return [.getDbValue]のString値
     */
    val dbWhereValue: String
        get() = dbValue.toString()

    /**
     * insertで使用するContentValueに型名と値設定する
     *
     * @param columnName   型名称
     * @param contentValue 値
     */
    abstract fun setContentValue(columnName: String, contentValue: ContentValues)

    override fun equals(other: Any?): Boolean = if (other == null || other !is ADbType<*, *>) false else dbValue == other.dbValue

    override fun hashCode(): Int = dbValue!!.hashCode()

    @Suppress("UNCHECKED_CAST")
    override public fun clone(): C = super.clone() as C
}
