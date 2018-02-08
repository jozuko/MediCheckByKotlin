package com.studiojozu.medicheck.domain.model.medicine.validator

import com.studiojozu.common.domain.model.validator.ANumericDecimalValidator

import java.math.BigDecimal

class TakeNumberValidator
    : ANumericDecimalValidator(
        min = BigDecimal(0),
        max = BigDecimal(Integer.MAX_VALUE),
        allowMinValue = false,
        allowMaxValue = true)
