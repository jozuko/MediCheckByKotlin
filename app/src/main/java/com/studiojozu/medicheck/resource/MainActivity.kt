package com.studiojozu.medicheck.resource

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.studiojozu.medicheck.application.MedicineFinderService
import com.studiojozu.medicheck.di.MediCheckApplication
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var medicineFinderService: MedicineFinderService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as MediCheckApplication).mComponent.inject(this)

        Toast.makeText(this, medicineFinderService.defaultTimetable?.mTimetableName?.displayValue, Toast.LENGTH_SHORT).show()
    }
}
