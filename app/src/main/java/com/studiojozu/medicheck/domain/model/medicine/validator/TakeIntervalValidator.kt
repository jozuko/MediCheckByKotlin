package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.validator.ANumericValidator
import com.studiojozu.medicheck.domain.model.medicine.MedicineIntervalModeType

class TakeIntervalValidator(intervalPattern: MedicineIntervalModeType.DateIntervalPattern)
    : ANumericValidator(
        min = if (intervalPattern === MedicineIntervalModeType.DateIntervalPattern.DAYS) 0 else 1,
        max = if (intervalPattern === MedicineIntervalModeType.DateIntervalPattern.DAYS) 365 else 31,
        allowMinValue = true,
        allowMaxValue = true)
