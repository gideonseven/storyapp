package com.don.core_validation

import androidx.databinding.BindingAdapter


/**
 * Created by gideon on 05 January 2023
 * gideon@cicil.co.id
 * https://www.cicil.co.id/
 */

@BindingAdapter("validationType")
fun CustomTextInputLayout.validationType(valType: ValidationType) {
    this.validationType = valType
}