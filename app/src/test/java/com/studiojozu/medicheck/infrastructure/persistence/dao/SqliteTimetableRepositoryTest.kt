package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.setting.*
import com.studiojozu.medicheck.infrastructure.persistence.database.AppDatabase
import com.studiojozu.medicheck.infrastructure.persistence.entity.SqliteTimetable
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test
import org.junit.runner.RunWith
import org.powermock.core.classloader.annotations.PowerMockIgnore
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = "src/main/AndroidManifest.xml")
@PowerMockIgnore("org.mockito.*", "org.robolectric.*", "android.*")
class SqliteTimetableRepositoryTest : ATestParent() {

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun crud() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.timetableDao()

        // select init data
        var timetableArray = dao.findAll()
        assertEquals(12, timetableArray.size)

        // insert
        val insertData = setSqliteTimetable(Timetable(
                mTimetableId = TimetableIdType(),
                mTimetableName = TimetableNameType("服用時間名-1"),
                mTimetableTime = TimetableTimeType(15, 0),
                mTimetableDisplayOrder = TimetableDisplayOrderType(13L)
        ))
        dao.insert(insertData)

        timetableArray = dao.findAll()
        assertEquals(13, timetableArray.size)
        assertEquals("服用時間名-1", timetableArray[12].mTimetableName.dbValue)
        assertEquals("15:00", timetableArray[12].mTimetableTime.displayValue)
        assertEquals(13L, timetableArray[12].mTimetableDisplayOrder.dbValue)

        // update
        val updateData = setSqliteTimetable(Timetable(
                mTimetableId = insertData.mTimetableId,
                mTimetableName = TimetableNameType("服用時間名-2"),
                mTimetableTime = TimetableTimeType(4, 30),
                mTimetableDisplayOrder = TimetableDisplayOrderType(14L)
        ))
        dao.insert(updateData)

        timetableArray = dao.findAll()
        assertEquals(13, timetableArray.size)
        assertEquals("服用時間名-2", timetableArray[12].mTimetableName.dbValue)
        assertEquals("4:30", timetableArray[12].mTimetableTime.displayValue)
        assertEquals(14L, timetableArray[12].mTimetableDisplayOrder.dbValue)

        // delete
        val deleteData = setSqliteTimetable(Timetable(
                mTimetableId = insertData.mTimetableId,
                mTimetableName = TimetableNameType("服用時間名-3"),
                mTimetableTime = TimetableTimeType(16, 0),
                mTimetableDisplayOrder = TimetableDisplayOrderType(15L)
        ))
        dao.delete(deleteData)

        timetableArray = dao.findAll()
        assertEquals(12, timetableArray.size)
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun initRecords() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.timetableDao()

        // select init data
        val timetableArray = dao.findAll()
        assertEquals(12, timetableArray.size)

        var index = 0
        assertEquals("朝", timetableArray[index].mTimetableName.dbValue)
        assertEquals("7:00", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 1
        assertEquals("昼", timetableArray[index].mTimetableName.dbValue)
        assertEquals("12:00", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 2
        assertEquals("夜", timetableArray[index].mTimetableName.dbValue)
        assertEquals("19:00", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 3
        assertEquals("就寝前", timetableArray[index].mTimetableName.dbValue)
        assertEquals("22:00", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 4
        assertEquals("朝食前", timetableArray[index].mTimetableName.dbValue)
        assertEquals("6:30", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 5
        assertEquals("昼食前", timetableArray[index].mTimetableName.dbValue)
        assertEquals("11:30", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 6
        assertEquals("夕食前", timetableArray[index].mTimetableName.dbValue)
        assertEquals("18:30", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 7
        assertEquals("朝食後", timetableArray[index].mTimetableName.dbValue)
        assertEquals("7:30", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 8
        assertEquals("昼食後", timetableArray[index].mTimetableName.dbValue)
        assertEquals("12:30", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 9
        assertEquals("夕食後", timetableArray[index].mTimetableName.dbValue)
        assertEquals("19:30", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 10
        assertEquals("食間(朝食 - 昼食)", timetableArray[index].mTimetableName.dbValue)
        assertEquals("10:00", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)

        index = 11
        assertEquals("食間(昼食 - 夕食)", timetableArray[index].mTimetableName.dbValue)
        assertEquals("16:00", timetableArray[index].mTimetableTime.displayValue)
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder.dbValue)
    }

    @Test
    @Throws(Exception::class)
    @Config(qualifiers = "ja")
    fun findById() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.timetableDao()

        // select init data
        val timetableArray = dao.findAll()
        val timetable1 = timetableArray[0]
        val timetable2 = timetableArray[1]

        // findById
        var timetable = dao.findById(timetable1.mTimetableId.dbValue)!!
        assertEquals(timetable1.mTimetableId, timetable.mTimetableId)
        assertEquals(timetable1.mTimetableName, timetable.mTimetableName)
        assertEquals(timetable1.mTimetableTime, timetable.mTimetableTime)
        assertEquals(timetable1.mTimetableDisplayOrder, timetable.mTimetableDisplayOrder)

        timetable = dao.findById(timetable2.mTimetableId.dbValue)!!
        assertEquals(timetable2.mTimetableId, timetable.mTimetableId)
        assertEquals(timetable2.mTimetableName, timetable.mTimetableName)
        assertEquals(timetable2.mTimetableTime, timetable.mTimetableTime)
        assertEquals(timetable2.mTimetableDisplayOrder, timetable.mTimetableDisplayOrder)

        // findById - not exists
        assertNull(dao.findById("unknown id"))
    }

    @Test
    @Throws(Exception::class)
    fun maxDisplayOrder() {
        val database = AppDatabase.getAppDatabase(RuntimeEnvironment.application.applicationContext)
        val dao = database.timetableDao()

        assertEquals(12L, dao.maxDisplayOrder())

        // insert
        val insertData = setSqliteTimetable(Timetable(
                mTimetableId = TimetableIdType(),
                mTimetableName = TimetableNameType("服用時間名-1"),
                mTimetableTime = TimetableTimeType(15, 0),
                mTimetableDisplayOrder = TimetableDisplayOrderType(20)
        ))
        dao.insert(insertData)

        assertEquals(20L, dao.maxDisplayOrder())
    }

    private fun setSqliteTimetable(entity: Timetable) =
            SqliteTimetable.build { mTimetable = entity }
}