package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.ANumericDecimalValidator

import java.math.BigDecimal

class TakeNumberValidator
    : ANumericDecimalValidator(
        mMin = BigDecimal(0),
        mMax = BigDecimal(Integer.MAX_VALUE),
        mAllowMinValue = false,
        mAllowMaxValue = true)
