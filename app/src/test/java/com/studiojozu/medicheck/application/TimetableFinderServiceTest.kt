package com.studiojozu.medicheck.application

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.setting.ATestParent
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
class TimetableFinderServiceTest : ATestParent() {
    @Inject
    lateinit var timetableFinderService: TimetableFinderService

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Config(qualifiers = "ja")
    @Throws(Exception::class)
    fun findAll() {
        // select init data
        val timetableList = timetableFinderService.findAll()
        assertEquals(12, timetableList.size)

        var index = 0
        assertEquals("朝", timetableList[index].timetableName.dbValue)
        assertEquals("7:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 1
        assertEquals("昼", timetableList[index].timetableName.dbValue)
        assertEquals("12:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 2
        assertEquals("夜", timetableList[index].timetableName.dbValue)
        assertEquals("19:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 3
        assertEquals("就寝前", timetableList[index].timetableName.dbValue)
        assertEquals("22:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 4
        assertEquals("朝食前", timetableList[index].timetableName.dbValue)
        assertEquals("6:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 5
        assertEquals("昼食前", timetableList[index].timetableName.dbValue)
        assertEquals("11:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 6
        assertEquals("夕食前", timetableList[index].timetableName.dbValue)
        assertEquals("18:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 7
        assertEquals("朝食後", timetableList[index].timetableName.dbValue)
        assertEquals("7:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 8
        assertEquals("昼食後", timetableList[index].timetableName.dbValue)
        assertEquals("12:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 9
        assertEquals("夕食後", timetableList[index].timetableName.dbValue)
        assertEquals("19:30", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 10
        assertEquals("食間(朝食 - 昼食)", timetableList[index].timetableName.dbValue)
        assertEquals("10:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)

        index = 11
        assertEquals("食間(昼食 - 夕食)", timetableList[index].timetableName.dbValue)
        assertEquals("16:00", timetableList[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableList[index].timetableDisplayOrder.dbValue)
    }
}
