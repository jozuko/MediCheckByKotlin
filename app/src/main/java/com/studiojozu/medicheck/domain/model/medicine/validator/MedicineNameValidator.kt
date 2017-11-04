package com.studiojozu.medicheck.domain.model.medicine.validator

import android.support.annotation.StringRes
import com.studiojozu.common.domain.model.AValidator
import com.studiojozu.common.domain.model.IValidator
import com.studiojozu.medicheck.R

/**
 * 薬の名前に対するValidator
 */
class MedicineNameValidator : AValidator() {

    @StringRes
    override fun validate(vararg validateTargets: Any?): Int {
        if (validateTargets.isEmpty())
            return R.string.validation_require

        val requiredCheckResult = requiredCheck(value = validateTargets[0])
        if (requiredCheckResult != IValidator.NO_ERROR_RESOURCE_ID) return requiredCheckResult

        return IValidator.NO_ERROR_RESOURCE_ID
    }
}
