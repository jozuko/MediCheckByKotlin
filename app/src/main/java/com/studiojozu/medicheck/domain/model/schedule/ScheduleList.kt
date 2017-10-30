package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.medicheck.domain.model.medicine.Medicine
import com.studiojozu.medicheck.domain.model.medicine.MedicineDateNumberType
import com.studiojozu.medicheck.domain.model.medicine.MedicineTimetableList
import com.studiojozu.medicheck.domain.model.medicine.StartDatetimeType
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

    private var mScheduleList: MutableList<Schedule> = mutableListOf()
    private var mScheduleIterator: Iterator<Schedule>? = null

    private fun copy(): ScheduleList {
        val copyTarget = ScheduleList()
        copyTarget.mScheduleList.addAll(mScheduleList.map { it.copy() })

        return copyTarget
    }

    override fun iterator(): Iterator<Schedule> {
        val scheduleList = copy()
        scheduleList.mScheduleIterator = scheduleList.createScheduleIterator()
        return scheduleList
    }

    override fun hasNext(): Boolean = mScheduleIterator!!.hasNext()

    override fun next(): Schedule = mScheduleIterator!!.next()

    private fun createScheduleIterator(): Iterator<Schedule> = mScheduleList.iterator()

    private fun clearScheduleList() = mScheduleList.clear()

    fun createScheduleList(medicine: Medicine) {
        val medicineNumber = calculateMedicineNumber(medicine.timetableList, medicine.dateNumber)
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
            val schedule = Schedule(medicine.medicineId, planDate!!.planDate, planDate.mTimetableId, ScheduleNeedAlarmType(), IsTakeType(), TookDatetimeType())
            mScheduleList.add(schedule)
        }
    }

    @Contract("_, null -> !null")
    private fun getNextStartDatetime(medicine: Medicine, planDate: PlanDate?): ADatetimeType<*> {

        // 予定時刻が初期値の場合は予定日時を返却する
        if (planDate == null)
            return getFirstDatetime(medicine)

        // 予定日時で使用しているTimetableIdがTimetableListの最終時刻ではなかった場合は予定日時を返却する
        if (!medicine.timetableList.isFinalTime(planDate.mTimetableId))
            return planDate.mPlanDatetime

        // 予定日時で使用しているTimetableIdがTimetableListの最終時刻の場合はIntervalを加算する
        val afterIntervalDateTime = medicine.takeInterval.addInterval(planDate.mPlanDatetime, medicine.takeIntervalMode)
        val nextDay = Calendar.getInstance()
        nextDay.timeInMillis = afterIntervalDateTime.dbValue
        nextDay.set(Calendar.HOUR_OF_DAY, 0)
        nextDay.set(Calendar.MINUTE, 0)
        return StartDatetimeType(nextDay)
    }

    private fun getFirstDatetime(medicine: Medicine): ADatetimeType<*> {
        if (medicine.takeIntervalMode.isDays)
            return medicine.startDatetime

        val nextDay = Calendar.getInstance()
        nextDay.timeInMillis = medicine.startDatetime.dbValue
        nextDay.set(Calendar.DAY_OF_MONTH, medicine.takeInterval.dbValue.toInt())

        if (nextDay.timeInMillis < medicine.startDatetime.dbValue)
            nextDay.add(Calendar.MONTH, 1)

        return StartDatetimeType(nextDay)
    }
}
