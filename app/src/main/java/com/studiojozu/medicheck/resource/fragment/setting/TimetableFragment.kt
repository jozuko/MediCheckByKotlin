package com.studiojozu.medicheck.resource.fragment.setting

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.BindView
import butterknife.ButterKnife
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.application.TimetableFinderService
import com.studiojozu.medicheck.application.TimetableRegisterService
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.resource.custom.SortableArrayAdapter
import com.studiojozu.medicheck.resource.custom.SortableListView
import com.studiojozu.medicheck.resource.fragment.MessageAlertDialogFragment
import javax.inject.Inject

class TimetableFragment : ABaseFragment() {
    @Inject
    lateinit var timetableFinderService: TimetableFinderService

    @Inject
    lateinit var timetableRegisterService: TimetableRegisterService

    @BindView(R.id.timetable_list_view)
    lateinit var timetableListView: SortableListView

    @BindView(R.id.button_add)
    lateinit var addButton: Button

    private var savedTimetables: MutableList<Timetable> = mutableListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        application.component.inject(this)

        val view = getView(inflater, container)
        unBinder = ButterKnife.bind(this, view)

        updateSavedTimetable()
        setInitialData()
        setAddButtonListener()
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
            MessageAlertDialogFragment.build(this@TimetableFragment, {
                message = context.resources.getString(resourceId)
                positiveButtonLabel = context.resources.getString(android.R.string.ok)
            }).show(childFragmentManager, "message_dialog")

    private fun setAddButtonListener() =
            addButton.setOnClickListener { showEditDialog(-1) }

    private fun delete(targetObject: Timetable): Boolean {
        val result = timetableRegisterService.delete(timetable = targetObject)
        if (result == TimetableRegisterService.ErrorType.NO_ERROR) return true

        if (result == TimetableRegisterService.ErrorType.LAST_ONE) {
            showMessage(R.string.message_can_not_delete_last_one)
            return false
        }

        showMessage(R.string.message_can_not_delete_exist_relation_other)
        return false
    }

    private fun showEditDialog(position: Int) {
        val editFragment = EditFragment()
        editFragment.positiveClickListener = DialogInterface.OnClickListener { _, _ -> save(position) }
        editFragment.show(childFragmentManager, "edit_dialog")
    }

    private fun save(position: Int) {
        // TODO 処理を書く
    }

    class EditFragment : DialogFragment(), DialogInterface.OnClickListener {

        var positiveClickListener: DialogInterface.OnClickListener? = null

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val builder = AlertDialog.Builder(activity, R.style.EditableAlertDialog)
            builder.setView(R.layout.fragment_setting_alarm_edit)
            builder.setTitle(R.string.button_setting_menu_take_time)
            builder.setPositiveButton(android.R.string.ok, this@EditFragment)
            builder.setNegativeButton(android.R.string.cancel, this@EditFragment)

            return builder.create()
        }

        override fun onClick(dialog: DialogInterface?, which: Int) {
            when (which) {
                DialogInterface.BUTTON_POSITIVE -> positiveClickListener?.onClick(dialog, which)
            }
        }
    }
}

