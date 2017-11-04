package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.ANumericValidator

class DateNumberValidator : ANumericValidator(
        mMin = 1,
        mMax = 365,
        mAllowMinValue = true,
        mAllowMaxValue = true)
