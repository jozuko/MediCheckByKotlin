package com.studiojozu.medicheck.infrastructure.persistence.dao

import com.studiojozu.medicheck.domain.model.setting.ATestParent
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType
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
import java.util.*

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
        val insertData = SqliteTimetable(TimetableIdType().dbValue)
        insertData.mTimetableName = "服用時間名-1"
        insertData.mTimetableTime = TimetableTimeType(15, 0).dbValue
        insertData.mTimetableDisplayOrder = 13L
        dao.insert(insertData)

        timetableArray = dao.findAll()
        assertEquals(13, timetableArray.size)
        assertEquals("服用時間名-1", timetableArray[12].mTimetableName)
        assertEquals(15, timetableArray[12].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, timetableArray[12].mTimetableTime.get(Calendar.MINUTE))
        assertEquals(13L, timetableArray[12].mTimetableDisplayOrder)

        // update
        val updateData = SqliteTimetable(insertData.mTimetableId)
        updateData.mTimetableName = "服用時間名-2"
        updateData.mTimetableTime = TimetableTimeType(4, 30).dbValue
        updateData.mTimetableDisplayOrder = 14L
        dao.insert(updateData)

        timetableArray = dao.findAll()
        assertEquals(13, timetableArray.size)
        assertEquals("服用時間名-2", timetableArray[12].mTimetableName)
        assertEquals(4, timetableArray[12].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, timetableArray[12].mTimetableTime.get(Calendar.MINUTE))
        assertEquals(14L, timetableArray[12].mTimetableDisplayOrder)

        // delete
        val deleteData = SqliteTimetable(insertData.mTimetableId)
        updateData.mTimetableName = "服用時間名-3"
        updateData.mTimetableTime = TimetableTimeType(16, 0).dbValue
        updateData.mTimetableDisplayOrder = 15L
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
        assertEquals("朝", timetableArray[index].mTimetableName)
        assertEquals(7, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)

        index = 1
        assertEquals("昼", timetableArray[index].mTimetableName)
        assertEquals(12, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)

        index = 2
        assertEquals("夜", timetableArray[index].mTimetableName)
        assertEquals(19, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)

        index = 3
        assertEquals("就寝前", timetableArray[index].mTimetableName)
        assertEquals(22, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)

        index = 4
        assertEquals("朝食前", timetableArray[index].mTimetableName)
        assertEquals(6, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)

        index = 5
        assertEquals("昼食前", timetableArray[index].mTimetableName)
        assertEquals(11, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)

        index = 6
        assertEquals("夕食前", timetableArray[index].mTimetableName)
        assertEquals(18, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)

        index = 7
        assertEquals("朝食後", timetableArray[index].mTimetableName)
        assertEquals(7, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)

        index = 8
        assertEquals("昼食後", timetableArray[index].mTimetableName)
        assertEquals(12, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)

        index = 9
        assertEquals("夕食後", timetableArray[index].mTimetableName)
        assertEquals(19, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(30, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)

        index = 10
        assertEquals("食間(朝食 - 昼食)", timetableArray[index].mTimetableName)
        assertEquals(10, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)

        index = 11
        assertEquals("食間(昼食 - 夕食)", timetableArray[index].mTimetableName)
        assertEquals(16, timetableArray[index].mTimetableTime.get(Calendar.HOUR_OF_DAY))
        assertEquals(0, timetableArray[index].mTimetableTime.get(Calendar.MINUTE))
        assertEquals((index + 1).toLong(), timetableArray[index].mTimetableDisplayOrder)
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
        var timetable = dao.findById(timetable1.mTimetableId)!!
        assertEquals(timetable1.mTimetableId, timetable.mTimetableId)
        assertEquals(timetable1.mTimetableName, timetable.mTimetableName)
        assertEquals(timetable1.mTimetableTime, timetable.mTimetableTime)
        assertEquals(timetable1.mTimetableDisplayOrder, timetable.mTimetableDisplayOrder)

        timetable = dao.findById(timetable2.mTimetableId)!!
        assertEquals(timetable2.mTimetableId, timetable.mTimetableId)
        assertEquals(timetable2.mTimetableName, timetable.mTimetableName)
        assertEquals(timetable2.mTimetableTime, timetable.mTimetableTime)
        assertEquals(timetable2.mTimetableDisplayOrder, timetable.mTimetableDisplayOrder)

        // findById - not exists
        assertNull(dao.findById("unknown id"))
    }
}