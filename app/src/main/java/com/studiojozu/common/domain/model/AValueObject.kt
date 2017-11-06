package com.studiojozu.common.domain.model

import java.io.Serializable

abstract class AValueObject<out T, out C : AValueObject<T, C>> : Serializable, Cloneable {

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

    override fun equals(other: Any?): Boolean {
        other ?: return false
        if (other !is AValueObject<*, *>) return false
        return dbValue == other.dbValue
    }

    override fun hashCode(): Int = dbValue!!.hashCode()

    @Suppress("UNCHECKED_CAST")
    override public fun clone(): C = super.clone() as C
}
