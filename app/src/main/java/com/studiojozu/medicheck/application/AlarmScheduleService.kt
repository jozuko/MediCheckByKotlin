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
    internal lateinit var timetableRepository: TimetableRepository
    @Inject
    internal lateinit var settingRepository: SettingRepository
    @Inject
    internal lateinit var scheduleRepository: ScheduleRepository
    @Inject
    internal lateinit var personMediRelationRepository: PersonMediRelationRepository
    @Inject
    internal lateinit var medicineViewRepository: MedicineViewRepository

    init {
        application.component.inject(this)
    }

    fun getNeedAlarmSchedules(): List<AlarmSchedule> {
        val needAlarmSchedules = ArrayList(scheduleRepository.findAlarmAll())
        val setting = settingRepository.find()
        val now = CalendarNoSecond().calendar

        val alarmTargetSchedules = TreeSet(AlarmScheduleComparator())
        return needAlarmSchedules
                .mapNotNull { createAlarmSchedule(it) }
                .filterTo(alarmTargetSchedules) { it.isNeedAlarm(now, setting) }
                .toList()
    }

    private fun createAlarmSchedule(schedule: Schedule): AlarmSchedule? {
        val timetable = timetableRepository.findById(schedule.timetableId) ?: return null
        val medicine = medicineViewRepository.findByMedicineId(schedule.medicineId) ?: return null
        val person = personMediRelationRepository.findPersonByMedicineId(schedule.medicineId) ?: return null

        return AlarmSchedule(schedule, timetable, medicine, person)
    }
}