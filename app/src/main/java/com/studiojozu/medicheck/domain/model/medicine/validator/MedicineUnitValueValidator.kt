package com.studiojozu.medicheck.domain.model.medicine.validator

import android.support.annotation.StringRes
import com.studiojozu.common.domain.model.validator.IRequiredValidator
import com.studiojozu.common.domain.model.validator.IValidator

class MedicineUnitValueValidator : IRequiredValidator {

    @StringRes
    override fun validate(vararg validateTargets: Any?): Int {
        val requiredResult = super.validate(*validateTargets)
        if (requiredResult != IValidator.NO_ERROR_RESOURCE_ID)
            return requiredResult

        return IValidator.NO_ERROR_RESOURCE_ID
    }
}
