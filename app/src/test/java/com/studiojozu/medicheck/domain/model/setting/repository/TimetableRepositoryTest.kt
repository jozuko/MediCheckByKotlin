package com.studiojozu.medicheck.domain.model.setting.repository

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import javax.inject.Inject

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml", application = MediCheckTestApplication::class)
class TimetableRepositoryTest : ATestParent() {

    @Inject
    lateinit var timetableRepository: TimetableRepository

    @Before
    fun setUp() = (RuntimeEnvironment.application as MediCheckTestApplication).mAppTestComponent.inject(this)

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun crud() {
        // select init data
        var timetableArray = timetableRepository.findAll()
        assertEquals(12, timetableArray.size)

        // insert
        val insertData = Timetable(
                timetableId = TimetableIdType(),
                timetableName = TimetableNameType("服用時間名-1"),
                timetableTime = TimetableTimeType(15, 0),
                timetableDisplayOrder = TimetableDisplayOrderType(13L)
        )
        timetableRepository.insert(insertData)

        timetableArray = timetableRepository.findAll()
        assertEquals(13, timetableArray.size)
        assertEquals("服用時間名-1", timetableArray[12].timetableName.dbValue)
        assertEquals("15:00", timetableArray[12].timetableTime.displayValue)
        assertEquals(13L, timetableArray[12].timetableDisplayOrder.dbValue)

        // update
        val updateData = Timetable(
                timetableId = insertData.timetableId,
                timetableName = TimetableNameType("服用時間名-2"),
                timetableTime = TimetableTimeType(4, 30),
                timetableDisplayOrder = TimetableDisplayOrderType(14L)
        )
        timetableRepository.insert(updateData)

        timetableArray = timetableRepository.findAll()
        assertEquals(13, timetableArray.size)
        assertEquals("服用時間名-2", timetableArray[12].timetableName.dbValue)
        assertEquals("4:30", timetableArray[12].timetableTime.displayValue)
        assertEquals(14L, timetableArray[12].timetableDisplayOrder.dbValue)

        // delete
        val deleteData = Timetable(
                timetableId = insertData.timetableId,
                timetableName = TimetableNameType("服用時間名-3"),
                timetableTime = TimetableTimeType(16, 0),
                timetableDisplayOrder = TimetableDisplayOrderType(15L)
        )
        timetableRepository.delete(deleteData)

        timetableArray = timetableRepository.findAll()
        assertEquals(12, timetableArray.size)
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun initRecords() {
        // select init data
        val timetableArray = timetableRepository.findAll()
        assertEquals(12, timetableArray.size)

        var index = 0
        assertEquals("朝", timetableArray[index].timetableName.dbValue)
        assertEquals("7:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 1
        assertEquals("昼", timetableArray[index].timetableName.dbValue)
        assertEquals("12:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 2
        assertEquals("夜", timetableArray[index].timetableName.dbValue)
        assertEquals("19:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 3
        assertEquals("就寝前", timetableArray[index].timetableName.dbValue)
        assertEquals("22:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 4
        assertEquals("朝食前", timetableArray[index].timetableName.dbValue)
        assertEquals("6:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 5
        assertEquals("昼食前", timetableArray[index].timetableName.dbValue)
        assertEquals("11:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 6
        assertEquals("夕食前", timetableArray[index].timetableName.dbValue)
        assertEquals("18:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 7
        assertEquals("朝食後", timetableArray[index].timetableName.dbValue)
        assertEquals("7:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 8
        assertEquals("昼食後", timetableArray[index].timetableName.dbValue)
        assertEquals("12:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 9
        assertEquals("夕食後", timetableArray[index].timetableName.dbValue)
        assertEquals("19:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 10
        assertEquals("食間(朝食 - 昼食)", timetableArray[index].timetableName.dbValue)
        assertEquals("10:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 11
        assertEquals("食間(昼食 - 夕食)", timetableArray[index].timetableName.dbValue)
        assertEquals("16:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun findById() {
        // select init data
        val timetableArray = timetableRepository.findAll()
        val timetable1 = timetableArray[0]
        val timetable2 = timetableArray[1]

        // findById
        var timetable = timetableRepository.findById(timetable1.timetableId)!!
        assertEquals(timetable1.timetableId, timetable.timetableId)
        assertEquals(timetable1.timetableName, timetable.timetableName)
        assertEquals(timetable1.timetableTime, timetable.timetableTime)
        assertEquals(timetable1.timetableDisplayOrder, timetable.timetableDisplayOrder)

        timetable = timetableRepository.findById(timetable2.timetableId)!!
        assertEquals(timetable2.timetableId, timetable.timetableId)
        assertEquals(timetable2.timetableName, timetable.timetableName)
        assertEquals(timetable2.timetableTime, timetable.timetableTime)
        assertEquals(timetable2.timetableDisplayOrder, timetable.timetableDisplayOrder)

        // findById - not exists
        assertNull(timetableRepository.findById(TimetableIdType("unknown id")))
    }

    @Test
    @Throws(Exception::class)
    fun maxDisplayOrder() {
        assertEquals(12L, timetableRepository.maxDisplayOrder())

        // insert
        val insertData = Timetable(
                timetableId = TimetableIdType(),
                timetableName = TimetableNameType("服用時間名-1"),
                timetableTime = TimetableTimeType(15, 0),
                timetableDisplayOrder = TimetableDisplayOrderType(20L)
        )
        timetableRepository.insert(insertData)

        assertEquals(20L, timetableRepository.maxDisplayOrder())
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun update() {
        // select init data
        val defaultTimetables = timetableRepository.findAll()
        val updateList = defaultTimetables.mapIndexed { index, timetable ->
            Timetable(timetableId = timetable.timetableId,
                    timetableName = timetable.timetableName,
                    timetableTime = timetable.timetableTime,
                    timetableDisplayOrder = TimetableDisplayOrderType(defaultTimetables.size - index))
        }
        timetableRepository.update(updateList)

        val timetableArray = timetableRepository.findAll()

        var index = 11
        assertEquals("朝", timetableArray[index].timetableName.dbValue)
        assertEquals("7:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 10
        assertEquals("昼", timetableArray[index].timetableName.dbValue)
        assertEquals("12:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 9
        assertEquals("夜", timetableArray[index].timetableName.dbValue)
        assertEquals("19:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 8
        assertEquals("就寝前", timetableArray[index].timetableName.dbValue)
        assertEquals("22:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 7
        assertEquals("朝食前", timetableArray[index].timetableName.dbValue)
        assertEquals("6:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 6
        assertEquals("昼食前", timetableArray[index].timetableName.dbValue)
        assertEquals("11:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 5
        assertEquals("夕食前", timetableArray[index].timetableName.dbValue)
        assertEquals("18:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 4
        assertEquals("朝食後", timetableArray[index].timetableName.dbValue)
        assertEquals("7:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 3
        assertEquals("昼食後", timetableArray[index].timetableName.dbValue)
        assertEquals("12:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 2
        assertEquals("夕食後", timetableArray[index].timetableName.dbValue)
        assertEquals("19:30", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 1
        assertEquals("食間(朝食 - 昼食)", timetableArray[index].timetableName.dbValue)
        assertEquals("10:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)

        index = 0
        assertEquals("食間(昼食 - 夕食)", timetableArray[index].timetableName.dbValue)
        assertEquals("16:00", timetableArray[index].timetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].timetableDisplayOrder.dbValue)
    }
}