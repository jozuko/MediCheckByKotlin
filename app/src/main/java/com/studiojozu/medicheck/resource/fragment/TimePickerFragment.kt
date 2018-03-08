package com.studiojozu.medicheck.resource.fragment

import android.app.Activity
import android.app.Dialog
import android.app.TimePickerDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.format.DateFormat
import android.widget.TimePicker
import com.studiojozu.medicheck.R

class TimePickerFragment : DialogFragment(), TimePickerDialog.OnTimeSetListener {

    companion object {
        const val KEY_REQUEST_CODE = "requestCode"
        const val KEY_HOUR_OF_DAY = "hourOfDay"
        const val KEY_MINUTE = "minute"

        fun newInstance(requestCode: Int, hourOfDay: Int, minute: Int): TimePickerFragment {
            val timePickerFragment = TimePickerFragment()

            timePickerFragment.arguments = Bundle()
            timePickerFragment.arguments.putInt(TimePickerFragment.KEY_REQUEST_CODE, requestCode)
            timePickerFragment.arguments.putInt(TimePickerFragment.KEY_HOUR_OF_DAY, hourOfDay)
            timePickerFragment.arguments.putInt(TimePickerFragment.KEY_MINUTE, minute)

            return timePickerFragment
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val hourOfDay = arguments?.getInt(KEY_HOUR_OF_DAY) ?: 0
        val minute = arguments?.getInt(KEY_MINUTE) ?: 0

        val dialog = TimePickerDialog(context, R.style.TimePickerDialogStyle, this, hourOfDay, minute, DateFormat.is24HourFormat(context))
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, resources.getString(android.R.string.cancel), { _, _ -> dismiss() })
        dialog.setOnCancelListener { dismiss() }

        return dialog
    }

    override fun onTimeSet(view: TimePicker, hourOfDay: Int, minute: Int) {
        if (Build.VERSION.SDK_INT in Build.VERSION_CODES.JELLY_BEAN..Build.VERSION_CODES.KITKAT && !view.isShown) {
            return
        }

        val resultIntent = Intent()
        resultIntent.putExtra(KEY_HOUR_OF_DAY, hourOfDay)
        resultIntent.putExtra(KEY_MINUTE, minute)
        parentFragment.onActivityResult(arguments?.getInt(KEY_REQUEST_CODE) ?: -1, Activity.RESULT_OK, resultIntent)
    }
}