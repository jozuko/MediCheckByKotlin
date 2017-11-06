package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.validator.ANumericValidator
import com.studiojozu.medicheck.domain.model.medicine.MedicineIntervalModeType

class TakeIntervalValidator(intervalPattern: MedicineIntervalModeType.DateIntervalPattern)
    : ANumericValidator(
        mMin = if (intervalPattern === MedicineIntervalModeType.DateIntervalPattern.DAYS) 0 else 1,
        mMax = if (intervalPattern === MedicineIntervalModeType.DateIntervalPattern.DAYS) 365 else 31,
        mAllowMinValue = true,
        mAllowMaxValue = true)
