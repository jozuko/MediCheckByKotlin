package com.studiojozu.medicheck.domain.model.person

import java.io.Serializable

data class Person(val personId: PersonIdType = PersonIdType(),
                  val personName: PersonNameType = PersonNameType(),
                  val personPhoto: PersonPhotoType = PersonPhotoType(),
                  val personDisplayOrder: PersonDisplayOrderType = PersonDisplayOrderType(-1)) : Serializable {

    companion object {
        const val serialVersionUID = -4752297475972711988L
    }
}
