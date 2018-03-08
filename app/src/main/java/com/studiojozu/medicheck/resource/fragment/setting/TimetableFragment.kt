package com.studiojozu.medicheck.resource.fragment.setting

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.application.TimetableFinderService
import com.studiojozu.medicheck.application.TimetableRegisterService
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.domain.model.setting.TimetableDisplayOrderType
import com.studiojozu.medicheck.resource.custom.SortableArrayAdapter
import com.studiojozu.medicheck.resource.custom.SortableListView
import com.studiojozu.medicheck.resource.fragment.MessageAlertDialogFragment
import javax.inject.Inject

class TimetableFragment : ABaseFragment(), SortableArrayAdapter.OnShowTextListener<Timetable>, SortableArrayAdapter.OnDeleteClickListener<Timetable>, SortableArrayAdapter.OnSortedListener {

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
            val timetable = data?.getSerializableExtra(TimetableEditFragment.KEY_TIMETABLE) ?: return
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
                showTextListener = this,
                onDeleteClickListener = this,
                onSortedListener = this)

        timetableListView.setOnItemClickListener { _, _, position, _ -> showEditDialog(position) }
    }

    override fun getText(targetObject: Timetable): String
            = targetObject.timetableNameWithTime

    override fun onDeleteClicked(targetObject: Timetable, position: Int): Boolean
            = delete(targetObject)

    override fun onSorted() {
        val targetTimetables = (0..(timetableListView.adapter!!.count - 1))
                .map { position ->
                    val listItem = timetableListView.adapter!!.getItem(position) as Timetable
                    savedTimetables.first { savedTimetable -> savedTimetable.timetableId == listItem.timetableId }
                            .copy(timetableDisplayOrder = TimetableDisplayOrderType(position + 1))
                }

        timetableRegisterService.save(targetTimetables)
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
        TimetableEditFragment
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
}

