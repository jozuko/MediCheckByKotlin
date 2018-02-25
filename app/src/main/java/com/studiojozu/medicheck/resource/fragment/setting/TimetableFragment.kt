package com.studiojozu.medicheck.resource.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.studiojozu.medicheck.R
import com.studiojozu.medicheck.application.TimetableFinderService
import com.studiojozu.medicheck.domain.model.setting.Timetable
import com.studiojozu.medicheck.resource.custom.SortableArrayAdapter
import com.studiojozu.medicheck.resource.custom.SortableListView
import javax.inject.Inject

class TimetableFragment : ABaseFragment() {
    @Inject
    lateinit var timetableFinderService: TimetableFinderService

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
                    override fun onDeleteClicked(targetObject: Timetable, position: Int) {
                        TODO("not implemented")
                    }
                })
        timetableListView.setOnItemClickListener { parent, view, position, id ->
            run {
                TODO("not implemented")
            }
        }
    }

    private fun updateSavedTimetable() {
        savedTimetables.clear()
        savedTimetables.addAll(timetableFinderService.findAll())
    }
}

