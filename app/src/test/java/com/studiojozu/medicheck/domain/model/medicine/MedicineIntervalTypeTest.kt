package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.TestDatetimeType
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@Suppress("FunctionName")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class MedicineIntervalTypeTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    fun constructor() {
        assertEquals(0L, MedicineIntervalType().dbValue)
        assertEquals(1L, MedicineIntervalType(1L).dbValue)
        assertEquals(2L, MedicineIntervalType(2).dbValue)
        assertEquals(3L, MedicineIntervalType(MedicineIntervalType(3)).dbValue)
    }

    @Test
    @Throws(Exception::class)
    fun displayValue() = try {
        MedicineIntervalType().displayValue
        fail()
    } catch (e: RuntimeException) {
        assertEquals("you need to call getDisplayValue(Resources, MedicineIntervalModeType).", e.message)
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun displayValue_DaysJa() {
        val resources = RuntimeEnvironment.application.applicationContext.resources
        val mode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)

        assertEquals("毎日", MedicineIntervalType(0).displayValue(resources, mode))
        assertEquals("1日おき", MedicineIntervalType(1).displayValue(resources, mode))
        assertEquals("2日おき", MedicineIntervalType(2).displayValue(resources, mode))
    }

    @Test
    @Throws(Exception::class)
    fun displayValue_DaysDefaultLocale() {
        val resources = RuntimeEnvironment.application.applicationContext.resources
        val mode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)

        assertEquals("every day", MedicineIntervalType(0).displayValue(resources, mode))
        assertEquals("every other days", MedicineIntervalType(1).displayValue(resources, mode))
        assertEquals("every 2 days", MedicineIntervalType(2).displayValue(resources, mode))
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun displayValue_MonthJa() {
        val resources = RuntimeEnvironment.application.applicationContext.resources
        val mode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)

        assertEquals("毎月 1日", MedicineIntervalType(1).displayValue(resources, mode))
        assertEquals("毎月 2日", MedicineIntervalType(2).displayValue(resources, mode))
        assertEquals("毎月 3日", MedicineIntervalType(3).displayValue(resources, mode))
        assertEquals("毎月 4日", MedicineIntervalType(4).displayValue(resources, mode))
    }

    @Test
    @Throws(Exception::class)
    fun displayValue_MonthDefaultLocale() {
        val resources = RuntimeEnvironment.application.applicationContext.resources
        val mode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)

        assertEquals("1st of every month", MedicineIntervalType(1).displayValue(resources, mode))
        assertEquals("2nd of every month", MedicineIntervalType(2).displayValue(resources, mode))
        assertEquals("3rd of every month", MedicineIntervalType(3).displayValue(resources, mode))
        assertEquals("4th of every month", MedicineIntervalType(4).displayValue(resources, mode))
    }

    @Test
    @Throws(Exception::class)
    fun addIntervalDays() {
        val mode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.DAYS)
        val firstDay = TestDatetimeType(2017, 1, 30, 0, 0)

        var model = MedicineIntervalType(0)
        assertEquals("17/01/31 0:00", model.addInterval(firstDay, mode).displayValue)

        model = MedicineIntervalType(1)
        assertEquals("17/02/01 0:00", model.addInterval(firstDay, mode).displayValue)

        model = MedicineIntervalType(2)
        assertEquals("17/02/02 0:00", model.addInterval(firstDay, mode).displayValue)
    }

    @Test
    @Throws(Exception::class)
    fun addIntervalMonth() {
        val mode = MedicineIntervalModeType(MedicineIntervalModeType.DateIntervalPattern.MONTH)

        var firstDay = TestDatetimeType(2017, 1, 30, 0, 0)
        var model = MedicineIntervalType(1)
        assertEquals("17/02/01 0:00", model.addInterval(firstDay, mode).displayValue)

        firstDay = TestDatetimeType(2017, 1, 1, 0, 0)
        model = MedicineIntervalType(15)
        assertEquals("17/02/15 0:00", model.addInterval(firstDay, mode).displayValue)

        firstDay = TestDatetimeType(2017, 1, 15, 0, 0)
        model = MedicineIntervalType(15)
        assertEquals("17/02/15 0:00", model.addInterval(firstDay, mode).displayValue)

        firstDay = TestDatetimeType(2017, 1, 1, 0, 0)
        model = MedicineIntervalType(31)
        assertEquals("17/02/28 0:00", model.addInterval(firstDay, mode).displayValue)

        firstDay = TestDatetimeType(2017, 1, 31, 0, 0)
        model = MedicineIntervalType(31)
        assertEquals("17/02/28 0:00", model.addInterval(firstDay, mode).displayValue)

        firstDay = TestDatetimeType(2017, 2, 28, 0, 0)
        model = MedicineIntervalType(31)
        assertEquals("17/03/31 0:00", model.addInterval(firstDay, mode).displayValue)
    }
}