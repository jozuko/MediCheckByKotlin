package com.studiojozu.medicheck.resource.uicomponent.calendar

import java.util.*

/**
 * カレンダーViewにアクセスするインターフェース定義
 */
interface ICalendarAccess {

    /**
     * カレンダーを任意の年月で表示する
     *
     * @param displayMonthCalendar 表示する年月
     */
    fun showCalendar(displayMonthCalendar: Calendar)

    /**
     * 選択された際に呼び出すListenerを設定する。
     *
     * @param listener 選択時Listener
     */
    fun setClientOnSelectedDayListener(listener: CalendarDayView.OnSelectedDayListener?)
}
