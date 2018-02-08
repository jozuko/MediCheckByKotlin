package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineDateNumberType
import com.studiojozu.medicheck.domain.model.medicine.MedicineStartDatetimeType
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList
import org.jetbrains.annotations.Contract
import java.io.Serializable
import java.util.*

/**
 * スケジュールの一覧を管理するクラス
 */
class ScheduleList : Iterator<Schedule>, Iterable<Schedule>, Serializable {
    companion object {
        private const val serialVersionUID = -4901152259481847837L
    }

    private var scheduleList: MutableList<Schedule> = mutableListOf()
    private var scheduleIterator: Iterator<Schedule>? = null

    private fun copy(): ScheduleList {
        val copyTarget = ScheduleList()
        copyTarget.scheduleList.addAll(scheduleList.map { it.copy() })

        return copyTarget
    }

    override fun iterator(): Iterator<Schedule> {
        val scheduleList = copy()
        scheduleList.scheduleIterator = scheduleList.createScheduleIterator()
        return scheduleList
    }

    override fun hasNext(): Boolean = scheduleIterator!!.hasNext()

    override fun next(): Schedule = scheduleIterator!!.next()

    private fun createScheduleIterator(): Iterator<Schedule> = scheduleList.iterator()

    private fun clearScheduleList() = scheduleList.clear()

    fun createScheduleList(medicine: Medicine) {
        val medicineNumber = calculateMedicineNumber(medicine.timetableList, medicine.medicineDateNumber)
        createScheduleList(medicine, medicineNumber)
    }

    /**
     * 服用回数を計算する（タイムテーブルの回数×服用日数）
     *
     * @param timetableList タイムテーブル一覧
     * @param dateNumber    服用日数
     * @return （タイムテーブルの回数×服用日数）頓服の場合は0を返却する
     */
    private fun calculateMedicineNumber(timetableList: MedicineTimetableList, dateNumber: MedicineDateNumberType): Int = if (timetableList.isOneShotMedicine) 0 else timetableList.count * dateNumber.dbValue.toInt()

    /**
     * スケジュールを作成する。
     *
     * @param medicine       薬
     * @param medicineNumber 服用回数
     */
    private fun createScheduleList(medicine: Medicine, medicineNumber: Int) {
        clearScheduleList()

        // 服用予定日時を初期化する
        var planDate: PlanDate? = null

        for (medicine_i in 0 until medicineNumber) {
            // 服用予定算出基準日時を取得する
            val standardDatetime = getNextStartDatetime(medicine, planDate)

            // 服用予定日時を取得する
            planDate = medicine.timetableList.getPlanDate(standardDatetime)

            // 服用予定日時を一覧に追加する
            val schedule = Schedule(medicine.medicineId, planDate.schedulePlanDate, planDate.timetableId, ScheduleNeedAlarmType(), ScheduleIsTakeType(), ScheduleTookDatetimeType())
            scheduleList.add(schedule)
        }
    }

    @Contract("_, null -> !null")
    private fun getNextStartDatetime(medicine: Medicine, planDate: PlanDate?): ADatetimeType<*> {

        // 予定時刻が初期値の場合は予定日時を返却する
        if (planDate == null)
            return getFirstDatetime(medicine)

        // 予定日時で使用しているTimetableIdがTimetableListの最終時刻ではなかった場合は予定日時を返却する
        if (!medicine.timetableList.isFinalTime(planDate.timetableId))
            return planDate.planDatetime

        // 予定日時で使用しているTimetableIdがTimetableListの最終時刻の場合はIntervalを加算する
        val afterIntervalDateTime = medicine.medicineInterval.addInterval(planDate.planDatetime, medicine.medicineIntervalMode)
        val nextDay = CalendarNoSecond(afterIntervalDateTime.dbValue).calendar
        nextDay.set(Calendar.HOUR_OF_DAY, 0)
        nextDay.set(Calendar.MINUTE, 0)
        return MedicineStartDatetimeType(nextDay)
    }

    private fun getFirstDatetime(medicine: Medicine): ADatetimeType<*> {
        if (medicine.medicineIntervalMode.isDays)
            return medicine.medicineStartDatetime

        val nextDay = CalendarNoSecond(medicine.medicineStartDatetime.dbValue).calendar
        nextDay.set(Calendar.DAY_OF_MONTH, medicine.medicineInterval.dbValue.toInt())

        if (nextDay.timeInMillis < medicine.medicineStartDatetime.dbValue)
            nextDay.add(Calendar.MONTH, 1)

        return MedicineStartDatetimeType(nextDay)
    }
}
