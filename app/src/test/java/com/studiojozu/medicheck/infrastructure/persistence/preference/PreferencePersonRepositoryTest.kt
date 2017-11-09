package com.studiojozu.medicheck.infrastructure.persistence.preference

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.person.PersonIdType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqlitePerson
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class PreferencePersonRepositoryTest : ATestParent() {
    @Inject
    lateinit var preferencePersonRepository: PreferencePersonRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    fun latestUserPersonId() {
        // init null
        assertNull(preferencePersonRepository.latestUsedPersonId)

        // edit
        preferencePersonRepository.latestUsedPersonId = PersonIdType(SqlitePerson.DEFAULT_PERSON_ID)

        // read again
        assertEquals(SqlitePerson.DEFAULT_PERSON_ID, preferencePersonRepository.latestUsedPersonId!!.dbValue)
    }
}