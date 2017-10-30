package com.studiojozu.medicheck.domain.model.setting

import kotlin.reflect.KClass
import kotlin.reflect.KFunction
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberFunctions
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible

open class ATestParent {
    protected fun findProperty(kClazz: KClass<*>, name: String): KProperty1<*, *> {
        val mValueProperty = kClazz.memberProperties.find { it.name == name }
        mValueProperty!!.isAccessible = true
        return mValueProperty
    }

    protected fun findFunction(kClazz: KClass<*>, name: String): KFunction<*> {
        val function = kClazz.memberFunctions.find { it.name == name }
        function!!.isAccessible = true
        return function
    }
}