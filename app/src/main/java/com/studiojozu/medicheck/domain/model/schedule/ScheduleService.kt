package com.studiojozu.medicheck.domain.model.schedule

import com.studiojozu.medicheck.domain.model.medicine.Medicine

/**
 * 薬に関する情報を操作するサービス
 */
class ScheduleService {
    /**
     * 薬情報を元にスケジュールを生成する
     *
     * @return 生成したスケジュール一覧インスタンス
     */
    fun createScheduleList(medicine: Medicine): ScheduleList {
        val scheduleList = ScheduleList()
        scheduleList.createScheduleList(medicine)

        return scheduleList
    }
}
