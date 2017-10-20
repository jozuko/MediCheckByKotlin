package com.studiojozu.common.domain.model.general

import java.util.*

/**
 * IDを管理するクラス
 */
abstract class IdType<C : IdType<C>> : TextType<C> {
    companion object {
        private val serialVersionUID = 2407858114489390816L
        @JvmStatic
        private fun newId(): String = UUID.randomUUID().toString().toUpperCase().substring(0, 8)
    }

    protected constructor() : super(newId()) {}

    protected constructor(value: Any?) : super(value ?: newId()) {}
}
