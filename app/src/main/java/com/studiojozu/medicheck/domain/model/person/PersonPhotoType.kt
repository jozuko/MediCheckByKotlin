package com.studiojozu.medicheck.domain.model.person

import com.studiojozu.common.domain.model.general.APhotoType

class PersonPhotoType : APhotoType<PersonPhotoType> {
    companion object {
        const val serialVersionUID = -8138608804947506518L
    }

    constructor() : super("")
    constructor(value: Any) : super(value)
}
