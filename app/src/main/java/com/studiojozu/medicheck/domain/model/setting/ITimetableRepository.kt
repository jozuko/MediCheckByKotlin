package com.studiojozu.medicheck.domain.model.setting

import android.content.Context

interface ITimetableRepository {
    fun findTimetableById(context: Context, timetableId: TimetableIdType): Timetable?

    fun findAll(context: Context): Collection<Timetable>
}
