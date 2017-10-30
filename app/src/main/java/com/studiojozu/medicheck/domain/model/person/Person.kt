package com.studiojozu.medicheck.domain.model.person

import java.io.Serializable

data class Person(val mPersonId: PersonIdType = PersonIdType(),
                  val mPersonName: PersonNameType = PersonNameType(),
                  val mPersonPhoto: PersonPhotoType = PersonPhotoType(),
                  val mPersonDisplayOrder: PersonDisplayOrderType = PersonDisplayOrderType(-1)) : Serializable {

    companion object {
        const val serialVersionUID = -4752297475972711988L
    }
}
