package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.validator.IValidator
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.Timetable
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class MedicineTimetableValidatorTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun success_oneshot() {
        val validator = MedicineTimetableValidator()
        val timetableList = mutableListOf<Timetable>()
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate(true, timetableList))
    }

    @Test
    @Throws(Exception::class)
    fun success_timetable() {
        val validator = MedicineTimetableValidator()
        val timetableList = mutableListOf(Timetable())
        assertEquals(IValidator.NO_ERROR_RESOURCE_ID, validator.validate(false, timetableList))
    }

    @Test
    @Throws(Exception::class)
    fun failed() {
        val validator = MedicineTimetableValidator()
        val timetableList = mutableListOf<Timetable>()

        assertEquals(R.string.validation_require_timetable, validator.validate())
        assertEquals(R.string.validation_require_timetable, validator.validate(true))
        assertEquals(R.string.validation_require_timetable, validator.validate(timetableList))
        assertEquals(R.string.validation_require_timetable, validator.validate(timetableList, true))
        assertEquals(R.string.validation_require_timetable, validator.validate(null, null))
        assertEquals(R.string.validation_require_timetable, validator.validate(true, null))
        assertEquals(R.string.validation_require_select, validator.validate(false, timetableList))
    }
}