package com.studiojozu.common.domain.model.general

import android.net.Uri

import java.io.File

abstract class APhotoType<out C : APhotoType<C>> : ATextType<C> {
    companion object {
        const val serialVersionUID = 4343775991858053805L
    }

    val photoUri: Uri?
        get() {
            if (dbValue == "") return null

            val photoPath = File(dbValue)
            if (!photoPath.exists()) return null
            if (!photoPath.canRead()) return null

            return Uri.fromFile(photoPath)
        }

    protected constructor() : super()

    protected constructor(value: Any?) : super(value)
}
