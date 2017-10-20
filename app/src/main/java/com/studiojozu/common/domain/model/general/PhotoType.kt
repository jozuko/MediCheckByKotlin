package com.studiojozu.common.domain.model.general

import android.net.Uri

import java.io.File

/**
 * 写真パスを管理するクラス
 */
abstract class PhotoType<C : PhotoType<C>> : TextType<C> {
    companion object {
        private val serialVersionUID = 4343775991858053805L
    }

    val photoUri: Uri?
        get() {
            if (dbValue == "") return null

            val photoPath = File(dbValue)
            if (!photoPath.exists()) return null
            return if (!photoPath.canRead()) null else Uri.fromFile(photoPath)
        }

    protected constructor() : super("") {}

    protected constructor(value: Any?) : super(value) {}

}
