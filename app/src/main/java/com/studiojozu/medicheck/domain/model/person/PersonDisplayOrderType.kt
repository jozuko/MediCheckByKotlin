package com.studiojozu.medicheck.domain.model.person

import com.studiojozu.common.domain.model.general.ALongType

class PersonDisplayOrderType : ALongType<PersonDisplayOrderType> {
    companion object {
        private val serialVersionUID = -1248465725190951112L
    }

    constructor() : super()
    constructor(value: Any) : super(value)
}
