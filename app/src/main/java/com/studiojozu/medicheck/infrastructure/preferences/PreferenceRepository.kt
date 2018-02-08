package com.studiojozu.medicheck.infrastructure.preferences

import android.content.Context
import android.content.SharedPreferences

import com.studiojozu.medicheck.domain.model.person.PersonIdType

class PreferenceRepository(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("medicheck", Context.MODE_PRIVATE)

    var defaultPersonId: PersonIdType?
        get() {
            val personId = sharedPreferences.getString(KEY_DEFAULT_PERSON_ID, null) ?: return null

            return PersonIdType(personId)
        }
        set(personIdType) {
            val editor = sharedPreferences.edit()
            editor.putString(KEY_DEFAULT_PERSON_ID, personIdType?.dbValue)
            editor.apply()
        }

    companion object {
        private val KEY_DEFAULT_PERSON_ID = "default_person_id"
    }
}
