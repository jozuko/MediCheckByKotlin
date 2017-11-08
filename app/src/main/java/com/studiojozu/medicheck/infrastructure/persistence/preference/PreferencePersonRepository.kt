package com.studiojozu.medicheck.infrastructure.persistence.preference

import android.content.SharedPreferences
import com.studiojozu.medicheck.domain.model.person.PersonIdType

class PreferencePersonRepository(private val mSharedPreferences: SharedPreferences) {

    companion object {
        private const val KEY_DEFAULT_PERSON_ID = "default_person_id"
    }

    var latestUsedPersonId: PersonIdType?
        get() {
            val personId = mSharedPreferences.getString(KEY_DEFAULT_PERSON_ID, null) ?: return null
            return PersonIdType(personId)
        }
        set(personId) {
            personId ?: return

            val editor = mSharedPreferences.edit()
            editor.putString(KEY_DEFAULT_PERSON_ID, personId.dbValue)
            editor.apply()
        }
}