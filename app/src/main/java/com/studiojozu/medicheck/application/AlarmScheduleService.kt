package com.studiojozu.medicheck.application

import com.studiojozu.common.domain.model.CalendarNoSecond
import com.studiojozu.medicheck.di.MediCheckApplication
import com.studiojozu.medicheck.domain.model.alarm.AlarmSchedule
import com.studiojozu.medicheck.domain.model.alarm.AlarmScheduleComparator
import com.studiojozu.medicheck.domain.model.medicine.repository.MedicineViewRepository
import com.studiojozu.medicheck.domain.model.person.repository.PersonMediRelationRepository
import com.studiojozu.medicheck.domain.model.schedule.Schedule
import com.studiojozu.medicheck.domain.model.schedule.repository.ScheduleRepository
import com.studiojozu.medicheck.domain.model.setting.repository.SettingRepository
import com.studiojozu.medicheck.domain.model.setting.repository.TimetableRepository
import java.util.TreeSet
import javax.inject.Inject
import kotlin.collections.ArrayList

class AlarmScheduleService(application: MediCheckApplication) {

    @Inject
    internal lateinit var mTimetableRepository: TimetableRepository
    @Inject
    internal lateinit var mSettingRepository: SettingRepository
    @Inject
    internal lateinit var mScheduleRepository: ScheduleRepository
    @Inject
    internal lateinit var mPersonMediRelationRepository: PersonMediRelationRepository
    @Inject
    internal lateinit var mMedicineViewRepository: MedicineViewRepository

    init {
        application.mComponent.inject(this)
    }

    fun getNeedAlarmSchedules(): List<AlarmSchedule> {
        val needAlarmSchedules = ArrayList(mScheduleRepository.findAlarmAll())
        val setting = mSettingRepository.find()
        val now = CalendarNoSecond().calendar

        val alarmTargetSchedules = TreeSet(AlarmScheduleComparator())
        return needAlarmSchedules
                .mapNotNull { createAlarmSchedule(it) }
                .filterTo(alarmTargetSchedules) { it.isNeedAlarm(now, setting) }
                .toList()
    }

    private fun createAlarmSchedule(schedule: Schedule): AlarmSchedule? {
        val timetable = mTimetableRepository.findById(schedule.mTimetableId) ?: return null
        val medicine = mMedicineViewRepository.findByMedicineId(schedule.mMedicineId) ?: return null
        val person = mPersonMediRelationRepository.findPersonByMedicineId(schedule.mMedicineId) ?: return null

        return AlarmSchedule(schedule, timetable, medicine, person)
    }
}