package com.studiojozu.medicheck.domain.model.setting

import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

open class ATestParent {
    protected fun findProperty(kClazz: KClass<*>, name: String): KProperty1<*, *> {
        val mValueProperty = kClazz.memberProperties.find { it.name == name }
        mValueProperty!!.isAccessible = true
        return mValueProperty
    }
}