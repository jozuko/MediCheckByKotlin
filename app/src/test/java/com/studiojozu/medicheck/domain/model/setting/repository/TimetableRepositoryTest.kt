package com.studiojozu.medicheck.domain.model.setting.repository

import com.studiojozu.medicheck.di.MediCheckTestApplication
import com.studiojozu.medicheck.domain.model.setting.*
import org.junit.Assert
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
        Assert.assertEquals(12, timetableArray.size)

        // insert
        val insertData = Timetable(
                mTimetableId = TimetableIdType(),
                mTimetableName = TimetableNameType("服用時間名-1"),
                mTimetableTime = TimetableTimeType(15, 0),
                mTimetableDisplayOrder = TimetableDisplayOrderType(13L)
        )
        timetableRepository.insert(insertData)

        timetableArray = timetableRepository.findAll()
        Assert.assertEquals(13, timetableArray.size)
        Assert.assertEquals("服用時間名-1", timetableArray[12].mTimetableName.dbValue)
        Assert.assertEquals("15:00", timetableArray[12].mTimetableTime.displayValue)
        Assert.assertEquals(13L, timetableArray[12].mTimetableDisplayOrder.dbValue)

        // update
        val updateData = Timetable(
                mTimetableId = insertData.mTimetableId,
                mTimetableName = TimetableNameType("服用時間名-2"),
                mTimetableTime = TimetableTimeType(4, 30),
                mTimetableDisplayOrder = TimetableDisplayOrderType(14L)
        )
        timetableRepository.insert(updateData)

        timetableArray = timetableRepository.findAll()
        Assert.assertEquals(13, timetableArray.size)
        Assert.assertEquals("服用時間名-2", timetableArray[12].mTimetableName.dbValue)
        Assert.assertEquals("4:30", timetableArray[12].mTimetableTime.displayValue)
        Assert.assertEquals(14L, timetableArray[12].mTimetableDisplayOrder.dbValue)

        // delete
        val deleteData = Timetable(
                mTimetableId = insertData.mTimetableId,
                mTimetableName = TimetableNameType("服用時間名-3"),
                mTimetableTime = TimetableTimeType(16, 0),
                mTimetableDisplayOrder = TimetableDisplayOrderType(15L)
        )
        timetableRepository.delete(deleteData)

        timetableArray = timetableRepository.findAll()
        Assert.assertEquals(12, timetableArray.size)
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun initRecords() {
        // select init data
        val timetableArray = timetableRepository.findAll()
        Assert.assertEquals(12, timetableArray.size)

        var index = 0
        Assert.assertEquals("朝", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("7:00", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 1
        Assert.assertEquals("昼", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("12:00", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 2
        Assert.assertEquals("夜", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("19:00", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 3
        Assert.assertEquals("就寝前", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("22:00", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 4
        Assert.assertEquals("朝食前", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("6:30", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 5
        Assert.assertEquals("昼食前", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("11:30", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 6
        Assert.assertEquals("夕食前", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("18:30", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 7
        Assert.assertEquals("朝食後", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("7:30", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 8
        Assert.assertEquals("昼食後", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("12:30", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 9
        Assert.assertEquals("夕食後", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("19:30", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 10
        Assert.assertEquals("食間(朝食 - 昼食)", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("10:00", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 11
        Assert.assertEquals("食間(昼食 - 夕食)", timetableArray[index].mTimetableName.dbValue)
        Assert.assertEquals("16:00", timetableArray[index].mTimetableTime.displayValue)
        Assert.assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)
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
        var timetable = timetableRepository.findById(timetable1.mTimetableId)!!
        Assert.assertEquals(timetable1.mTimetableId, timetable.mTimetableId)
        Assert.assertEquals(timetable1.mTimetableName, timetable.mTimetableName)
        Assert.assertEquals(timetable1.mTimetableTime, timetable.mTimetableTime)
        Assert.assertEquals(timetable1.mTimetableDisplayOrder, timetable.mTimetableDisplayOrder)

        timetable = timetableRepository.findById(timetable2.mTimetableId)!!
        Assert.assertEquals(timetable2.mTimetableId, timetable.mTimetableId)
        Assert.assertEquals(timetable2.mTimetableName, timetable.mTimetableName)
        Assert.assertEquals(timetable2.mTimetableTime, timetable.mTimetableTime)
        Assert.assertEquals(timetable2.mTimetableDisplayOrder, timetable.mTimetableDisplayOrder)

        // findById - not exists
        Assert.assertNull(timetableRepository.findById(TimetableIdType("unknown id")))
    }
}