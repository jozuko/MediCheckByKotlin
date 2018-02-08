package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.validator.ANumericValidator

class DateNumberValidator : ANumericValidator(
        min = 1,
        max = 365,
        allowMinValue = true,
        allowMaxValue = true)
