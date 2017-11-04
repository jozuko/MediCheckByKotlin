package com.studiojozu.medicheck.domain.model.medicine

import com.studiojozu.common.domain.model.general.ADatetimeType
import com.studiojozu.medicheck.domain.model.schedule.PlanDate
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.TimetableComparator
import com.studiojozu.medicheck.domain.model.setting.TimetableIdType
import java.io.Serializable
import java.util.*

class MedicineTimetableList : Iterable<Timetable>, Iterator<Timetable>, Serializable {
    companion object {
        const val serialVersionUID = -6268967129299051940L
    }

    private var mTimetableList: MutableList<Timetable> = mutableListOf()

    @Transient private var mTimetableIterator: Iterator<Timetable>? = null

    var isOneShotMedicine = false

    val timetableListOrderByTime: List<Timetable>
        get() {
            val cloneList = this.copy()
            Collections.sort(cloneList.mTimetableList, TimetableComparator(TimetableComparator.ComparePattern.Time))
            return cloneList.mTimetableList
        }

    val timetableListOrderByDisplayOrder: List<Timetable>
        get() {
            val cloneList = this.copy()
            Collections.sort(cloneList.mTimetableList, TimetableComparator(TimetableComparator.ComparePattern.DisplayOrder))
            return cloneList.mTimetableList
        }

    val count: Int
        get() = mTimetableList.size

    val displayValue: String
        get() = if (isOneShotMedicine) "" else mTimetableList.joinToString("\n") { it.timetableNameWithTime }

    constructor()

    constructor(timetables: MutableList<Timetable>) {
        mTimetableList = timetables
    }

    private fun copy(): MedicineTimetableList {
        val copyTarget = MedicineTimetableList()
        copyTarget.mTimetableList.addAll(this.mTimetableList.map { it.copy() })
        return copyTarget
    }

    override fun iterator(): Iterator<Timetable> {
        val other = copy()
        other.mTimetableIterator = mTimetableList.iterator()
        return other
    }

    override fun hasNext(): Boolean = mTimetableIterator!!.hasNext()

    override fun next(): Timetable = mTimetableIterator!!.next()

    private fun clearTimetableList() = mTimetableList.clear()

    fun setTimetableList(timetableList: List<Timetable>?) {
        clearTimetableList()
        timetableList?.let { mTimetableList.addAll(it) }
    }

    /**
     * 服用予定日時を計算する
     *
     * @param startDatetime 服用予定日時を決めるうえでの起点となる日時(この日時以前とならない予定日時を求める)
     * @return 服用予定日時
     */
    fun getPlanDate(startDatetime: ADatetimeType<*>): PlanDate = getPlanDate(startDatetime, null)

    /**
     * 服用予定日時を計算する
     *
     * @param startDatetime 服用予定日時を決めるうえでの起点となる日時(この日時以前とならない予定日時を求める)
     * @param planDate      服用予定日時に使用する日付を保持
     * @return 服用予定日時
     */
    private fun getPlanDate(startDatetime: ADatetimeType<*>, planDate: PlanDate?): PlanDate {
        val resultPlanDate = planDate ?: PlanDate(startDatetime, TimetableIdType())
        return timetableListOrderByTime
                .map { it.getPlanDateTime(resultPlanDate.mPlanDatetime) }
                .firstOrNull { it.mPlanDatetime > startDatetime }
                ?: getPlanDate(startDatetime, resultPlanDate.addDay(1))
    }

    /**
     * タイムテーブル一覧の内、最終時刻を表すTimetableであるかをチェックする
     *
     * @param timetableId 比較するタイムテーブルID
     * @return 最終時刻を表すTimetableIDの場合はtrueを返却する
     */
    fun isFinalTime(timetableId: TimetableIdType): Boolean {
        if (mTimetableList.isEmpty()) return false
        return timetableListOrderByTime[count - 1].mTimetableId == timetableId
    }

    fun contain(timetable: Timetable?): Boolean = mTimetableList.contains(timetable)
}
