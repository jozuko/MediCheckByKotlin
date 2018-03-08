package com.studiojozu.medicheck.resource.fragment.setting

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
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
import com.studiojozu.medicheck.domain.model.setting.TimetableDisplayOrderType
import com.studiojozu.medicheck.domain.model.setting.TimetableNameType
import com.studiojozu.medicheck.domain.model.setting.TimetableTimeType
import com.studiojozu.medicheck.resource.custom.SortableArrayAdapter
import com.studiojozu.medicheck.resource.custom.SortableListView
import com.studiojozu.medicheck.resource.fragment.MessageAlertDialogFragment
import com.studiojozu.medicheck.resource.fragment.TimePickerFragment
import javax.inject.Inject

class TimetableFragment : ABaseFragment() {
    companion object {
        private const val REQUEST_CODE_EDIT = 1

        fun newInstance() = TimetableFragment()
    }

    @Inject
    lateinit var timetableFinderService: TimetableFinderService

    @Inject
    lateinit var timetableRegisterService: TimetableRegisterService

    @BindView(R.id.timetable_list_view)
    lateinit var timetableListView: SortableListView

    private var savedTimetables: List<Timetable> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        application.component.inject(this)

        val view = getView(inflater, container)
        unBinder = ButterKnife.bind(this, view)

        updateSavedTimetable()
        setInitialData()
        return view
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_CODE_EDIT && resultCode == DialogInterface.BUTTON_POSITIVE) {
            val timetable = data?.getSerializableExtra(EditFragment.KEY_TIMETABLE) ?: return
            if (timetable is Timetable) save(timetable)
        }
    }

    private fun getView(inflater: LayoutInflater, container: ViewGroup?): View
            = inflater.inflate(R.layout.fragment_setting_timetable, container, false)

    private fun setInitialData() {
        timetableListView.adapter = SortableArrayAdapter(
                context = context,
                objects = savedTimetables as MutableList<Timetable>,
                fragment = this,
                showTextListener = object : SortableArrayAdapter.OnShowTextListener<Timetable> {
                    override fun getText(targetObject: Timetable): String = targetObject.timetableNameWithTime
                },
                onDeleteClickListener = object : SortableArrayAdapter.OnDeleteClickListener<Timetable> {
                    override fun onDeleteClicked(targetObject: Timetable, position: Int): Boolean = delete(targetObject)
                },
                onSortedListener = object : SortableArrayAdapter.OnSortedListener {
                    override fun onSorted() {
                        val targetTimetables = (0..(timetableListView.adapter!!.count - 1))
                                .map { position ->
                                    val listItem = timetableListView.adapter!!.getItem(position) as Timetable
                                    savedTimetables.first { savedTimetable -> savedTimetable.timetableId == listItem.timetableId }
                                            .copy(timetableDisplayOrder = TimetableDisplayOrderType(position + 1))
                                }

                        timetableRegisterService.save(targetTimetables)
                    }
                }
        )

        timetableListView.setOnItemClickListener { _, _, position, _ -> showEditDialog(position) }
    }

    private fun showMessage(@StringRes resourceId: Int) =
            MessageAlertDialogFragment
                    .newInstance(
                            message = context.resources.getString(resourceId),
                            positiveButtonLabel = context.resources.getString(android.R.string.ok))
                    .show(childFragmentManager, "message_dialog")

    @OnClick(R.id.button_add)
    fun onClickAddButton() = showEditDialog(-1)

    private fun showEditDialog(position: Int) {
        val selectedItem = if (position < 0) null else timetableListView.adapter?.getItem(position) as? Timetable
        EditFragment
                .newInstance(
                        requestCode = REQUEST_CODE_EDIT,
                        timetable = selectedItem)
                .show(childFragmentManager, "edit_dialog")
    }

    private fun updateSavedTimetable() {
        savedTimetables = timetableFinderService.findAll()
    }

    private fun updateDisplayOrder() {
        updateSavedTimetable()

        savedTimetables = savedTimetables.mapIndexed { index, timetable -> timetable.copy(timetableDisplayOrder = TimetableDisplayOrderType(index + 1)) }
        timetableRegisterService.save(savedTimetables)

        setInitialData()
    }

    private fun delete(targetObject: Timetable): Boolean {
        when (timetableRegisterService.delete(timetable = targetObject)) {
            TimetableRegisterService.ErrorType.LAST_ONE -> showMessage(R.string.message_can_not_delete_last_one)
            TimetableRegisterService.ErrorType.EXIST_RELATION_OTHER -> showMessage(R.string.message_can_not_delete_exist_relation_other)
            else -> {
                updateDisplayOrder()
                return true
            }
        }
        return false
    }

    private fun save(timetable: Timetable) {
        timetableRegisterService.save(timetable)
        updateSavedTimetable()
        setInitialData()
    }

    class EditFragment : DialogFragment(), DialogInterface.OnClickListener {
        companion object {
            private const val REQUEST_CODE_TIME_PICK = 1
            const val KEY_REQUEST_CODE = "request_code"
            const val KEY_TIMETABLE = "timetable"

            fun newInstance(requestCode: Int, timetable: Timetable?): EditFragment {
                val editFragment = EditFragment()

                editFragment.arguments = Bundle()
                editFragment.arguments.putInt(EditFragment.KEY_REQUEST_CODE, requestCode)
                editFragment.arguments.putSerializable(EditFragment.KEY_TIMETABLE, timetable)

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
            unBinder = ButterKnife.bind(this@EditFragment, view)

            timetable = arguments?.get(KEY_TIMETABLE) as? Timetable ?: Timetable()
            time = timetable.timetableTime
            nameEditText.text = Editable.Factory.getInstance().newEditable(timetable.timetableName.displayValue)
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
}

