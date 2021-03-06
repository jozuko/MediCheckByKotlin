package com.studiojozu.medicheck.domain.model.person

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
class PersonTest : ATestParent() {
    private val mPersonNameProperty = findProperty(Person::class, "personName")
    private fun getPersonName(entity: Person): PersonNameType = mPersonNameProperty.call(entity) as PersonNameType

    private val mPersonPhotoProperty = findProperty(Person::class, "personPhoto")
    private fun getPersonPhoto(entity: Person): PersonPhotoType = mPersonPhotoProperty.call(entity) as PersonPhotoType

    private val mPersonDisplayOrderProperty = findProperty(Person::class, "personDisplayOrder")
    private fun getPersonDisplayOrder(entity: Person): PersonDisplayOrderType = mPersonDisplayOrderProperty.call(entity) as PersonDisplayOrderType

    @Test
    @Throws(Exception::class)
    fun constructor_NoParameter() {
        val entity = Person()
        assertNotNull(entity.personId.dbValue)
        assertNotSame("", entity.personId.dbValue)
        assertEquals("", getPersonName(entity).dbValue)
        assertEquals("", getPersonPhoto(entity).dbValue)
        assertEquals(-1L, getPersonDisplayOrder(entity).dbValue)
    }
}