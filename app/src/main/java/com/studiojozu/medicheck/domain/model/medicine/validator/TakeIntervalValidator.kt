package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.ANumericValidator
import com.studiojozu.medicheck.domain.model.medicine.TakeIntervalModeType

class TakeIntervalValidator(intervalPattern: TakeIntervalModeType.DateIntervalPattern)
    : ANumericValidator(
        mMin = if (intervalPattern === TakeIntervalModeType.DateIntervalPattern.DAYS) 0 else 1,
        mMax = if (intervalPattern === TakeIntervalModeType.DateIntervalPattern.DAYS) 365 else 31,
        mAllowMinValue = true,
        mAllowMaxValue = true)
