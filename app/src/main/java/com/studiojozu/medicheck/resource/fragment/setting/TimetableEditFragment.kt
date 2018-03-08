package com.studiojozu.medicheck.resource.fragment.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.TimetableNameType
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType
import com.studiojozu.medicheck.resource.fragment.TimePickerFragment

class TimetableEditFragment : DialogFragment(), DialogInterface.OnClickListener {
    companion object {
        private const val REQUEST_CODE_TIME_PICK = 1
        const val KEY_REQUEST_CODE = "request_code"
        const val KEY_TIMETABLE = "timetable"

        fun newInstance(requestCode: Int, timetable: Timetable?): TimetableEditFragment {
            val editFragment = TimetableEditFragment()

            editFragment.arguments = Bundle()
            editFragment.arguments.putInt(TimetableEditFragment.KEY_REQUEST_CODE, requestCode)
            editFragment.arguments.putSerializable(TimetableEditFragment.KEY_TIMETABLE, timetable)

            return editFragment
        }
    }

    private lateinit var unBinder: Unbinder
    private lateinit var timetable: Timetable
    private lateinit var time: TimetableTimeType

    @BindView(R.id.edit_name)
    lateinit var nameEditText: EditText
    @BindView(R.id.edit_time)
    lateinit var timeEditText: TextView

    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = View.inflate(context, R.layout.fragment_setting_alarm_edit, null)
        unBinder = ButterKnife.bind(this@TimetableEditFragment, view)

        timetable = arguments?.get(KEY_TIMETABLE) as? Timetable ?: Timetable()
        time = timetable.timetableTime
        nameEditText.text = Editable.Factory.getInstance().newEditable(timetable.timetableName.displayValue)
        timeEditText.text = Editable.Factory.getInstance().newEditable(time.displayValue)

        val builder = AlertDialog.Builder(activity, R.style.EditableAlertDialog)
        builder.setView(view)
        builder.setTitle(R.string.button_setting_menu_take_time)
        builder.setPositiveButton(android.R.string.ok, this@TimetableEditFragment)
        builder.setNegativeButton(android.R.string.cancel, this@TimetableEditFragment)

        return builder.create()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unBinder.unbind()
    }

    override fun onClick(dialog: DialogInterface?, which: Int) {
        val resultIntent = Intent()
        resultIntent.putExtra(KEY_TIMETABLE,
                timetable.copy(
                        timetableName = TimetableNameType(nameEditText.text.toString()),
                        timetableTime = time))

        parentFragment.onActivityResult(arguments?.getInt(KEY_REQUEST_CODE) ?: -1, which, resultIntent)
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        targetFragment.onActivityResult(targetRequestCode, Activity.RESULT_CANCELED, null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        data ?: return
        if (requestCode != REQUEST_CODE_TIME_PICK) return

        val hourOfDay = data.getIntExtra(TimePickerFragment.KEY_HOUR_OF_DAY, -1)
        if (hourOfDay < 0) return

        val minute = data.getIntExtra(TimePickerFragment.KEY_MINUTE, -1)
        if (minute < 0) return

        time = TimetableTimeType(hourOfDay, minute)
        timeEditText.text = Editable.Factory.getInstance().newEditable(time.displayValue)
    }

    @OnClick(R.id.edit_time)
    fun onClickTimeTextView() =
            TimePickerFragment
                    .newInstance(
                            requestCode = REQUEST_CODE_TIME_PICK,
                            hourOfDay = time.hourOfDay,
                            minute = time.minute)
                    .show(childFragmentManager, "time_picker_dialog")
}
