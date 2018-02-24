package com.studiojozu.medicheck.resource.fragment.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
        timetableListView.adapter = object : SortableArrayAdapter<Timetable>(context, android.R.layout.simple_list_item_1, savedTimetables) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val itemView = super.getView(position, convertView, parent)
                itemView.findViewById<TextView>(android.R.id.text1).text = getItem(position).timetableNameWithTime

                return itemView
            }
        }
    }

    private fun updateSavedTimetable() {
        savedTimetables.clear()
        savedTimetables.addAll(timetableFinderService.findAll())
    }
}

