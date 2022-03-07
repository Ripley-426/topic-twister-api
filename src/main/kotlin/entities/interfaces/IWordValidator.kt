package com.example.entities.interfaces

import com.example.entities.dataClasses.ValidationContainer

interface IWordValidator {
    fun getValidationResult(validationData: ValidationContainer) : MutableList<Boolean>
}