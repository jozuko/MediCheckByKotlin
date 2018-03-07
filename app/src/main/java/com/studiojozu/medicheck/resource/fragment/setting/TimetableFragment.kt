package com.studiojozu.medicheck.resource.fragment.setting

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import butterknife.Unbinder
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.application.TimetableFinderService
import com.studiojozu.medicheck.application.TimetableRegisterService
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.TimetableNameType
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType
import com.studiojozu.medicheck.resource.custom.SortableArrayAdapter
import com.studiojozu.medicheck.resource.custom.SortableListView
import com.studiojozu.medicheck.resource.fragment.MessageAlertDialogFragment
import com.studiojozu.medicheck.resource.fragment.TimePickerFragment
import javax.inject.Inject

class TimetableFragment : ABaseFragment() {
    @Inject
    lateinit var timetableFinderService: TimetableFinderService

    @Inject
    lateinit var timetableRegisterService: TimetableRegisterService

    @BindView(R.id.timetable_list_view)
    lateinit var timetableListView: SortableListView

    private var savedTimetables: MutableList<Timetable> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        application.component.inject(this)

        val view = getView(inflater, container)
        unBinder = ButterKnife.bind(this, view)

        updateSavedTimetable()
        setInitialData()
        return view
    }

    private fun getView(inflater: LayoutInflater, container: ViewGroup?): View
            = inflater.inflate(R.layout.fragment_setting_timetable, container, false)

    private fun setInitialData() {
        timetableListView.adapter = SortableArrayAdapter(
                context = context,
                objects = savedTimetables,
                fragment = this,
                showTextListener = object : SortableArrayAdapter.OnShowTextListener<Timetable> {
                    override fun getText(targetObject: Timetable): String = targetObject.timetableNameWithTime
                },
                onDeleteClickListener = object : SortableArrayAdapter.OnDeleteClickListener<Timetable> {
                    override fun onDeleteClicked(targetObject: Timetable, position: Int): Boolean = delete(targetObject)
                })

        timetableListView.setOnItemClickListener { _, _, position, _ -> showEditDialog(position) }
    }

    private fun updateSavedTimetable() {
        savedTimetables.clear()
        savedTimetables.addAll(timetableFinderService.findAll())
    }

    private fun showMessage(@StringRes resourceId: Int) =
            MessageAlertDialogFragment
                    .build(this@TimetableFragment, {
                        message = context.resources.getString(resourceId)
                        positiveButtonLabel = context.resources.getString(android.R.string.ok)
                    })
                    .show(childFragmentManager, "message_dialog")

    @OnClick(R.id.button_add)
    fun onClickAddButton() = showEditDialog(-1)

    private fun delete(targetObject: Timetable): Boolean {
        when (timetableRegisterService.delete(timetable = targetObject)) {
            TimetableRegisterService.ErrorType.LAST_ONE -> showMessage(R.string.message_can_not_delete_last_one)
            TimetableRegisterService.ErrorType.EXIST_RELATION_OTHER -> showMessage(R.string.message_can_not_delete_exist_relation_other)
            else -> return true
        }
        return false
    }

    private fun showEditDialog(position: Int) {
        val editFragment = EditFragment()

        if (position >= 0) {
            val selectedItem = timetableListView.adapter?.getItem(position) as? Timetable ?: return
            editFragment.arguments = Bundle()
            editFragment.arguments.putSerializable("name", selectedItem.timetableName)
            editFragment.arguments.putSerializable("time", selectedItem.timetableTime)
        }
        editFragment.positiveClickListener = DialogInterface.OnClickListener { _, _ -> save(position) }
        editFragment.show(childFragmentManager, "edit_dialog")
    }

    private fun save(position: Int) {
        // TODO 処理を書く
    }

    class EditFragment : DialogFragment(), DialogInterface.OnClickListener {
        private var unBinder: Unbinder? = null
        var positiveClickListener: DialogInterface.OnClickListener? = null

        @BindView(R.id.edit_name)
        lateinit var nameEditText: EditText
        @BindView(R.id.edit_time)
        lateinit var timeEditText: TextView

        @SuppressLint("InflateParams")
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val view = View.inflate(context, R.layout.fragment_setting_alarm_edit, null)
            unBinder = ButterKnife.bind(this@EditFragment, view)

            val name = arguments?.get("name") as? TimetableNameType ?: TimetableNameType()
            val time = arguments?.get("time") as? TimetableTimeType ?: TimetableTimeType(hourOfDay = 0, minute = 0)
            nameEditText.text = Editable.Factory.getInstance().newEditable(name.displayValue)
            timeEditText.text = Editable.Factory.getInstance().newEditable(time.displayValue)

            val builder = AlertDialog.Builder(activity, R.style.EditableAlertDialog)
            builder.setView(view)
            builder.setTitle(R.string.button_setting_menu_take_time)
            builder.setPositiveButton(android.R.string.ok, this@EditFragment)
            builder.setNegativeButton(android.R.string.cancel, this@EditFragment)

            return builder.create()
        }

        override fun onDestroyView() {
            super.onDestroyView()
            unBinder?.unbind()
        }

        override fun onClick(dialog: DialogInterface?, which: Int) {
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> positiveClickListener?.onClick(dialog, which)
            }
        }

        @OnClick(R.id.edit_time)
        fun onClickTimeTextView() {
            val timePickerFragment = TimePickerFragment()

            timePickerFragment.arguments = Bundle()
            timePickerFragment.arguments.putInt(TimePickerFragment.KEY_HOUR_OF_DAY, 0)
            timePickerFragment.arguments.putInt(TimePickerFragment.KEY_MINUTE, 0)

            timePickerFragment.show(childFragmentManager, "time_picker_dialog")
        }
    }
}

