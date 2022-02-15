package com.example.interfaces

import com.example.model.ValidationContainer

interface IWordValidator {
    fun getValidationResult(validationData: ValidationContainer) : MutableList<Boolean>
}